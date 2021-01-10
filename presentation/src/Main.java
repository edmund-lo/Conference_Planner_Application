import javafx.application.Application;
import javafx.stage.Stage;
import util.ComponentFactory;

public class Main extends Application {
    /**
     * JavaFX start method, sets initial scene and title to login scene
     * @param primaryStage JavaFX Stage object that all scenes are set in
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Project Phase 2");
        ComponentFactory.getInstance().createLoggedOutComponent(primaryStage, "login.fxml");
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
