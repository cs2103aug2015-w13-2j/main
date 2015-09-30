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

    /*****************************************************************
     * TESTING PARSE TASK NAME METHODS
     *****************************************************************/
    @Test
    public void parseTaskNameTest() {
        // Test cases for a task name with enclosing wrappers
        String validTaskName = "'Eat Lunch'";
        String validTaskNameExpected = "[NAME=Eat Lunch]";
        String validTaskNameAlternative = "\"Eat Lunch\"";
        String validTaskNameAlternativeExpected = "[NAME=Eat Lunch]";

        testParser(validTaskName, validTaskNameExpected);
        testParser(validTaskNameAlternative, validTaskNameAlternativeExpected);

        // Test cases for a task name with incomplete wrappers
        // NOTE: No closing quote will result in rest of command being assumed
        // as being part of the task name
        String noWrappers = "Eat Lunch";
        String noWrappersExpected = "[ALPHA_NUM=Eat][WHITESPACE]"
                + "[ALPHA_NUM=Lunch]";
        String noOpeningWrapper = "Eat Lunch'";
        String noOpeningWrapperExpected = "[ALPHA_NUM=Eat][WHITESPACE]"
                + "[ALPHA_NUM=Lunch']";
        String noClosingWrapper = "'Eat Lunch";
        String noClosingWrapperExpected = "[NAME=Eat Lunch]";

        testParser(noWrappers, noWrappersExpected);
        testParser(noOpeningWrapper, noOpeningWrapperExpected);
        testParser(noClosingWrapper, noClosingWrapperExpected);
    }

    /*****************************************************************
     * TESTING PARSE OPTIONS IN COMMANDLINE METHODS
     *****************************************************************/
    @Test
    public void parseAllOptionsTest() {
        // Test cases for valid options
        String validOptions = "-s 23/09 -e 24/09";
        String validOptionsExpected = "[FLAG=s][WHITESPACE][DATE=23/09]"
                + "[WHITESPACE][FLAG=e][WHITESPACE][DATE=24/09]";
        String singleValidOption = "-s 23/09";
        String singleValidOptionExpected = "[FLAG=s][WHITESPACE][DATE=23/09]";
        String emptyToken = "";
        String emptyTokenExpected = "";

        testParser(validOptions, validOptionsExpected);
        testParser(singleValidOption, singleValidOptionExpected);
        testParser(emptyToken, emptyTokenExpected);

        // Test cases for invalid number of options
        String singleToken = "-s";
        String singleTokenExpected = "[FLAG=s]";
        String invalidSecondOption = "-s 23/09 24/09 -e 25/09";
        String invalidSecondOptionExpected = "[FLAG=s][WHITESPACE][DATE=23/09]"
                + "[WHITESPACE][ID_INVALID=24/09][WHITESPACE][FLAG=e]"
                + "[WHITESPACE][DATE=25/09]";
        String invalidSecondOptionField = "-s 23/09 -e";
        String invalidSecondOptionFieldExpected = "[FLAG=s][WHITESPACE]"
                + "[DATE=23/09][WHITESPACE][FLAG=e]";

        testParser(singleToken, singleTokenExpected);
        testParser(invalidSecondOption, invalidSecondOptionExpected);
        testParser(invalidSecondOptionField, invalidSecondOptionFieldExpected);

        // Test cases for having an invalid option
        String singleInvalidToken = "-ss";
        String singleInvalidTokenExpected = "[FLAG=s][DATE_INVALID=s]";
        String invalidOptionWithField = "-ss 23/09";
        String invalidOptionWithFieldExpected = "[FLAG=s][DATE_INVALID=s]"
                + "[WHITESPACE][ID_INVALID=23/09]";

        testParser(singleInvalidToken, singleInvalidTokenExpected);
        testParser(invalidOptionWithField, invalidOptionWithFieldExpected);
    }
}