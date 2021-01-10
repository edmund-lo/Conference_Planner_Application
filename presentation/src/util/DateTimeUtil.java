package util;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import model.LoginLog;
import model.MessageThread;
import model.ScheduleEntry;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Static utility class for formatting Duration and LocalDateTime objects from Strings and vice versa
 */
public class DateTimeUtil {
    private static final DateTimeUtil Instance = new DateTimeUtil();

    /**
     * Gets the current instance of a DateTimeUtil
     * @return An instance of DateTimeUtil object
     */
    public static DateTimeUtil getInstance() { return Instance; }

    /**
     * Empty DateTimeUtil constructor
     */
    private DateTimeUtil() {}

    /** The date pattern that is used for conversion. Change as you wish. */
    private static final String DATETIME_PATTERN = "dd.MMM.yyyy HH:mm";
    /** The date formatter. */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_PATTERN);

    public String format(LocalDateTime dateTime) {
        if (dateTime == null)
            return null;
        return DATE_FORMATTER.format(dateTime);
    }

    /**
     * Converts a String in the format of the defined {@link DateTimeUtil#DATETIME_PATTERN}
     * to a {@link LocalDateTime} object.
     *
     * Returns null if the String could not be converted.
     *
     * @param dateTimeString the date as String
     * @return the date object or null if it could not be converted
     */
    public LocalDateTime parse(String dateTimeString) {
        try {
            return DATE_FORMATTER.parse(dateTimeString, LocalDateTime::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Converts duration into a String representation
     * @param duration Duration object
     * @return String representation of duration formatted hh:mm
     */
    public String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        return String.format("%d:%02d", absSeconds / 3600, (absSeconds % 3600) / 60);
    }

    /**
     * Sets cell factory for given column
     * @param column JavaFX column containing LocalDateTime variable from ScheduleEntry
     */
    public void setScheduleDateTimeCellFactory(TableColumn<ScheduleEntry, LocalDateTime> column) {
        column.setCellFactory(c -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime dateTime, boolean empty) {
                super.updateItem(dateTime, empty);
                if (empty)
                    setText(null);
                else
                    this.setText(format(dateTime));
            }
        });
    }

    /**
     * Sets cell factory for given column
     * @param column JavaFX column containing LocalDateTime variable from MessageThread
     */
    public void setMessageDateTimeCellFactory(TableColumn<MessageThread, LocalDateTime> column) {
        column.setCellFactory(c -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime dateTime, boolean empty) {
                super.updateItem(dateTime, empty);
                if (empty)
                    setText(null);
                else
                    this.setText(format(dateTime));
            }
        });
    }

    /**
     * Sets cell factory for given column
     * @param column JavaFX column containing LocalDateTime variable from LoginLog
     */
    public void setLoginDateTimeCellFactory(TableColumn<LoginLog, LocalDateTime> column) {
        column.setCellFactory(c -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime dateTime, boolean empty) {
                super.updateItem(dateTime, empty);
                if (empty)
                    setText(null);
                else
                    this.setText(format(dateTime));
            }
        });
    }
}
