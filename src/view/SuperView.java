package view;

import java.io.PrintStream;

/**
 * The view used for offering the to decide the type of user
 * interface they want.
 */
public class SuperView implements SuperInterface {
  private final PrintStream out;

  /**
   * function to set output stream.
   * @param out output stream object.
   */
  public SuperView(PrintStream out) {
    this.out = out;
  }

  @Override
  public void askUserView() {
    out.println("Menu: ");
    out.println("T: Do you wish to use the Text Interface?");
    out.println("G: Do you wish to use the Graphical User Interface?");
    out.println("Q: Quit the program");
    out.print("Enter your choice: ");
  }

  public void showOptionError() {
    out.print("\nInvalid option. Please try again.");
  }
}
