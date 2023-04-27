package parsers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import model.Plan;
import model.Portfolio;
import model.Stock;
import model.User;

/**
 * Class responsible for writing user object to the file in the xml format.
 */
public class UserXmlWriterImpl implements XmlWriter {

  @Override
  public int writeData(String file, User data) throws ParserConfigurationException {
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

    Document doc = docBuilder.newDocument();
    Element rootElement = doc.createElement("user");
    doc.appendChild(rootElement);

    Element name = doc.createElement("username");
    name.setTextContent(data.getUserName());
    rootElement.appendChild(name);
    Element name1;
    Element name2;
    Element name3;
    Element name4;
    Element name5;
    Element name6;
    Element name7;
    Element name8;
    Element name9;
    Element name10;
    List<Portfolio> portfolioList = data.getAllPortfolios();
    for (Portfolio temp : portfolioList) {
      String portfolio = "portfolio";
      if (temp.isFlexible()) {
        portfolio = "fportfolio";
      }
      name1 = doc.createElement(portfolio);
      name1.setAttribute("id", temp.getPortfolioName());
      List<Stock> temp1 = temp.getAllStocks();
      for (Stock stock : temp1) {
        name2 = doc.createElement("stock");
        name2.setAttribute("id", stock.getStockSymbol());
        name3 = doc.createElement("cost");
        name3.setTextContent(String.valueOf(stock.getPurchaseValue()));
        name2.appendChild(name3);
        name4 = doc.createElement("quantity");
        name4.setTextContent(String.valueOf(stock.getQuantity()));
        name2.appendChild(name4);
        name5 = doc.createElement("time");
        name5.setTextContent(String.valueOf(stock.getPurchaseDate()).replace("-", "/"));
        name2.appendChild(name5);
        name6 = doc.createElement("symbol");
        name6.setTextContent(String.valueOf(stock.getStockSymbol()));
        name2.appendChild(name6);
        if (temp.isFlexible()) {
          name7 = doc.createElement("commission");
          name7.setTextContent(String.valueOf(stock.getCommissionFees()));
          name2.appendChild(name7);
        }
        name1.appendChild(name2);
      }
      rootElement.appendChild(name1);
    }
    name1 = doc.createElement("plans");
    for (Plan plan : data.getPlans()) {
      name2 = doc.createElement("plan");
      name3 = doc.createElement("name");
      name3.setTextContent(plan.getPortfolioName());
      name2.appendChild(name3);
      name4 = doc.createElement("stime");
      name4.setTextContent(String.valueOf(plan.getStartDate()).replace("-", "/"));
      name2.appendChild(name4);
      name5 = doc.createElement("etime");
      name5.setTextContent(String.valueOf(plan.getEndDate()).replace("-", "/"));
      name2.appendChild(name5);
      name6 = doc.createElement("ongoing");
      name6.setTextContent(String.valueOf(plan.isOngoing()));
      name2.appendChild(name6);
      name7 = doc.createElement("amount");
      name7.setTextContent(String.valueOf(plan.getAmount()));
      name2.appendChild(name7);
      name8 = doc.createElement("frequency");
      name8.setTextContent(String.valueOf(plan.getFrequency()));
      name2.appendChild(name8);
      name9 = doc.createElement("commission");
      name9.setTextContent(String.valueOf(plan.getCommissionFees()));
      name2.appendChild(name9);
      name10 = doc.createElement("percentage");
      for (String key : plan.getPercentage().keySet()) {
        Element symbol = doc.createElement("stock");
        symbol.setTextContent(key);
        Element percent = doc.createElement("percent");
        percent.setTextContent(String.valueOf(plan.getPercentage().get(key)));
        symbol.appendChild(percent);
        name10.appendChild(symbol);
      }
      name2.appendChild(name10);
      name1.appendChild(name2);
      rootElement.appendChild(name1);
    }

    try (FileOutputStream output =
                 new FileOutputStream(file)) {
      writeXml(doc, output);
    } catch (IOException | TransformerException e) {
      e.printStackTrace();
    }
    return 0;
  }

  private static void writeXml(Document doc,
                               OutputStream output)
          throws TransformerException {

    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();

    // pretty print
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");

    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(output);

    transformer.transform(source, result);

  }
}
