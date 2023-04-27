package controller;

/**
 * The following interface represents the Controller of our project.
 * The Controller controls the flow of the program.
 * Though it does not host any of the functionality, it takes user inputs.
 * It tells model what to do and view what to display.
 */
public interface IController {
  /**
   * The following method is the first to be called from the main function.
   * It dictates the flow of the program based on the input the user provides.
   */
  void connect();

}
