package sg.edu.cs2103aug2015_w13_2j.parser;

import java.util.ArrayList;
import java.util.Iterator;

import sg.edu.cs2103aug2015_w13_2j.parser.Token.Type;

public class Command implements Iterable<Token> {
    private ArrayList<Token> mTokens = new ArrayList<Token>();

    /**
     * Adds the newly parsed Token object into this Command object
     * 
     * @param token
     *            The Token object to be added
     */
    public void addToken(Token token) {
        mTokens.add(token);
    }

    /**
     * Adds the newly parsed Token object into this Command object
     * 
     * @param type
     *            The enumerated Type of the new Token
     * @param value
     *            The String value of the new Token
     */
    public void addToken(Token.Type type, String value) {
        addToken(new Token(type, value));
    }

    /**
     * Retrieves the first ALPHA_NUM token of this command
     * 
     * @return First Token object of type ALPHA_NUM or an EMPTY Token if command
     *         contains none
     */
    public Token getAlphaToken() {
        for (Token token : this) {
            if (token.type == Type.ALPHA_NUM) {
                return token;
            }
        }
        return Token.EMPTY_TOKEN;
    }

    /**
     * Retrieves the first ID token of this command
     * 
     * @return First Token object of type ID or an EMPTY Token if command
     *         contains none
     */
    public Token getIdToken() {
        for (Token token : this) {
            if (token.type == Type.ID) {
                return token;
            }
        }
        return Token.EMPTY_TOKEN;
    }
    
    /**
     * Convenience method to retrieves a list of all values of ID 
     * tokens in this command
     * 
     * @return List of all values of objects of type ID or an empty list 
     *         if command contains none
     */
    public ArrayList<Integer> getAllIdTokenValues() {
        ArrayList<Integer> idTokens = new ArrayList<Integer>();
        for (Token token : this) {
            if (token.type == Type.ID) {
                idTokens.add(Integer.parseInt(token.value));
            }
        }
        return idTokens;
    }

    /**
     * Retrieves the first RESERVED token of this command
     * 
     * @return First Token object of type RESERVED or an EMPTY Token if command
     *         contains none
     */
    public Token getReservedToken() {
        for (Token token : this) {
            if (token.type == Type.RESERVED) {
                return token;
            }
        }
        return Token.EMPTY_TOKEN;
    }

    @Override
    public Iterator<Token> iterator() {
        return mTokens.iterator();
    }
}
