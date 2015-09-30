package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ParserTest {
    // Use the same Parser object across all test cases
    private Parser mParser = new Parser();

    private void testParser(String command, String expected) {
        System.out.println("Parsing: " + command);
        mParser.parseCommand(command);
        System.out.println("Parsed Tokens: " + mParser.getParsedTokens());
        System.out.println("Expected: " + expected);

        assertEquals(expected, mParser.getParsedTokens());
    }

    @Test
    public void parserTokenizerTest() {
        String command = "add -s 23/09 -e 4pm *@(*#(!&@! 'Task name'";
        String expected = "[RESERVED=add][WHITESPACE][FLAG=s][WHITESPACE]"
                + "[DATE=23/09][WHITESPACE][FLAG=e][WHITESPACE][DATE=4pm]"
                + "[WHITESPACE][ALPHA_NUM=*@(*#(!&@!][WHITESPACE]"
                + "[NAME=Task name]";

        testParser(command, expected);
    }

    /*****************************************************************
     * TESTING PARSE COMMAND METHODS
     *****************************************************************/
    @Test
    public void parseCommandTest() {
        // Test cases for valid command formats
        String correctCommand = "add **(#!(*@&#!(@&";
        String correctCommandExpected = "[RESERVED=add][WHITESPACE]"
                + "[ALPHA_NUM=**(#!(*@&#!(@&]";
        String singleTokenCommand = "add";
        String singleTokenCommandExpected = "[RESERVED=add]";

        testParser(correctCommand, correctCommandExpected);
        testParser(singleTokenCommand, singleTokenCommandExpected);

        // Test cases for invalid command formats or incorrect number of tokens
        String incorrectKeywordCommand = "addd *(&(!*&@*!*(@&(";
        String incorrectKeywordCommandExpected = "[ALPHA_NUM=addd][WHITESPACE]"
                + "[ALPHA_NUM=*(&(!*&@*!*(@&(]";
        String singleTokenIncorrectKeywordCommand = "adzzd";
        String singleTokenIncorrectKeywordCommandExpected = "[ALPHA_NUM=adzzd]";
        String emptyCommand = "";
        String emptyCommandExpected = "";

        testParser(incorrectKeywordCommand, incorrectKeywordCommandExpected);
        testParser(singleTokenIncorrectKeywordCommand,
                singleTokenIncorrectKeywordCommandExpected);
        testParser(emptyCommand, emptyCommandExpected);
    }
}