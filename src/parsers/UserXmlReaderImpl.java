package parsers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import model.Builderportfolio;
import model.FlexiblePortfolioImpl;
import model.Plan;
import model.PlanImpl;
import model.PortfolioImpl;
import model.StockImpl;
import model.User;
import model.UserImpl;

/**
 * Class to read user data from the file in xml format and initialize user object .
 */
public class UserXmlReaderImpl implements XmlReader {

  private final HashSet<String> hashMap = new HashSet<>();

  @Override
  public User readData(String fileName, String password) {
    UserImpl.UserBuilder user = UserImpl.createBuilder();
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

    try {
      // optional, but recommended
      // process XML securely, avoid attacks like XML External Entities (XXE)
      dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

      // parse XML file
      DocumentBuilder db = dbf.newDocumentBuilder();
      File f = new File(fileName);
      if (!f.exists()) {
        return null;
      }
      Document doc = db.parse(f);

      // optional, but recommended
      // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
      doc.getDocumentElement().normalize();

      String expression3 = "/user/username";
      XPath xPath3 = XPathFactory.newInstance().newXPath();
      NodeList nodeList3 = (NodeList) xPath3.compile(expression3).evaluate(
              doc, XPathConstants.NODESET);
      Node n = nodeList3.item(0);
      user.setUserName(n.getTextContent());
      user.setPassword(password);
      user = getPortfolio(user, factoryMethod("portfolio"), doc, "portfolio");
      user = getPortfolio(user, factoryMethod("fportfolio"), doc, "fportfolio");
      user = getPlan(user, doc);
      return user.create();
    } catch (SAXException e) {
      String[] split = fileName.split("/");
      user.setUserName(split[1].substring(0, split[1].indexOf("File")));
      user.setPassword(password);
      return user.create();
    } catch (ParserConfigurationException | IOException | XPathExpressionException e) {
      return null;
    }
  }

  private Builderportfolio factoryMethod(String portType) {
    if (Objects.equals(portType, "portfolio")) {
      return PortfolioImpl.getBuilder();
    } else {
      return FlexiblePortfolioImpl.getBuilder();
    }
  }

  private UserImpl.UserBuilder getPlan(UserImpl.UserBuilder user,
                                       Document doc) throws XPathExpressionException {
    PlanImpl.CustomerBuilder plan;
    String expression1 = "/user/plans/plan";
    XPath xPath1 = XPathFactory.newInstance().newXPath();
    NodeList nodeList1 = (NodeList) xPath1.compile(expression1).evaluate(
            doc, XPathConstants.NODESET);
    plan = PlanImpl.getBuilder();
    List<Plan> planList = new ArrayList<>();

    for (int j = 0; j < nodeList1.getLength(); j++) {
      Node nNode1 = nodeList1.item(j);
      if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
        Element eElement1 = (Element) nNode1;
        NodeList a = eElement1.getChildNodes();
        for (int k = 0; k < a.getLength(); k++) {
          Node currentode1 = a.item(k);
          if (currentode1.getNodeName().equals("name")) {
            plan.portfolioName(currentode1.getTextContent());
          } else if (currentode1.getNodeName().equals("stime")) {
            String date = currentode1.getTextContent();
            String[] yymmdd = date.split("/");
            plan.startDate(LocalDate.of(Integer.parseInt(yymmdd[0]),
                    Integer.parseInt(yymmdd[1]), Integer.parseInt(yymmdd[1])));
          } else if (currentode1.getNodeName().equals("etime")) {
            String date = currentode1.getTextContent();
            String[] yymmdd = date.split("/");
            plan.endDate(LocalDate.of(Integer.parseInt(yymmdd[0]),
                    Integer.parseInt(yymmdd[1]), Integer.parseInt(yymmdd[1])));
          } else if (currentode1.getNodeName().equals("commission")) {
            plan.commissionFees(Float.parseFloat(currentode1.getTextContent()));
          } else if (currentode1.getNodeName().equals("amount")) {
            plan.amount(Float.parseFloat(currentode1.getTextContent()));
          } else if (currentode1.getNodeName().equals("frequency")) {
            plan.frequency(Long.parseLong(currentode1.getTextContent()));
          } else if (currentode1.getNodeName().equals("ongoing")) {
            plan.isOngoing(Boolean.parseBoolean(currentode1.getTextContent()));
          }
        }
        int k = j + 1;
        String expression2 = "/user/plans/plan[" + k + "]/percentage";
        XPath xPath2 = XPathFactory.newInstance().newXPath();
        NodeList nodeList2 = (NodeList) xPath2.compile(expression2).evaluate(
                doc, XPathConstants.NODESET);
        HashMap<String, Float> map = new HashMap<>();
        for (int i = 0; i < nodeList2.getLength(); i++) {
          Node node = nodeList2.item(0);
          if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) node;
            NodeList b = eElement.getChildNodes();
            String key;
            for (int l = 0; l < b.getLength(); l++) {
              Node currentode2 = b.item(l);
              if (currentode2.getNodeName().equals("stock")) {
                key = currentode2.getTextContent();
                String[] keys = key.strip().split("\n");
                if (keys.length == 2) {
                  map.put(keys[0].strip(), Float.parseFloat(keys[1].strip()));
                } else if (keys.length == 3) {
                  map.put(keys[1].strip(), Float.parseFloat(keys[2].strip()));
                }
              }
            }
          }
        }
        plan.hashMap(map);
        PlanImpl p = plan.create();
        planList.add(p);
      }
    }
    planList.removeAll(Collections.singleton(null));
    user.addPlans(planList);
    return user;
  }

  private UserImpl.UserBuilder getPortfolio(UserImpl.UserBuilder user,
                                            Builderportfolio newPortfolio,
                                            Document doc, String portfolioType)
          throws XPathExpressionException {
    StockImpl.CustomerBuilder stock1;
    String expression = "/user/" + portfolioType;
    XPath xPath = XPathFactory.newInstance().newXPath();
    NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(
            doc, XPathConstants.NODESET);

    for (int i = 0; i < nodeList.getLength(); i++) {
      Node nNode = nodeList.item(i);
      if (nNode.getNodeType() == Node.ELEMENT_NODE) {
        Element eElement = (Element) nNode;
        String idName = eElement.getAttribute("id");
        newPortfolio.portfolioName(idName);

        String expression1 = "/user/" + portfolioType + "[@id='" + idName + "']/stock";
        XPath xPath1 = XPathFactory.newInstance().newXPath();
        NodeList nodeList1 = (NodeList) xPath1.compile(expression1).evaluate(
                doc, XPathConstants.NODESET);
        stock1 = StockImpl.getBuilder();

        for (int j = 0; j < nodeList1.getLength(); j++) {
          Node nNode1 = nodeList1.item(j);
          if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement1 = (Element) nNode1;
            stock1.stockSymbol(eElement1.getAttribute("id"));
            NodeList a = eElement1.getChildNodes();
            for (int k = 0; k < a.getLength(); k++) {
              Node currentode1 = a.item(k);
              if (currentode1.getNodeName().equals("cost")) {
                stock1.purchaseValue(Float.parseFloat(currentode1.getTextContent()));
              } else if (currentode1.getNodeName().equals("quantity")) {
                stock1.quantity(Float.parseFloat(currentode1.getTextContent()));
              } else if (currentode1.getNodeName().equals("time")) {
                String date = currentode1.getTextContent();
                String[] yymmdd = date.split("/");
                stock1.purchaseDate(LocalDate.of(Integer.parseInt(yymmdd[0]),
                        Integer.parseInt(yymmdd[1]), Integer.parseInt(yymmdd[1])));
              } else if (currentode1.getNodeName().equals("commission")) {
                stock1.commissionFees(Float.parseFloat(currentode1.getTextContent()));
              }
            }
            newPortfolio.addStocks(stock1.create());
          }
        }

      }
      if (!hashMap.contains(newPortfolio.getportfolioName())) {
        user.addPortfolioList(newPortfolio.create());
        hashMap.add(newPortfolio.getportfolioName());
        newPortfolio = factoryMethod(portfolioType);
      }

    }

    return user;
  }
}


