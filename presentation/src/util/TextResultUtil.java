package util;

import javafx.css.PseudoClass;
import javafx.scene.Node;

/**
 * Static utility class for adding pseudo classes to JavaFX nodes, i.e. text and form fields
 */
public class TextResultUtil {
    public static final TextResultUtil Instance = new TextResultUtil();

    /**
     * Gets the current instance of a TextResultUtil
     * @return An instance of TextResultUtil object
     */
    public static TextResultUtil getInstance() { return Instance; }

    /**
     * Empty TextResultUtil constructor
     */
    private TextResultUtil() {}

    /**
     * Adds pseudoClassString to node
     * @param pseudoClassString String representation of css class
     * @param node JavaFX Node object to add pseudo class to
     */
    public void addPseudoClass(String pseudoClassString, Node node) {
        PseudoClass pseudoClass = PseudoClass.getPseudoClass(pseudoClassString);
        node.pseudoClassStateChanged(pseudoClass, true);
    }

    /**
     * Removes all possible pseudo classes from node
     * @param node JavaFX Node object to remove pseudo classes from
     */
    public void removeAllPseudoClasses(Node node) {
        node.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), false);
        node.pseudoClassStateChanged(PseudoClass.getPseudoClass("warning"), false);
        node.pseudoClassStateChanged(PseudoClass.getPseudoClass("success"), false);
    }
}
