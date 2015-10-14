package sg.edu.cs2103aug2015_w13_2j;

import java.awt.Color;

//@@author A0121410H

/**
 * Enumeration of the styling for the different types of user feedback
 * messages. The color of font to be used for each feedback type can be
 * retrieved via {@link FeedbackType#getColor()}
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
     * feedback
     * 
     * @return Color object to be used for this type of feedback
     */
    public Color getColor() {
        return mColor;
    }
}