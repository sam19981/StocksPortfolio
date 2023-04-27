# Stocks

PDP Group project

Our program provides the user with the following features/functionalities:

1. Ability to store his portfolio and stock information and fetch it back when he logs in again.
2. Provides the user option to input his details through an xml/ txt file.
3. Also, provides the user option to input his details step by step using the Text UI.
4. Get Portfolio - Displays the specific portfolio and the stocks present in it which the user
   wants to examine.
5. Compute Portfolio - Computes and returns the worth of the single portfolio of the user on a
   given date.
6. Add Portfolio - Add new portfolio with the desired set of stocks which the user wants.
7. If 2 stocks in the same portfolio have the same name their quantities add up to show the total
   amount.
8. If the user tries to input a new Portfolio with the same name as one of his existing portfolio,
   we request him to provide a different name.
9. Allows a user to add new portfolios to his name if he/she wishes even after an initial input
   with file or step-by-step input is complete.
10. Allow user to buy stocks and add it to an existing portfolio.
11. Allow user to sell stocks and add it to an existing portfolio.
12. Allow for the user to enter the buy and sell in non-chronological order.
13. Determine the cost basis of a specific portfolio the total amount of money
    invested in a portfolio by a specific date.
14. Allow the client to set the commission fee for each transaction(buy/ sell stocks) made.
15. Allow the user to see the performance of a portfolio over a period of time.
16. Persist the data related to a user after each transaction. i.e suppose the user logs out
    prematurely/ the OS stops responding, all his data will be saved in the file related to the
    user.
17. If 2 stocks in the same flexible portfolio have the same name their quantities add up to show
    the total amount, taking care of the dates on which they were purchased.
18. Allow the user to create a dollar cost averaging plan extending between two dates 
    thereby providing him an opportunity to plan for the future.
19. The Dollar cost averaging plan allows the user to also not specify an end date thereby making 
    the plan ongoing. 
20. Allow the user to pick between a graphical user interface and a text based interface and 
    provides the above listed operations.
21. The inputs are validated as the user enters them, allowing him to know at run time if his 
    inputs are valid rather than waiting for him to submit it.

Things to be implemented in the future:

1. Sell Portfolio - The user can sell his portfolio and determine the amount of profit/loss
   he make at the given date.
2. Remove all Portfolios - Sell all the portfolios owned by the user and display
   the total profit made by the portfolios on the sell date.
3. We currently do not provide an option for the user to go back after he goes into an invalid
   state in the TEXT UI.
   (Entering an invalid file)
4. Compute the loss/ profit on each stock if sold on a particular day before the user sells it.
5. Increased protection to the user data, currently the user name and password is stored just as
   strings in the csv. Encryption could be used to encrypt the password before storing.
6. Currently only a new user who has no data can provide a file input to store data, we plan to
   add this feature to an existing user as well.