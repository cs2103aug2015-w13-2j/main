package sg.edu.cs2103aug2015_w13_2j.parser;

// @@author A0121410H

/**
 * Utility class that represents a parsed token and its corresponding value
 * 
 * @author Zhu Chunqi
 */
public class Token {
    public static final Token EMPTY_TOKEN = new Token(Type.EMPTY, "");

    public enum Type {
        EMPTY, RESERVED, DATE, DATE_INVALID, FLAG, FLAG_INVALID, ID, ID_INVALID, NAME, WHITESPACE, ALPHA_NUM;
    };

    public Type type;
    public String value;

    public Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public boolean isEmptyToken() {
        return this.type.equals(Token.Type.EMPTY);
    }
}
