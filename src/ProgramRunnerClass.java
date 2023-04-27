import controller.SuperController;
import model.IModel;
import model.Model;
import view.SuperView;

/**
 * The following class hosts the main method.
 * It is the one that will be called when running the jar.
 */
public class ProgramRunnerClass {

  /**
   * The below main method instantiates objects of the Model, View and the Controller.
   * It also sets the program in motion by the go function of the Controller.
   *
   * @param args - input arguments if any.
   */
  public static void main(String[] args) {
    IModel model = new Model();
    SuperView view = new SuperView(System.out);
    SuperController controller = new SuperController(model, System.in, view);
    controller.connect();
  }
}
