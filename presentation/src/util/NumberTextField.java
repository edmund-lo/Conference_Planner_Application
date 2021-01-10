package util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TextField;

/**
 * TextField implementation that accepts formatted number and stores them in a
 * BigDecimal property The user input is formatted when the focus is lost or the
 * user hits RETURN.
 */
public class NumberTextField extends TextField {
    private final NumberFormat nf;
    private final ObjectProperty<BigDecimal> number = new SimpleObjectProperty<>();

    /**
     * Gets value of the number class variable
     * @return BigDecimal object representing number
     */
    public final BigDecimal getNumber() {
        return number.get();
    }

    /**
     * Sets value of the number class variable
     * @param value BigDecimal object representing new value
     */
    public final void setNumber(BigDecimal value) {
        number.set(value);
    }

    /**
     * Gets the number property
     * @return ObjectProperty of BigDecimal representing number class variable
     */
    public ObjectProperty<BigDecimal> numberProperty() {
        return number;
    }

    /**
     * NumberTextField constructor with default settings and listeners
     */
    public NumberTextField() {
        super(); // calls TextField constructor
        setNumber(BigDecimal.ZERO);
        initHandlers();
        this.nf = NumberFormat.getInstance();
    }

    /**
     * Initialises all the listeners associated with this NumberTextField
     */
    private void initHandlers() {
        // force the field to be numeric only
        textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                setText(newValue.replaceAll("[^\\d]", ""));
        });

        // try to parse when focus is lost or RETURN is hit
        setOnAction(arg0 -> parseAndFormatInput());

        focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                parseAndFormatInput();
            }
        });

        // Set text in field if BigDecimal property is changed from outside.
        numberProperty().addListener((observable, oldValue, newValue) -> setText(nf.format(newValue)));
    }

    /**
     * Tries to parse the user input to a number according to the provided
     * NumberFormat
     */
    private void parseAndFormatInput() {
        try {
            String input = getText();
            if (input == null || input.length() == 0) {
                return;
            }
            Number parsedNumber = nf.parse(input);
            BigDecimal newValue = new BigDecimal(parsedNumber.toString());
            setNumber(newValue);
            selectAll();
        } catch (ParseException ex) {
            // If parsing fails keep old number
            setText(nf.format(number.get()));
        }
    }
}
