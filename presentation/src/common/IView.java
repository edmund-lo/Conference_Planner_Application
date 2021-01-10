package common;

import javafx.scene.text.Text;

/**
 * Common View interface for all view implementations
 */
public interface IView {
    /**
     * Gets the JavaFX Text object where the result of controller methods is displayed
     * @return JavaFX Text object
     */
    Text getResultTextControl();
}
