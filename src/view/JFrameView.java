package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;

//import javax.swing.

import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;

import controller.Features;
import edu.princeton.cs.algs4.StdDraw;
import model.Stock;
import utilities.Utility;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

/**
 * GUI view representing all the operations present in the application.
 */
public class JFrameView extends JFrame implements GuiView {
  JLabel display;
  JTextField input;
  JLabel display1;
  JTextField input1;
  JButton loginButton;
  JButton[] options;
  JFrame newScreen;
  String filePath = "";
  List<String> operationsList;
  Features features;
  JPanel rightPanel;
  JTextField total;
  JPanel buttonPanel;
  JLabel value;
  JLabel totalValue;
  JComboBox<String> comboBox;
  JButton showPerformance;
  JTable table = new JTable();
  JScrollPane tableScoll = new JScrollPane();
  String[] portfolioList;
  JTextField symbol;
  JTextField percent;
  JTextField quantity;
  JComboBox<String> date;
  JComboBox<String> month;
  JComboBox<String> year;
  JTextField fees;

  JScrollPane mainScrollPane;
  JPanel stockPanel;

  List<JButton> buttonGroup = new ArrayList<>();

  private JPanel mainPanel;

  String[] futureYears = {"----", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002",
    "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010",
    "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022",
    "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034",
    "2035", "2036", "2037", "2038"};

  String[] years = {"1995", "1996", "1997", "1998",
    "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006",
    "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014",
    "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022"};

  public JFrameView() {
    newScreen = new JFrame();
    loginpage();
  }

  private void jFrameViewScreen(String title) {
    switch (title) {
      case "Stocks":
      case "logout":
        loginpage();
        break;
      case "Operations":
        afterLogin();
        break;
      case "Compute":
        computePortfolioForm();
        break;
      case "addPortfolio":
        addPortfolio();
        break;
      case "buyStock":
        buyStockForm();
        break;
      case "sellStock":
        sellStockForm();
        break;
      case "examinePortfolio":
        examinePortfolio();
        break;
      case "ComputeBasis":
        computeCostBasisForm();
        break;
      case "addPlan":
        addPlan();
        break;
      case "Performance":
        performanceScreen();
        break;
      default:
        break;
    }
    newScreen.setVisible(true);
  }

  @Override
  public void transition(String transScreen) {
    if (!transScreen.equals("Operations")) {
      rightPanel.removeAll();
    }
    if (transScreen.equals("logout")) {
      newScreen.dispose();
    }
    if (transScreen.equals("Operations")) {
      newScreen.dispose();
    }
    jFrameViewScreen(transScreen);
  }

  @Override
  public void addFeatures(Features features) {
    this.features = features;
    operationsList = features.operationsList();
    options = new JButton[operationsList.size()];

    for (int i = 0; i < options.length; i++) {
      String opt = operationsList.get(i);
      options[i] = new JButton(opt);
      options[i].addActionListener(evt -> features.options(opt));
    }
  }

  private void setFieldBackground(JComponent input, boolean shouldYieldFocus) {
    Color bg = shouldYieldFocus ? Color.GREEN : Color.RED;
    input.setForeground(bg);
  }

  static class MyFocusCheck extends FocusAdapter {
    private final JFrameView gui;
    private boolean flag = false;

    public MyFocusCheck(JFrameView gui) {
      this.gui = gui;
    }

    @Override
    public void focusGained(FocusEvent e) {
      flag = true;
    }

    @Override
    public void focusLost(FocusEvent e) {
      if (flag) {
        JTextComponent textComp = (JTextComponent) e.getSource();
        String text = textComp.getText();
        boolean verified = gui.verifyText(text, textComp.getToolTipText());
        gui.setFieldBackground(textComp, verified);
      }
    }
  }

  private boolean verifyText(String text, String type) {
    return features.validate(text, type);
  }

  @Override
  public String getinputString() {
    return input.getText();
  }

  @Override
  public String getinput1String() {
    return input1.getText();
  }


  private void loginpage() {
    newScreen = new JFrame();
    newScreen.setSize(450, 200);
    newScreen.setLocation(200, 200);
    newScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    newScreen.setResizable(false);

    newScreen.setLayout(new FlowLayout());

    display = new JLabel("Username:");
    input = new JTextField(10);

    display1 = new JLabel("Password:");

    input1 = new JPasswordField(10);

    loginButton = new JButton("Login");
    loginButton.addActionListener(evt -> features.login());

    JButton newUserButton = new JButton("Create a new user");
    newUserButton.addActionListener(evt -> createUser());

    newScreen.add(display);
    newScreen.add(input);
    newScreen.add(display1);
    newScreen.add(input1);
    newScreen.add(loginButton);
    newScreen.add(newUserButton);
    newScreen.pack();
    newScreen.setVisible(true);
  }

  private void createUser() {
    newScreen.dispose();
    newScreen = new JFrame();
    newScreen.setSize(450, 600);
    newScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    newScreen.setResizable(false);
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

    JButton backButton = new JButton("Back");
    backButton.addActionListener(evt -> {
      newScreen.dispose();
      loginpage();
    });
    backButton.setSize(50, 40);
    backButton.setLocation(-1, 0);
    JPanel backPanel = new JPanel();
    backPanel.setLayout(null);
    backPanel.add(backButton);
    backPanel.setMaximumSize(new Dimension(600, 100));
    panel.add(backPanel);

    mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayout(12, 0, 4, 0));

    JLabel title = new JLabel("New User Signup", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.PLAIN, 20));
    title.setForeground(Color.BLUE);
    title.setSize(300, 30);
    title.setLocation(200, 30);

    display = new JLabel("Username:");
    input = new JTextField(10);

    display1 = new JLabel("Password:");

    input1 = new JPasswordField(10);

    JTextField message = new JTextField();
    message.setVisible(false);

    JButton fileOpenButton = new JButton("Upload User Portfolio");
    fileOpenButton.addActionListener(evt -> {
      filePath = fileOpener();

      if (filePath != null && !filePath.equals("")) {
        message.setText(filePath);
        message.setVisible(true);
        message.setEnabled(false);
      }
    });

    loginButton = new JButton("signIn");

    loginButton.addActionListener(evt ->
            features.createUser(input.getText(), input1.getText(), filePath));

    mainPanel.setMaximumSize(new Dimension(200, 300));
    mainPanel.add(title);

    mainPanel.add(display);
    mainPanel.add(input);
    mainPanel.add(display1);
    mainPanel.add(input1);
    mainPanel.add(Box.createRigidArea(new Dimension(5, 0)));
    mainPanel.add(message);
    mainPanel.add(Box.createRigidArea(new Dimension(5, 0)));
    mainPanel.add(fileOpenButton);
    mainPanel.add(Box.createRigidArea(new Dimension(5, 0)));
    mainPanel.add(loginButton);
    panel.add(mainPanel);
    newScreen.add(panel);
    newScreen.setVisible(true);


  }

  private String fileOpener() {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Text files", "txt");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(newScreen);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      String[] path = f.getPath().split("/");
      return path[path.length - 1];
    }
    return null;
  }

  private void examinePortfolio() {
    fillPortfolioList();
    rightPanel.setLayout(null);

    JLabel title = new JLabel("Examine Portfolio", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.PLAIN, 30));
    title.setSize(300, 30);
    title.setLocation(100, 30);
    rightPanel.add(title);

    JLabel portfolioName = new JLabel("Please Select a portfolio");
    portfolioName.setFont(new Font("Arial", Font.PLAIN, 13));
    portfolioName.setSize(170, 20);
    portfolioName.setLocation(100, 100);
    rightPanel.add(portfolioName);

    comboBox = new JComboBox<>(portfolioList);
    comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
    comboBox.setSize(150, 20);
    comboBox.setLocation(280, 100);
    rightPanel.add(comboBox);

    comboBox.addActionListener(evt ->
            features.displayPortfolio(String.valueOf(comboBox.getSelectedItem())));
  }


  @Override
  public void popUp(String error) {
    JOptionPane.showMessageDialog(newScreen,
            error, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void table(String[][] data, String[] col, String port) {
    if (table.getParent() == tableScoll) {
      rightPanel.remove(table);
    }
    table = new JTable(data, col);
    table.setLayout(new BoxLayout(table, BoxLayout.PAGE_AXIS));
    table.setBounds(100, 150, 200, 200);
    table.setForeground(Color.RED);
    table.setShowGrid(true);
    table.setGridColor(Color.BLACK);
    tableScoll = new JScrollPane();
    TitledBorder centerBorder = BorderFactory.createTitledBorder(port);
    centerBorder.setTitleJustification(TitledBorder.CENTER);
    tableScoll.setBorder(centerBorder);
    tableScoll.setViewportView(table);
    tableScoll.setBounds(150, 150, 200, 200);
    rightPanel.add(tableScoll);
    tableScoll.repaint();
  }

  @Override
  public void popUpSuccess(String error) {
    JOptionPane.showMessageDialog(newScreen,
            error, "Success", JOptionPane.INFORMATION_MESSAGE);
  }

  private void performanceScreen() {
    fillPortfolioList();
    rightPanel.setLayout(null);

    JLabel title = new JLabel("Performance", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.PLAIN, 30));
    title.setSize(300, 30);
    title.setLocation(100, 30);
    rightPanel.add(title);

    JLabel portfolioName = new JLabel("Portfolio Name");
    portfolioName.setFont(new Font("Arial", Font.PLAIN, 12));
    portfolioName.setSize(100, 20);
    portfolioName.setLocation(100, 100);
    rightPanel.add(portfolioName);

    comboBox = new JComboBox<>(portfolioList);
    comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
    comboBox.setSize(190, 20);
    comboBox.setLocation(200, 100);
    rightPanel.add(comboBox);

    JLabel dop = new JLabel("Start Date");
    dop.setFont(new Font("Arial", Font.PLAIN, 12));
    dop.setSize(100, 20);
    dop.setLocation(100, 150);
    rightPanel.add(dop);

    String[] dates = {"-", "1", "2", "3", "4", "5",
      "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
      "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
      "26", "27", "28", "29", "30", "31"};

    String[] months = {"--", "01", "02", "03", "04", "05", "06", "07", "08",
      "09", "10", "11", "12"};

    JComboBox<String> date = new JComboBox<>(dates);
    date.setFont(new Font("Arial", Font.PLAIN, 12));
    date.setSize(80, 20);
    date.setLocation(200, 150);
    date.setToolTipText("Enter the performance start date");
    rightPanel.add(date);


    JComboBox<String> month = new JComboBox<>(months);
    month.setFont(new Font("Arial", Font.PLAIN, 12));
    month.setSize(80, 20);
    month.setLocation(290, 150);
    month.setToolTipText("Enter the performance start month");
    rightPanel.add(month);

    JComboBox<String> year = new JComboBox<>(years);
    year.setFont(new Font("Arial", Font.PLAIN, 12));
    year.setSize(100, 20);
    year.setLocation(370, 150);
    year.setToolTipText("Enter the performance start year");
    rightPanel.add(year);

    JLabel end = new JLabel("End Date");
    end.setFont(new Font("Arial", Font.PLAIN, 12));
    end.setSize(100, 20);
    end.setLocation(100, 200);
    rightPanel.add(end);

    JComboBox<String> date1 = new JComboBox<>(dates);
    date1.setFont(new Font("Arial", Font.PLAIN, 12));
    date1.setSize(80, 20);
    date1.setLocation(200, 200);
    date1.setToolTipText("Enter the performance end date");
    rightPanel.add(date1);


    JComboBox<String> month1 = new JComboBox<>(months);
    month1.setFont(new Font("Arial", Font.PLAIN, 12));
    month1.setSize(80, 20);
    month1.setLocation(290, 200);
    month1.setToolTipText("Enter the performance end month");
    rightPanel.add(month1);

    JComboBox<String> year1 = new JComboBox<>(years);
    year1.setFont(new Font("Arial", Font.PLAIN, 12));
    year1.setSize(100, 20);
    year1.setLocation(370, 200);
    year1.setToolTipText("Enter the performance start year");
    rightPanel.add(year1);

    showPerformance = new JButton("showPerformance");
    showPerformance.setFont(new Font("Arial", Font.PLAIN, 15));
    showPerformance.setSize(200, 20);
    showPerformance.setLocation(150, 250);
    showPerformance.addActionListener(evt ->
            features.performance(
                    Utility.validateDates("" + date.getSelectedItem() + "-"
                            + month.getSelectedItem() + "-" + year.getSelectedItem()),
                    Utility.validateDates("" + date1.getSelectedItem() + "-"
                            + month1.getSelectedItem() + "-" + year1.getSelectedItem()),
                    String.valueOf(comboBox.getSelectedItem())));
    rightPanel.add(showPerformance);

  }

  @Override
  public void performance(BarChart chart) {
    try {
      StdDraw.setCanvasSize(1000, 700);
      StdDraw.enableDoubleBuffering();
      chart.draw();
      StdDraw.show();
    } catch (Exception e) {
      popUp("No data available to plot");
    }

  }

  private void afterLogin() {
    newScreen = new JFrame();
    newScreen.setSize(900, 700);
    newScreen.setLocation(200, 200);
    newScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    newScreen.setResizable(false);


    mainPanel = new JPanel();

    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

    buttonPanel = new JPanel();

    TitledBorder centerBorder = BorderFactory.createTitledBorder("List Of Operations");
    centerBorder.setTitleJustification(TitledBorder.CENTER);
    centerBorder.setTitleFont(new Font("Arial", Font.PLAIN, 16));
    buttonPanel.setBorder(centerBorder);
    buttonPanel.setMaximumSize(new Dimension(100, 700));

    buttonPanel.setLayout(new GridLayout(9, 0));

    for (JButton button : options) {
      buttonPanel.add(button);
    }

    rightPanel = new JPanel();

    rightPanel.add(new Label("Hello"));
    mainPanel.add(buttonPanel);
    mainPanel.add(rightPanel);
    newScreen.add(mainPanel);

  }

  private void fillPortfolioList() {
    portfolioList = new String[features.getPortfolios().size()];
    features.getPortfolios().toArray(portfolioList);
    newScreen.repaint();
  }

  private void buyStockForm() {
    fillPortfolioList();
    rightPanel.setLayout(null);

    JLabel title = new JLabel("Buy Stocks", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.PLAIN, 30));
    title.setSize(300, 30);
    title.setLocation(100, 30);
    rightPanel.add(title);

    JLabel portfolioName = new JLabel("Portfolio Name");
    portfolioName.setFont(new Font("Arial", Font.PLAIN, 12));
    portfolioName.setSize(100, 20);
    portfolioName.setLocation(100, 100);
    rightPanel.add(portfolioName);

    comboBox = new JComboBox<>(portfolioList);
    comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
    comboBox.setSize(190, 20);
    comboBox.setLocation(200, 100);
    rightPanel.add(comboBox);

    JLabel stockName = new JLabel("Stock Symbol");
    stockName.setFont(new Font("Arial", Font.PLAIN, 12));
    stockName.setSize(100, 20);
    stockName.setLocation(100, 150);
    rightPanel.add(stockName);

    JTextField tmno = new JTextField();
    tmno.setFont(new Font("Arial", Font.PLAIN, 12));
    tmno.setSize(100, 20);
    tmno.setLocation(200, 150);
    tmno.setToolTipText("Please add a valid Stock symbol");
    tmno.addFocusListener(new MyFocusCheck(this));
    rightPanel.add(tmno);

    JLabel quantity = new JLabel("Quantity");
    quantity.setFont(new Font("Arial", Font.PLAIN, 12));
    quantity.setSize(100, 20);
    quantity.setLocation(100, 200);
    rightPanel.add(quantity);

    JTextField count = new JTextField();
    count.setFont(new Font("Arial", Font.PLAIN, 12));
    count.setSize(100, 20);
    count.setLocation(200, 200);
    count.setToolTipText("Please enter a quantity greater than 1");
    count.addFocusListener(new MyFocusCheck(this));
    rightPanel.add(count);

    JLabel dop = new JLabel("Date of Purchase");
    dop.setFont(new Font("Arial", Font.PLAIN, 12));
    dop.setSize(100, 20);
    dop.setLocation(100, 250);
    rightPanel.add(dop);

    String[] dates = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
        "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
        "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    String[] months = {"01", "02", "03", "04",
      "05", "06", "07", "08", "09", "10", "11", "12"};

    JComboBox<String> date = new JComboBox<>(dates);
    date.setFont(new Font("Arial", Font.PLAIN, 12));
    date.setSize(80, 20);
    date.setLocation(200, 250);
    date.setToolTipText("Enter the day of purchase");
    rightPanel.add(date);


    JComboBox<String> month = new JComboBox<>(months);
    month.setFont(new Font("Arial", Font.PLAIN, 12));
    month.setSize(80, 20);
    month.setLocation(290, 250);
    month.setToolTipText("Enter the month of purchase");
    rightPanel.add(month);

    JComboBox<String> year = new JComboBox<>(years);
    year.setFont(new Font("Arial", Font.PLAIN, 12));
    year.setSize(100, 20);
    year.setLocation(370, 250);
    year.setToolTipText("Enter the year of purchase");
    rightPanel.add(year);

    JLabel add = new JLabel("Commission Fees");
    add.setFont(new Font("Arial", Font.PLAIN, 12));
    add.setSize(100, 20);
    add.setLocation(100, 300);
    rightPanel.add(add);

    JTextField fees = new JTextField();
    fees.setFont(new Font("Arial", Font.PLAIN, 12));
    fees.setSize(100, 20);
    fees.setLocation(200, 300);
    fees.setToolTipText("Please add a commission fees between $2 and $40");
    fees.addFocusListener(new MyFocusCheck(this));
    rightPanel.add(fees);

    JCheckBox term = new JCheckBox("Accept Terms And Conditions.");
    term.setFont(new Font("Arial", Font.PLAIN, 12));
    term.setSize(250, 20);
    term.setLocation(150, 400);
    rightPanel.add(term);

    JButton sub = new JButton("Submit");
    sub.setFont(new Font("Arial", Font.PLAIN, 15));
    sub.setSize(100, 20);
    sub.setLocation(150, 450);
    rightPanel.add(sub);
    sub.addActionListener(evt -> features.addStocksToPortfolio(
            String.valueOf(comboBox.getSelectedItem()), tmno.getText(), count.getText(),
            String.valueOf(date.getSelectedItem()), String.valueOf(month.getSelectedItem()),
            String.valueOf(year.getSelectedItem()), fees.getText()));

    JButton reset = new JButton("Reset");
    reset.setFont(new Font("Arial", Font.PLAIN, 15));
    reset.setSize(100, 20);
    reset.setLocation(270, 450);
    reset.addActionListener(evt -> transition("buyStock"));
    rightPanel.add(reset);

  }

  private void sellStockForm() {
    fillPortfolioList();
    rightPanel.setLayout(null);

    JLabel title = new JLabel("Sell Stocks", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.PLAIN, 30));
    title.setSize(300, 30);
    title.setLocation(100, 30);
    rightPanel.add(title);

    JLabel portfolioName = new JLabel("Portfolio Name");
    portfolioName.setFont(new Font("Arial", Font.PLAIN, 12));
    portfolioName.setSize(100, 20);
    portfolioName.setLocation(100, 100);
    rightPanel.add(portfolioName);

    comboBox = new JComboBox<>(portfolioList);
    comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
    comboBox.setSize(190, 20);
    comboBox.setLocation(200, 100);
    rightPanel.add(comboBox);

    JLabel stockName = new JLabel("Stock Symbol");
    stockName.setFont(new Font("Arial", Font.PLAIN, 12));
    stockName.setSize(100, 20);
    stockName.setLocation(100, 150);
    rightPanel.add(stockName);

    JTextField tmno = new JTextField();
    tmno.setFont(new Font("Arial", Font.PLAIN, 12));
    tmno.setSize(100, 20);
    tmno.setLocation(200, 150);
    tmno.setToolTipText("Please add a valid Stock symbol");
    tmno.addFocusListener(new MyFocusCheck(this));
    rightPanel.add(tmno);

    JLabel quantity = new JLabel("Quantity");
    quantity.setFont(new Font("Arial", Font.PLAIN, 12));
    quantity.setSize(100, 20);
    quantity.setLocation(100, 200);
    rightPanel.add(quantity);

    JTextField count = new JTextField();
    count.setFont(new Font("Arial", Font.PLAIN, 12));
    count.setSize(100, 20);
    count.setLocation(200, 200);
    count.setToolTipText("Please enter a quantity greater than 1");
    count.addFocusListener(new MyFocusCheck(this));
    rightPanel.add(count);

    JLabel dop = new JLabel("Date Sold");
    dop.setFont(new Font("Arial", Font.PLAIN, 12));
    dop.setSize(100, 20);
    dop.setLocation(100, 250);
    rightPanel.add(dop);

    String[] dates = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
        "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
        "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    String[] months = {"01", "02", "03", "04", "05", "06", "07", "08",
      "09", "10", "11", "12"};

    String[] years = {"1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002",
      "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010",
      "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021",
      "2022"};

    JComboBox<String> date = new JComboBox<>(dates);
    date.setFont(new Font("Arial", Font.PLAIN, 12));
    date.setSize(80, 20);
    date.setLocation(200, 250);
    date.setToolTipText("Enter the day sold");
    rightPanel.add(date);

    JComboBox<String> month = new JComboBox<>(months);
    month.setFont(new Font("Arial", Font.PLAIN, 12));
    month.setSize(80, 20);
    month.setLocation(290, 250);
    month.setToolTipText("Enter the month the stock was sold");
    rightPanel.add(month);

    JComboBox<String> year = new JComboBox<>(years);
    year.setFont(new Font("Arial", Font.PLAIN, 12));
    year.setSize(100, 20);
    year.setLocation(370, 250);
    year.setToolTipText("Enter the year the stock was sold");
    rightPanel.add(year);

    JLabel add = new JLabel("Commission Fees");
    add.setFont(new Font("Arial", Font.PLAIN, 12));
    add.setSize(100, 20);
    add.setLocation(100, 300);
    rightPanel.add(add);

    JTextField fees = new JTextField();
    fees.setFont(new Font("Arial", Font.PLAIN, 12));
    fees.setSize(100, 20);
    fees.setLocation(200, 300);
    fees.setToolTipText("Please add a commission fees between $2 and $40");
    fees.addFocusListener(new MyFocusCheck(this));
    rightPanel.add(fees);

    JCheckBox term = new JCheckBox("Accept Terms And Conditions.");
    term.setFont(new Font("Arial", Font.PLAIN, 12));
    term.setSize(250, 20);
    term.setLocation(150, 400);
    rightPanel.add(term);

    JButton sub = new JButton("Submit");
    sub.setFont(new Font("Arial", Font.PLAIN, 15));
    sub.setSize(100, 20);
    sub.setLocation(150, 450);
    rightPanel.add(sub);
    sub.addActionListener(evt -> features.sellStockFromPortfolio(
            String.valueOf(comboBox.getSelectedItem()), tmno.getText(), count.getText(),
            String.valueOf(date.getSelectedItem()), String.valueOf(month.getSelectedItem()),
            String.valueOf(year.getSelectedItem()), fees.getText()));

    JButton reset = new JButton("Reset");
    reset.setFont(new Font("Arial", Font.PLAIN, 15));
    reset.setSize(100, 20);
    reset.setLocation(270, 450);
    reset.addActionListener(evt -> transition("sellStock"));
    rightPanel.add(reset);

  }

  private void addPlan() {
    fillPortfolioList();
    rightPanel.setLayout(null);

    JLabel title = new JLabel("Add Plan", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.PLAIN, 30));
    title.setSize(300, 30);
    title.setLocation(100, 30);
    rightPanel.add(title);

    JLabel portfolioName = new JLabel("Portfolio Name");
    portfolioName.setFont(new Font("Arial", Font.PLAIN, 12));
    portfolioName.setSize(100, 20);
    portfolioName.setLocation(100, 100);
    rightPanel.add(portfolioName);

    JTextField pName = new JTextField();
    pName.setFont(new Font("Arial", Font.PLAIN, 12));
    pName.setSize(190, 20);
    pName.setLocation(200, 100);
    pName.setToolTipText("Enter a portfolio name to apply the plan. " +
            "New portfolio will be created if the portfolio does not exist");
    pName.addFocusListener(new MyFocusCheck(this));
    rightPanel.add(pName);

    JLabel dop = new JLabel("Plan Start Date");
    dop.setFont(new Font("Arial", Font.PLAIN, 12));
    dop.setSize(100, 20);
    dop.setLocation(100, 150);
    rightPanel.add(dop);

    String[] dates = {"-", "1", "2", "3", "4", "5",
      "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
      "16", "17", "18", "19", "20", "21", "22", "23", "24", "25",
      "26", "27", "28", "29", "30", "31"};

    String[] months = {"--", "01", "02", "03", "04", "05", "06", "07", "08",
      "09", "10", "11", "12"};

    JComboBox<String> date = new JComboBox<>(dates);
    date.setFont(new Font("Arial", Font.PLAIN, 12));
    date.setSize(80, 20);
    date.setLocation(200, 150);
    date.setToolTipText("Enter the Plan start day");
    rightPanel.add(date);

    JComboBox<String> month = new JComboBox<>(months);
    month.setFont(new Font("Arial", Font.PLAIN, 12));
    month.setSize(80, 20);
    month.setLocation(290, 150);
    month.setToolTipText("Enter the Plan start month");
    rightPanel.add(month);

    JComboBox<String> year = new JComboBox<>(futureYears);
    year.setFont(new Font("Arial", Font.PLAIN, 12));
    year.setSize(100, 20);
    year.setLocation(370, 150);
    year.setToolTipText("Enter the Plan start year");
    rightPanel.add(year);

    JLabel doe = new JLabel("Plan End Date");
    doe.setFont(new Font("Arial", Font.PLAIN, 12));
    doe.setSize(100, 20);
    doe.setLocation(100, 200);
    rightPanel.add(doe);

    JComboBox<String> edate = new JComboBox<>(dates);
    edate.setFont(new Font("Arial", Font.PLAIN, 12));
    edate.setSize(80, 20);
    edate.setLocation(200, 200);
    edate.setToolTipText("Enter the Plan end day");
    rightPanel.add(edate);

    JComboBox<String> eMonth = new JComboBox<>(months);
    eMonth.setFont(new Font("Arial", Font.PLAIN, 12));
    eMonth.setSize(80, 20);
    eMonth.setLocation(290, 200);
    eMonth.setToolTipText("Enter the Plan end month");
    rightPanel.add(eMonth);

    JComboBox<String> eYear = new JComboBox<>(futureYears);
    eYear.setFont(new Font("Arial", Font.PLAIN, 12));
    eYear.setSize(100, 20);
    eYear.setLocation(370, 200);
    eYear.setToolTipText("Enter the Plan end year");
    rightPanel.add(eYear);

    JLabel add = new JLabel("Per Time Investment");
    add.setFont(new Font("Arial", Font.PLAIN, 12));
    add.setSize(100, 20);
    add.setLocation(100, 250);
    rightPanel.add(add);

    JTextField amount = new JTextField();
    amount.setFont(new Font("Arial", Font.PLAIN, 12));
    amount.setSize(100, 20);
    amount.setLocation(200, 250);
    eYear.setToolTipText("Enter the investment made per cycle");
    rightPanel.add(amount);

    JLabel freqLabel = new JLabel("Cycle Period");
    freqLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    freqLabel.setSize(100, 20);
    freqLabel.setLocation(100, 300);
    rightPanel.add(freqLabel);

    JTextField frequency = new JTextField();
    frequency.setFont(new Font("Arial", Font.PLAIN, 12));
    frequency.setSize(100, 20);
    frequency.setLocation(200, 300);
    frequency.setToolTipText("Enter the cycle period for investment in days");
    rightPanel.add(frequency);

    JLabel feeLabel = new JLabel("Commission Fees");
    feeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    feeLabel.setSize(100, 20);
    feeLabel.setLocation(100, 350);

    rightPanel.add(feeLabel);

    JTextField fees = new JTextField();
    fees.setFont(new Font("Arial", Font.PLAIN, 12));
    fees.setSize(100, 20);
    fees.setLocation(200, 350);
    fees.setToolTipText("Please add a commission fees between $2 and $40");
    fees.addFocusListener(new MyFocusCheck(this));
    rightPanel.add(fees);

    JLabel stock = new JLabel("Stock Details", SwingConstants.CENTER);
    stock.setFont(new Font("Arial", Font.PLAIN, 20));
    stock.setSize(300, 30);
    stock.setLocation(100, 400);
    rightPanel.add(stock);

    JLabel totalLabel = new JLabel("Total Percentage");
    totalLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    totalLabel.setSize(100, 20);
    totalLabel.setLocation(350, 410);
    rightPanel.add(totalLabel);

    total = new JTextField("0.0");
    total.setFont(new Font("Arial", Font.PLAIN, 12));
    total.setSize(100, 20);
    total.setLocation(450, 410);
    total.setEditable(false);
    rightPanel.add(total);

    JLabel symbolLabel = new JLabel("Stock Symbol");
    symbolLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    symbolLabel.setSize(100, 20);
    symbolLabel.setLocation(100, 450);
    rightPanel.add(symbolLabel);

    symbol = new JTextField();
    symbol.setFont(new Font("Arial", Font.PLAIN, 12));
    symbol.setSize(100, 20);
    symbol.setLocation(200, 450);
    symbol.setToolTipText("Please add a valid Stock symbol");
    symbol.addFocusListener(new MyFocusCheck(this));
    rightPanel.add(symbol);

    JLabel percentLabel = new JLabel("Stock Percentage");
    percentLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    percentLabel.setSize(100, 20);
    percentLabel.setLocation(100, 500);
    rightPanel.add(percentLabel);

    percent = new JTextField();
    percent.setFont(new Font("Arial", Font.PLAIN, 12));
    percent.setSize(100, 20);
    percent.setLocation(200, 500);
    percent.setToolTipText("Enter the percentage between 0 and 100");
    rightPanel.add(percent);

    stockPanel = new JPanel();

    stockPanel.setLayout(new BoxLayout(stockPanel, BoxLayout.PAGE_AXIS));
    stockPanel.setBounds(100, 150, 200, 200);
    stockPanel.setForeground(Color.RED);

    tableScoll = new JScrollPane();
    TitledBorder centerBorder = BorderFactory.createTitledBorder("Stocks");
    centerBorder.setTitleFont(new Font("Arial", Font.PLAIN, 8));
    centerBorder.setTitleJustification(TitledBorder.CENTER);
    tableScoll.setBorder(centerBorder);
    tableScoll.setViewportView(stockPanel);
    tableScoll.setBounds(400, 450, 100, 200);
    rightPanel.add(tableScoll);

    tableScoll.repaint();

    JButton next = new JButton("Add Stock");
    next.setFont(new Font("Arial", Font.PLAIN, 10));
    next.setSize(60, 18);
    next.setLocation(200, 550);
    next.addActionListener(evt -> {
      float p = features.updatePercent(symbol.getText(), percent.getText());
      setTotal(p);
      enableButtons();
      tableScoll.repaint();
    });
    rightPanel.add(next);

    JButton sub = new JButton("Submit");
    sub.setFont(new Font("Arial", Font.PLAIN, 15));
    sub.setSize(100, 20);
    sub.setLocation(150, 600);
    rightPanel.add(sub);
    sub.addActionListener(evt -> features.addPlan(String.valueOf(date.getSelectedItem()),
            String.valueOf(month.getSelectedItem()), String.valueOf(year.getSelectedItem()),
            String.valueOf(edate.getSelectedItem()), String.valueOf(eMonth.getSelectedItem()),
            String.valueOf(eYear.getSelectedItem()), pName.getText(),
            amount.getText(), fees.getText(), frequency.getText()));

    JButton reset = new JButton("Reset");
    reset.setFont(new Font("Arial", Font.PLAIN, 15));
    reset.setSize(100, 20);
    reset.setLocation(270, 600);
    reset.addActionListener(evt -> transition("addPlan"));
    rightPanel.add(reset);
  }

  @Override
  public void addStockButtons(String symbol, String percent) {
    JButton b4 = new JButton(symbol + " : " + percent);
    stockPanel.add(b4);
    tableScoll.repaint();
  }

  private void addPortfolio() {
    fillPortfolioList();
    rightPanel.setLayout(null);

    JLabel title = new JLabel("Add Portfolio", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.PLAIN, 30));
    title.setSize(300, 30);
    title.setLocation(100, 30);
    rightPanel.add(title);

    JLabel portfolioName = new JLabel("Portfolio Name");
    portfolioName.setFont(new Font("Arial", Font.PLAIN, 12));
    portfolioName.setSize(100, 20);
    portfolioName.setLocation(100, 100);
    rightPanel.add(portfolioName);

    JTextField pName = new JTextField();
    pName.setFont(new Font("Arial", Font.PLAIN, 12));
    pName.setSize(190, 20);
    pName.setLocation(200, 100);
    pName.setToolTipText("Please add a new portfolio name");
    pName.addFocusListener(new MyFocusCheck(this));
    rightPanel.add(pName);

    JLabel dop = new JLabel("Purchase Date");
    dop.setFont(new Font("Arial", Font.PLAIN, 12));
    dop.setSize(100, 20);
    dop.setLocation(100, 300);
    rightPanel.add(dop);

    String[] dates = {"-", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
      "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
      "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    String[] months = {"--", "1", "2", "3", "4", "5", "6", "7", "8",
      "9", "10", "11", "12"};
    String[] years = {"----", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002",
      "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010",
      "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018",
      "2019", "2020", "2021", "2022"};

    date = new JComboBox<>(dates);
    date.setFont(new Font("Arial", Font.PLAIN, 12));
    date.setSize(80, 20);
    date.setLocation(200, 300);
    date.setToolTipText("Enter the purchase day");
    rightPanel.add(date);

    month = new JComboBox<>(months);
    month.setFont(new Font("Arial", Font.PLAIN, 12));
    month.setSize(80, 20);
    month.setLocation(290, 300);
    month.setToolTipText("Enter the purchase month");
    rightPanel.add(month);

    year = new JComboBox<>(years);
    year.setFont(new Font("Arial", Font.PLAIN, 12));
    year.setSize(100, 20);
    year.setLocation(370, 300);
    year.setToolTipText("Enter the purchase year");
    rightPanel.add(year);

    JLabel feeLabel = new JLabel("Commission Fees");
    feeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    feeLabel.setSize(100, 20);
    feeLabel.setLocation(100, 350);
    rightPanel.add(feeLabel);

    fees = new JTextField();
    fees.setFont(new Font("Arial", Font.PLAIN, 12));
    fees.setSize(100, 20);
    fees.setLocation(200, 350);
    fees.setToolTipText("Please add a commission fees between $2 and $40");
    fees.addFocusListener(new MyFocusCheck(this));
    rightPanel.add(fees);

    JLabel stockL = new JLabel("Stock Details", SwingConstants.CENTER);
    stockL.setFont(new Font("Arial", Font.PLAIN, 20));
    stockL.setSize(300, 30);
    stockL.setLocation(100, 150);
    rightPanel.add(stockL);

    JLabel totalLabel = new JLabel("Number of Stocks");
    totalLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    totalLabel.setSize(100, 20);
    totalLabel.setLocation(350, 160);
    rightPanel.add(totalLabel);

    total = new JTextField("0");
    total.setFont(new Font("Arial", Font.PLAIN, 12));
    total.setSize(100, 20);
    total.setLocation(450, 160);
    total.setToolTipText("Total number of stocks entered in portfolio so far");
    total.setEditable(false);
    rightPanel.add(total);

    JLabel symbolLabel = new JLabel("Stock Symbol");
    symbolLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    symbolLabel.setSize(100, 20);
    symbolLabel.setLocation(100, 200);
    rightPanel.add(symbolLabel);

    symbol = new JTextField();
    symbol.setFont(new Font("Arial", Font.PLAIN, 12));
    symbol.setSize(100, 20);
    symbol.setLocation(200, 200);
    symbol.setToolTipText("Please add a valid Stock symbol");
    symbol.addFocusListener(new MyFocusCheck(this));
    rightPanel.add(symbol);

    JLabel quantityLabel = new JLabel("Quantity");
    quantityLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    quantityLabel.setSize(100, 20);
    quantityLabel.setLocation(100, 250);
    rightPanel.add(quantityLabel);

    quantity = new JTextField();
    quantity.setFont(new Font("Arial", Font.PLAIN, 12));
    quantity.setSize(100, 20);
    quantity.setLocation(200, 250);
    quantity.setToolTipText("Please enter a quantity greater than 1");
    quantity.addFocusListener(new MyFocusCheck(this));
    rightPanel.add(quantity);

    JButton next = new JButton("Add Stock");
    next.setFont(new Font("Arial", Font.PLAIN, 10));
    next.setSize(60, 20);
    next.setLocation(200, 400);
    next.setToolTipText("Click to enter the stock details");
    next.addActionListener(evt -> {
      int p = features.addStockToNewPortfolio(pName.getText(), symbol.getText(), fees.getText(),
              String.valueOf(date.getSelectedItem()), String.valueOf(month.getSelectedItem()),
              String.valueOf(year.getSelectedItem()), quantity.getText());
      setTotalInt(p);
    });
    rightPanel.add(next);

    stockPanel = new JPanel();

    stockPanel.setLayout(new BoxLayout(stockPanel, BoxLayout.PAGE_AXIS));

    mainScrollPane = new JScrollPane(stockPanel,
            VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_AS_NEEDED);
    mainScrollPane.setFont(new Font("Arial", Font.PLAIN, 10));
    mainScrollPane.setSize(500, 150);
    mainScrollPane.setLocation(50, 450);

    rightPanel.add(mainScrollPane);

    JButton sub = new JButton("Submit");
    sub.setFont(new Font("Arial", Font.PLAIN, 15));
    sub.setSize(100, 20);
    sub.setLocation(150, 625);
    sub.setToolTipText("Click to create the portfolio");
    rightPanel.add(sub);
    sub.addActionListener(evt -> features.addPortfolio(pName.getText()));

    JButton reset = new JButton("Reset");
    reset.setFont(new Font("Arial", Font.PLAIN, 15));
    reset.setSize(100, 20);
    reset.setLocation(270, 625);
    reset.addActionListener(evt -> transition("addPortfolio"));
    rightPanel.add(reset);
  }

  @Override
  public void addPercentageEditButtons() {
    stockPanel.removeAll();
    int prevX = 0;
    int prevY = 0;
    HashMap<String, Float> stocks = features.getHashMap();
    buttonGroup.clear();
    for (String stock : stocks.keySet()) {
      JButton button = new JButton(stock + " : " + stocks.get(stock) + "%");
      button.setFont(new Font("Arial", Font.PLAIN, 12));
      button.setSize(90, 15);
      button.setLocation(prevX, prevY);
      prevY += 20;
      button.setFocusable(true);
      button.addActionListener(evt -> {
        setStocks(stock, String.valueOf(stocks.get(stock)));
        features.removeStockFromMap(stock);
        stockPanel.remove(button);
        buttonGroup.remove(button);
        disableButtons();
      });
      stockPanel.add(button);
      //stockPanel.repaint();
      //buttonGroup.add(button);
    }
    //tableScoll.removeAll();
    //tableScoll.add(stockPanel);
    //tableScoll.repaint();
    //pack();
  }

  @Override
  public void addEditButtons() {
    stockPanel.removeAll();
    int prevX = 0;
    int prevY = 0;
    List<Stock> stocks = features.getStockCache();
    buttonGroup.clear();
    for (int i = 0; i < stocks.size(); i++) {
      Stock stock = stocks.get(i);
      JButton button = new JButton(stock.getStockSymbol() + "- q:" + (int) stock.getQuantity());
      button.setFont(new Font("Arial", Font.PLAIN, 12));
      button.setSize(75, 20);
      button.setLocation(prevX, prevY);
      prevX += 75;
      if (prevX >= 350) {
        prevX = 50;
        prevY += 50;
      }
      button.setFocusable(true);
      int finalI = i;
      button.addActionListener(evt -> {
        LocalDate date = stock.getPurchaseDate();
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        int year = date.getYear();
        setStockDetails(stock.getStockSymbol(), String.valueOf(stock.getQuantity()),
                String.valueOf(stock.getCommissionFees()), String.valueOf(day),
                String.valueOf(month), String.valueOf(year));
        features.removeStockFromCache(finalI);
        stockPanel.remove(button);
        buttonGroup.remove(button);
        disableButtons();
      });
      stockPanel.add(button);
      buttonGroup.add(button);
    }
    mainScrollPane.removeAll();
    mainScrollPane.add(stockPanel);
    pack();
  }

  @Override
  public void disableButtons() {
    for (JButton button : buttonGroup) {
      button.setEnabled(false);
    }
  }

  @Override
  public void enableButtons() {
    for (JButton button : buttonGroup) {
      button.setEnabled(true);
    }
  }

  @Override
  public void resetStocks() {
    symbol.setText("");
    percent.setText("");
  }

  @Override
  public void resetStockDetails() {
    symbol.setText("");
    quantity.setText("");
    fees.setText("");
    date.setSelectedItem("-");
    month.setSelectedItem("--");
    year.setSelectedItem("----");
  }

  @Override
  public void setStockDetails(String sym, String quant,
                              String fee, String day, String mon, String yr) {
    symbol.setText(sym);
    quantity.setText(quant);
    fees.setText(fee);
    date.setSelectedItem(day);
    month.setSelectedItem(mon);
    year.setSelectedItem(yr);
  }

  private void setStocks(String sym, String perc) {
    symbol.setText(sym);
    percent.setText(perc);
  }

  private void setTotal(float val) {
    total.setText(String.valueOf(val));
  }

  private void setTotalInt(int val) {
    total.setText(String.valueOf(val));
  }

  private void computePortfolioForm() {
    fillPortfolioList();
    rightPanel.setLayout(null);

    JLabel title = new JLabel("Compute Portfolio Value", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.PLAIN, 25));
    title.setSize(350, 30);
    title.setLocation(100, 30);
    rightPanel.add(title);

    JLabel portfolioName = new JLabel("Portfolio Name");
    portfolioName.setFont(new Font("Arial", Font.PLAIN, 14));
    portfolioName.setSize(100, 20);
    portfolioName.setLocation(100, 100);
    rightPanel.add(portfolioName);

    comboBox = new JComboBox<>(portfolioList);
    comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
    comboBox.setSize(190, 20);
    comboBox.setLocation(200, 100);
    rightPanel.add(comboBox);


    JLabel dop = new JLabel("Date to Compute");
    dop.setFont(new Font("Arial", Font.PLAIN, 14));
    dop.setSize(120, 20);
    dop.setLocation(80, 150);
    rightPanel.add(dop);

    String[] dates = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
      "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
      "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    String[] years = {"1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002",
      "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010",
      "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018",
      "2019", "2020", "2021", "2022"};

    JComboBox<String> date = new JComboBox<>(dates);
    date.setFont(new Font("Arial", Font.PLAIN, 14));
    date.setSize(80, 20);
    date.setLocation(200, 150);
    date.setToolTipText("Enter the day to compute value");
    rightPanel.add(date);

    JComboBox<String> month = new JComboBox<>(months);
    month.setFont(new Font("Arial", Font.PLAIN, 14));
    month.setSize(80, 20);
    month.setLocation(290, 150);
    month.setToolTipText("Enter the month to compute value");
    rightPanel.add(month);

    JComboBox<String> year = new JComboBox<>(years);
    year.setFont(new Font("Arial", Font.PLAIN, 14));
    year.setSize(100, 20);
    year.setLocation(370, 150);
    year.setToolTipText("Enter the year to compute value");
    rightPanel.add(year);


    JButton sub = new JButton("Submit");
    sub.setFont(new Font("Arial", Font.PLAIN, 15));
    sub.setSize(100, 20);
    sub.setLocation(150, 250);
    rightPanel.add(sub);
    sub.addActionListener(evt -> {
      float val = features.computePortfolio(String.valueOf(comboBox.getSelectedItem()),
              String.valueOf(date.getSelectedItem()), String.valueOf(month.getSelectedItem()),
              String.valueOf(year.getSelectedItem()));
      computedValueDisplay(val);
    });

    JButton reset = new JButton("Reset");
    reset.setFont(new Font("Arial", Font.PLAIN, 15));
    reset.setSize(100, 20);
    reset.setLocation(270, 250);
    reset.addActionListener(evt -> transition("Compute"));
    rightPanel.add(reset);

    totalValue = new JLabel("Total Value of the Portfolio is:");
    totalValue.setFont(new Font("Arial", Font.PLAIN, 14));
    totalValue.setSize(200, 20);
    totalValue.setLocation(100, 350);
    rightPanel.add(totalValue);

    value = new JLabel("$ 0.0");
    value.setFont(new Font("Arial", Font.PLAIN, 14));
    value.setSize(150, 20);
    value.setLocation(300, 350);
    rightPanel.add(value);

    newScreen.setVisible(true);

  }

  private void computedValueDisplay(float val) {
    value.setText("$ " + val);
    value.setFont(new Font("Arial", Font.ITALIC, 25));
  }

  private void computeCostBasisForm() {
    fillPortfolioList();
    rightPanel.setLayout(null);

    JLabel title = new JLabel("Compute Cost Basis", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.PLAIN, 25));
    title.setSize(350, 30);
    title.setLocation(100, 30);
    rightPanel.add(title);

    JLabel portfolioName = new JLabel("Portfolio Name");
    portfolioName.setFont(new Font("Arial", Font.PLAIN, 14));
    portfolioName.setSize(100, 20);
    portfolioName.setLocation(100, 100);
    rightPanel.add(portfolioName);

    comboBox = new JComboBox<>(portfolioList);
    comboBox.setFont(new Font("Arial", Font.PLAIN, 14));
    comboBox.setSize(190, 20);
    comboBox.setLocation(200, 100);
    rightPanel.add(comboBox);


    JLabel dop = new JLabel("Date to Compute");
    dop.setFont(new Font("Arial", Font.PLAIN, 14));
    dop.setSize(100, 20);
    dop.setLocation(100, 150);
    rightPanel.add(dop);

    String[] dates = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
      "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
      "21", "22", "23", "24", "25","26", "27", "28", "29", "30", "31"};
    String[] months = {"01", "02", "03", "04", "05", "06", "07", "08",
      "09", "10", "11", "12"};


    JComboBox<String> date = new JComboBox<>(dates);
    date.setFont(new Font("Arial", Font.PLAIN, 14));
    date.setSize(80, 20);
    date.setLocation(200, 150);
    date.setToolTipText("Enter the day to compute cost basis");
    rightPanel.add(date);

    JComboBox<String> month = new JComboBox<>(months);
    month.setFont(new Font("Arial", Font.PLAIN, 14));
    month.setSize(80, 20);
    month.setLocation(290, 150);
    month.setToolTipText("Enter the month to compute cost basis");
    rightPanel.add(month);

    JComboBox<String> year = new JComboBox<>(futureYears);
    year.setFont(new Font("Arial", Font.PLAIN, 14));
    year.setSize(100, 20);
    year.setLocation(370, 150);
    year.setToolTipText("Enter the year to compute cost basis");
    rightPanel.add(year);

    JButton sub = new JButton("Submit");
    sub.setFont(new Font("Arial", Font.PLAIN, 15));
    sub.setSize(100, 20);
    sub.setLocation(150, 250);
    rightPanel.add(sub);
    sub.addActionListener(evt -> {
      float val = features.getCostBasis(String.valueOf(comboBox.getSelectedItem()),
              String.valueOf(date.getSelectedItem()), String.valueOf(month.getSelectedItem()),
              String.valueOf(year.getSelectedItem()));
      computedValueDisplay(val);
    });

    JButton reset = new JButton("Reset");
    reset.setFont(new Font("Arial", Font.PLAIN, 15));
    reset.setSize(100, 20);
    reset.setLocation(270, 250);
    reset.addActionListener(evt -> transition("Compute"));
    rightPanel.add(reset);

    totalValue = new JLabel("Total Cost Basis of the Portfolio is: ");
    totalValue.setFont(new Font("Arial", Font.PLAIN, 12));
    totalValue.setSize(200, 20);
    totalValue.setLocation(100, 350);
    rightPanel.add(totalValue);

    value = new JLabel("$ 0.0");
    value.setFont(new Font("Arial", Font.PLAIN, 12));
    value.setSize(150, 20);
    value.setLocation(300, 350);
    rightPanel.add(value);
    newScreen.setVisible(true);

  }


}
