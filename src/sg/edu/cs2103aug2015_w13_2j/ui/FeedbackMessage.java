package sg.edu.cs2103aug2015_w13_2j.ui;

import java.awt.Color;

// @@author A0121410H

/**
 * FeedbackMessage class encapsulates the data necessary to display styled
 * feedback messages to the user. It comprises a message string retrievable via
 * {@link #getMessage()} and a {@link FeedbackType} object which determines the
 * type of feedback as well as the styling.
 * 
 * @author Zhu Chunqi
 */
public class FeedbackMessage {
    public static final FeedbackMessage CLEAR = new FeedbackMessage("",
            FeedbackType.INFO);
    public static final FeedbackMessage ERROR_INVALID_FILTER = new FeedbackMessage(
            "The filter that you have specified is not valid. Did you type the filter name correctly?",
            FeedbackType.ERROR);
    public static final FeedbackMessage ERROR_INVALID_TASK = new FeedbackMessage(
            "Invalid task entered. Please provide a name!", FeedbackType.ERROR);
    public static final FeedbackMessage ERROR_TASK_NOT_FOUND = new FeedbackMessage(
            "Task not found. Did you enter the index correctly?",
            FeedbackType.ERROR);
    public static final FeedbackMessage ERROR_INVALID_INDEX = new FeedbackMessage(
            "Task index entered is invalid. Please provide a valid index!",
            FeedbackType.ERROR);
    public static final FeedbackMessage ERROR_UNRECOGNIZED_COMMAND = new FeedbackMessage(
            "Command not recognized.", FeedbackType.ERROR);

    /**
     * Enumeration of the styling for different types of user feedback messages.
     * The color of font to be used for each feedback type can be retrieved via
     * {@link #getAWTColor()} or {@link #getFXColor()}
     * 
     * @author Zhu Chunqi
     *
     */
    public enum FeedbackType {
        INFO(Color.BLACK), ERROR(Color.RED);

        private Color mColor;

        private FeedbackType(Color color) {
            mColor = color;
        }

        /**
         * Retrieves the color that should be used to style this type of
         * feedback message
         * 
         * @return AWT Color object to be used for this type of feedback
         */
        public Color getAWTColor() {
            return mColor;
        }

        /**
         * Retrieves the color that should be used to style this type of
         * feedback message
         * 
         * @return JavaFX Color object to be used for this type of feedback
         */
        public javafx.scene.paint.Color getFXColor() {
            return javafx.scene.paint.Color.rgb(mColor.getRed(),
                    mColor.getGreen(), mColor.getBlue());
        }
    }

    private String mMessage;
    private FeedbackType mType;

    public FeedbackMessage(String message, FeedbackType type) {
        mMessage = message;
        mType = type;
    }

    /**
     * Retrieves the string message text
     * 
     * @return String message text
     */
    public String getMessage() {
        return mMessage;
    }

    /**
     * Retrieves the {@link FeedbackType} object containing the styling
     * information for this feedback message
     * 
     * @return {@link FeedbackType} object for this feedback message
     */
    public FeedbackType getType() {
        return mType;
    }
}
