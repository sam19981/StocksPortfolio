
ASSIGNMENT 6

Modified or newly added classes:
1. Features Interface: (added)
    This Interface was introduced to define the various functionalities provided to the user.
    We also use the methods available through this class to link to the buttons of the view.
    GUIcontroller implements this interface and has all the implementation details of each feature.

2. PlanImpl: (added)
    To provide the user with an opportunity to invest in a dollar cost averaging plan, we have added
     this class. This class will execute on a given portfolio for a given time period, each time
     investing a fixed amount, split into multiple stocks. (Visitor Pattern)

3. UserXmlReaderImpl: (modified)
    To accommodate for associating a user with a plan and to persist it for the future, we have
    added more tags in the user related file if he wants to add a plan.
    Correspondingly, to read these plans from a file we have added a new method called getPlan and
    assign it to the user upon his login.

4. UserXmlWriterImpl: (modified)
    In continuation of the above change, to persist the plan and write it in the user related file,
    we have modified the writer to write the plan details too.

5. BarChart: (added)
   This is class was added using a external dependency which draws the barchart for us using the
   data which we provide using swing and at libraries.

6. BargraphAdapter: (added)
   Added to utilize the external bar chart class by converting the existing bar graph class to adapt 
   to the external bar chart class. (Adapter pattern)

7. SuperController: (added)
    This controller is the one that is instantiated by the main method, based on the user's input
    further we delegate the control to either TextController or GUIController.

8. GUIController: (added)
    To support Graphical user interface, we added the following implementation of the controller
    interface. This controller co-ordinated between the newly added GUI view and the existing model
    classes.

9. JFrameView: (added)
    This implementation of the GUI View class is the class which renders the Graphical user based
    interface to the user.

10. Model interface: (Features added)
    i. validatePercentage - To validate if the combination of percent and stock symbols provided for 	a plan is valid or not.
    ii. addPlan - To add a new dollar cost averaging plan and associate it with the current user.
    iii. updateMap - To update the Stock to percent map associated with a given plan.
    iv. fetchMapTotalValue - Fetch the total value of the given map
    v. removeStockFromCache - remove the stock associated with the cache list for a new Portfolio ( called when the user wants to edit  the stock and the corresponding in the GUI after setting it once).
    vi. removeStockFromMap - remove the stock associated with a cache map for a new Plan ( called when the user wants to edit the stock and the corresponding in the GUI 	after setting it once)


-----------------------------------------------------------------------------------------------
ASSIGNMENT 5
Modified or newly added classes:

i. AlphaVantageImpl:
    Fetches the input stream from Alpha Vantage API for a given symbol.
    The fetch is done from the API only if the value for the ticker
    symbol is not available in the cache.

ii. Connection:
    fetch method signature now returns object of StringBuilder rather than InputStream.
    This was primarily done because InputStream object can only be read once as it the return
    type was not marked and would hence be difficult to cache in the same format.

iii. TextController:
    Added new menus to incorporate the requirements of the assignments.
    Added option for the user to
    - Buy 1 or more stocks to an existing portfolio.
    - Sell 1 or more stocks from an existing portfolio
    - Compute the Cost Basis of the specific portfolio.
    - See the performance chart for the range of dates provided by the user.
    Modified the type of date input taken by the user as day, month year separately
    to dd-mm-yyyy.

iv. Stock & StockImpl:
    Added another attribute associated with a stock object called commission fees
    whose value is taken from the user.
    We no longer have an attribute named Stock Name as the names provided by users could be
    ambiguous.
    We reference a Stock using its stock symbol.

v. LRUCache:
    An implementation of the popular LRU cache using a doubly linked list with left and
    right pointers and a Hashmap to store and fetch the values faster.
    The capacity of this cache is set to 10 at the moment, i.e. information of upto 10
    different stocks can be added after which the least recently used cached stocked data
    is deleted.
    The capacity limit of the cache helps in not overloading the memory with a lot of data.
    But however, we could alter the size of the cache based on the type of user and the load
    that is taken by the application.

vi. Node:
    This newly added class is solely used by the LRU Cache to hold its data, the key for the node
    is the stock ticker symbol. The value stored by it is the StringBuilder output provided by the
    AlphaVantage API.

vii. AbstractPortfolio:
    This class was added to house all the common functions of different portfolios
    to incorporate union data type design for storing the two different kinds of
    portfolios (flexible and inflexible).

    Justification - By introducing this abstract class and using union datatype design we were
    able to avoid major functionality change and were able to reuse the existing code for
    inflexible portfolio and build on it rather than creating a separate set of classes and
    functions for implementing inflexible portfolios.

viii. Builderportfolio class:
    We added Builderportfolio interface to take advantage of the union data type
    design and dynamic dispatch while creating the different portfolios ,persisting
    them while reading and writing.

    Justification - with the help of the builder portfolio and dynamic dispatch we
    were able to reuse the existing xml read and write parsers instead of writing a
    new one for each type of portfolios.
    Added a Builder Interface for creating Flexible Portfolio and Inflexible Portfolio
    depending on the nature of portfolio user wants to create.


ix. FlexiblePortfolioImpl:
     We introduced new Flexible portfolio class to provide the users with the necessary
     functionalities for the users like to buy and sell stock which was not offered by the
     inflexible portfolios.

     Justification - By introducing a new class for flexible portfolio and make it extend the
     abstract portfolio class, abstracting out the common code present in the inflexible
     portfolio we were able to reuse the code from inflexible portfolio and make our
     code compact.

x. Draw class:
    We introduced a new draw interface to incorporate any new
    graphical representations which may be requested in the future.

    Justification - by adding this interface we can incorporate any new
    graphical representation without making much changes to view as it will
    be taking only a object of type Draw and will have the draw method
    to which perform the needed operation without affecting view in any manner.

------------------------------------------------------------------------------------------------

The hierarchy (MVC) of the program is as follows:

I. The Model class in model package fetches all user details at run time by instantiating the data Model
classes User,Portfolio and stock.
  i. Our data models come with builders which we use to instantiate objects and thus avoiding
  any error or empty data during their creation.

Justification:
We chose to use the above design even when the question did not specify anything
about multiple users because this would help us in the future assignments.
In case Stocks Part-2 expands to multiple users working on the same program
or asking us to capture and store data for multiple data, our program will be easy to extend.

II. Each User object contains a name, password and one or more portfolios assigned to his name.

Justification:
A User object is created with a uniques username and password so that
data of one user does not interfere with data of other users

III. Each Portfolio object has its unique name, and a list of Stock Objects within it.

Justification:
A Portfolio is made to be identified uniquely by a portfolio name so that a user
can choose which portfolio's value to compute from when he wishes to

IV. Each Stock Object has information about a given stock i.e Stock name, ticker symbol,
purchase date, etc.

Justification:
A Stock Object not only has a name, ticker symbol and quantity but also has the purchase date
so that if future assignments ask us to compute profit or loss at a particular date
we will be able to do it.

V. As input maybe provided to the program in xml we have an XML parser which
parses information and loads it onto the program.

We chose XML because:
a. XML offers the capability to display data because it is a markup language.
JSON supports only text and number data type.

b. In jdk, javax.xml.parsers offers us help to better parse data in XML.
Whereas for json there was no direct help available in jdk.

VI. We also have an XML Writer class which stores the information of the user onto
 a file related to the user.

Justification:
We have a writer along with the reader as data fetched during the interaction with the user
could be written to the user related file .


vii. A Connection class is present with one Impl using the Yahoo API, could be
extended to more classes in the future assignments.

Justification:
If Alpha Vantage is to be supported in the future, we can easily create
an implementation of the Connection class and return the Stream of data to the program.

viii. A Utilities class is present to load CSV data about the users onto the program.

ix. A View class is present whose implementation's methods are called each time we want the user
 to see some information.

 Justification:
 The View is separated from other components so that it becomes easy to narrow down any
 problems with the actual functionality.

x. Finally, a Controller class which handles the running of all of these classes,
i.e delegates work to each of these classes appropriately for the program to function.

The controller is the entity that takes input from the user,
 which in turn causes the model to transition state.
