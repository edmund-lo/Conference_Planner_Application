package util;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for picking a date and time in a form
 */
public class DateTimePicker extends DatePicker {
    public static final String DefaultFormat = "yyyy-MM-dd HH:mm";
    private DateTimeFormatter formatter;
    private final ObjectProperty<LocalDateTime> dateTimeValue = new SimpleObjectProperty<>(LocalDateTime.now());
    private final ObjectProperty<String> format = new SimpleObjectProperty<>() {
        /**
         * Sets DateTimeFormatter to parse a different format string
         * @param newValue String pattern to parse
         */
        public void set(String newValue) {
            super.set(newValue);
            formatter = DateTimeFormatter.ofPattern(newValue);
        }
    };

    /**
     * Sets preferred column count based on the format
     */
    public void alignColumnCountWithFormat() {
        getEditor().setPrefColumnCount(getFormat().length());
    }

    /**
     * DateTimePicker constructor with default settings and listeners
     */
    public DateTimePicker() {
        getStyleClass().add("datetime-picker");
        setFormat(DefaultFormat);
        setConverter(new InternalConverter());
        alignColumnCountWithFormat();

        // Synchronize changes to the underlying date value back to the dateTimeValue
        valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                dateTimeValue.set(null);
            } else {
                if (dateTimeValue.get() == null) {
                    dateTimeValue.set(LocalDateTime.of(newValue, LocalTime.now()));
                } else {
                    LocalTime time = dateTimeValue.get().toLocalTime();
                    dateTimeValue.set(LocalDateTime.of(newValue, time));
                }
            }
        });

        // Synchronize changes to dateTimeValue back to the underlying date value
        dateTimeValue.addListener((observable, oldValue, newValue) ->
                setValue(newValue == null ? null : newValue.toLocalDate()));

        // Persist changes on blur
        getEditor().focusedProperty().addListener((observableValue, oldValue, newValue) -> simulateEnterPressed());
    }

    /**
     * Simulates the Enter key being pressed to commit the value
     */
    private void simulateEnterPressed() {
        getEditor().fireEvent(new KeyEvent(getEditor(), getEditor(), KeyEvent.KEY_PRESSED, null, null,
                KeyCode.ENTER, false, false, false, false));
    }

    /**
     * Gets the value of the dateTimeValue class variable
     * @return LocalDateTime object representing dateTimeValue
     */
    public LocalDateTime getDateTimeValue() {
        return dateTimeValue.get();
    }

    /**
     * Sets the value of the dateTimeValue class variable
     * @param dateTimeValue LocalDateTime object representing new value
     */
    public void setDateTimeValue(LocalDateTime dateTimeValue) {
        this.dateTimeValue.set(dateTimeValue);
    }

    /**
     * Gets the value of the format class variable
     * @return String object representing format
     */
    public String getFormat() {
        return format.get();
    }

    /**
     * Sets the value of the format class variable and realigns the preferred column count
     * @param format String object representing new value
     */
    public void setFormat(String format) {
        this.format.set(format);
        alignColumnCountWithFormat();
    }

    /**
     * Internal class that serves to convert a String into a LocalDate and vice versa
     */
    class InternalConverter extends StringConverter<LocalDate> {
        /**
         * Converts a LocalDate object into its String representation
         * @param object LocalDate object
         * @return String representation of LocalDate formatted by the formatter
         */
        public String toString(LocalDate object) {
            LocalDateTime value = getDateTimeValue();
            return (value != null) ? value.format(formatter) : "";
        }

        /**
         * Converts a String object into a LocalDate object
         * @param value String object
         * @return LocalDate object parsed using formatter
         */
        public LocalDate fromString(String value) {
            if (value == null || value.isEmpty()) {
                dateTimeValue.set(null);
                return null;
            }

            dateTimeValue.set(LocalDateTime.parse(value, formatter));
            return dateTimeValue.get().toLocalDate();
        }
    }
}
