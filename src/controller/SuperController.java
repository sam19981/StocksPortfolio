package controller;

import java.io.InputStream;
import java.util.Scanner;

import model.IModel;
import view.GuiView;
import view.IView;
import view.JFrameView;
import view.TextView;
import view.SuperView;

/**
 * The program provides the user with options to either execute in a
 * text based interface or in a graphical user interface.
 * The below controller delegates to either the text based controller or
 * graphical user based controller based on the user's input.
 */
public class SuperController implements IController {
  private final Scanner in;
  private final SuperView view;
  private final IModel model;

  /**
   * Decider which interface to call based on users
   * decision.
   *
   * @param model - data models required for
   *              the controller to represent
   *              user and perform operations on them.
   * @param in    - InputStream object to initialise scanner
   *              class for taking inputs for the user.
   * @param view  - view for displaying
   *              the prompts and user result of
   *              user interactions.
   */
  public SuperController(IModel model, InputStream in, SuperView view) {
    this.model = model;
    this.view = view;
    this.in = new Scanner(in);
  }

  @Override
  public void connect() {
    boolean mainQuit = false;
    while (!mainQuit) {
      view.askUserView();
      String option = in.nextLine().toUpperCase();
      switch (option) {
        case "T":
          IView textView = new TextView(System.out);
          IController controller = new TextController(model, System.in, textView);
          controller.connect();
          mainQuit = true;
          break;

        case "G":
          GuiView view2 = new JFrameView();
          GUIcontrolller controller2 = new GUIcontrolller(model);
          controller2.setView(view2);
          mainQuit = true;
          break;

        case "Q":
          mainQuit = true;
          break;
        default:
          view.showOptionError();
      }
    }
  }
}
