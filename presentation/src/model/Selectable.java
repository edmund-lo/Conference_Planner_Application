package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Helper class for allowing models to be selectable and store its selected attribute
 */
public abstract class Selectable {
    private final BooleanProperty selected;

    /**
     * Constructor that sets default selected attribute to false
     */
    protected Selectable(boolean selected) {
        this.selected = new SimpleBooleanProperty(selected);
    }

    //region Getters and Setters
    public BooleanProperty getSelected() {
        return selected;
    }

    public boolean isChecked() {
        return this.selected.getValue();
    }

    public void setChecked(boolean selected) {
        this.selected.setValue(selected);
    }
    //endregion
}
