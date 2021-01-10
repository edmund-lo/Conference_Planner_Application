package util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Static factory class that helps create
 */
public class ComponentFactory {
    private static final ComponentFactory Instance = new ComponentFactory();
    private final Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

    /**
     * Gets the current instance of a ComponentFactory
     * @return An instance of a ComponentFactory object
     */
    public static ComponentFactory getInstance() {
        return Instance;
    }

    /**
     * Empty ComponentFactory constructor
     */
    private ComponentFactory() {}

    /**
     * Tries to create a root node view with associated username and accountType in the view controller
     * @param fxml String FXML file name to load
     * @return JavaFX Node object that represents the scene
     */
    private Node createRoot(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("fxml/" + fxml));
            return fxmlLoader.load();
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Helper method to get a JavaFX Stage object from actionEvent
     * @param actionEvent JavaFX ActionEvent object representing user input event
     * @return JavaFX Stage object that all scenes will be set in
     */
    private Stage getStageFromEvent(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        return (Stage) node.getScene().getWindow();
    }

    /**
     * Creates a scene for view when the user has not logged in yet
     * @param stage JavaFX Stage object that all scenes will be set in
     * @param fxml String FXML file name to load
     */
    public void createLoggedOutComponent(Stage stage, String fxml) {
        Scene scene = new Scene((Parent) createRoot(fxml), screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(scene);
    }

    /**
     * Creates a scene for views when the user has not logged in yet
     * @param actionEvent JavaFX ActionEvent object representing user input event
     * @param fxml String FXML file name to load
     */
    public void createLoggedOutComponent(ActionEvent actionEvent, String fxml) {
        createLoggedOutComponent(getStageFromEvent(actionEvent), fxml);
    }

    /**
     * Creates a scene for view when the user has logged in
     * @param stage JavaFX Stage object that all scenes will be set in
     * @param fxml String FXML file name to load
     */
    public void createLoggedInComponent(Stage stage, String fxml) {
        BorderPane root = new BorderPane();
        root.setTop(createRoot("toolbar.fxml"));
        root.setCenter(createRoot(fxml));
        Scene scene = new Scene(root, screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(scene);
    }

    /**
     * Creates a scene for views when the user has logged in
     * @param actionEvent JavaFX ActionEvent object representing user input event
     * @param fxml String FXML file name to load
     */
    public void createLoggedInComponent(ActionEvent actionEvent, String fxml) {
        createLoggedInComponent(getStageFromEvent(actionEvent), fxml);
    }

    /**
     * Creates a scene for view when the user has not logged in yet
     * @param node JavaFX Node object represents one of the scene's nodes
     * @param fxml String FXML file name to load
     */
    public void createLoggedInComponent(Node node, String fxml) {
        createLoggedInComponent((Stage) node.getScene().getWindow(), fxml);
    }
}
