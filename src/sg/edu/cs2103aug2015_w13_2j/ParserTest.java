package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import sg.edu.cs2103aug2015_w13_2j.ParserInterface.IllegalDateFormatException;

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
        Calendar date1 = new GregorianCalendar(2015, 9 - 1, 23);

        String command = "add -s 23/09 -e 4pm *@(*#(!&@! 'Task name'";
        String expected = "[RESERVED=add][WHITESPACE][FLAG=s][WHITESPACE]"
                + "[DATE=" + date1.getTimeInMillis()
                + "][WHITESPACE][FLAG=e][WHITESPACE][DATE_INVALID=4pm]"
                + "[WHITESPACE][ALPHA_NUM=*@(*#(!&@!][WHITESPACE]"
                + "[NAME=Task name]";

        testParser(command, expected);
    }

    @Test
    public void parserDateParserTest() throws IllegalDateFormatException {
        // Normal date with all relevant information
        assertEquals("1442937600000", ParserInterface.parseDate("23/09/2015"));

        assertEquals("1442937600000", ParserInterface.parseDate("23/09"));
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
        Calendar date1 = new GregorianCalendar(2015, 9 - 1, 23);
        Calendar date2 = new GregorianCalendar(2015, 9 - 1, 24);
        Calendar date3 = new GregorianCalendar(2015, 9 - 1, 25);
        // Test cases for valid options
        String validOptions = "-s 23/09 -e 24/09";
        String validOptionsExpected = "[FLAG=s][WHITESPACE][DATE="
                + date1.getTimeInMillis() + "]"
                + "[WHITESPACE][FLAG=e][WHITESPACE][DATE="
                + date2.getTimeInMillis() + "]";
        String singleValidOption = "-s 23/09";
        String singleValidOptionExpected = "[FLAG=s][WHITESPACE][DATE="
                + date1.getTimeInMillis() + "]";
        String emptyToken = "";
        String emptyTokenExpected = "";

        testParser(validOptions, validOptionsExpected);
        testParser(singleValidOption, singleValidOptionExpected);
        testParser(emptyToken, emptyTokenExpected);

        // Test cases for invalid number of options
        String singleToken = "-s";
        String singleTokenExpected = "[FLAG=s]";
        String invalidSecondOption = "-s 23/09 24/09 -e 25/09";
        String invalidSecondOptionExpected = "[FLAG=s][WHITESPACE][DATE="
                + date1.getTimeInMillis() + "]"
                + "[WHITESPACE][ID_INVALID=24/09][WHITESPACE][FLAG=e]"
                + "[WHITESPACE][DATE=" + date3.getTimeInMillis() + "]";
        String invalidSecondOptionField = "-s 23/09 -e";
        String invalidSecondOptionFieldExpected = "[FLAG=s][WHITESPACE]"
                + "[DATE=" + date1.getTimeInMillis() + "][WHITESPACE][FLAG=e]";

        testParser(singleToken, singleTokenExpected);
        testParser(invalidSecondOption, invalidSecondOptionExpected);
        testParser(invalidSecondOptionField, invalidSecondOptionFieldExpected);

        // Test cases for having an invalid option
        String singleInvalidToken = "-ss";
        String singleInvalidTokenExpected = "[FLAG_INVALID=ss]";
        String invalidOptionWithField = "-ss 23/09";
        String invalidOptionWithFieldExpected = "[FLAG_INVALID=ss][WHITESPACE]"
                + "[ID_INVALID=23/09]";

        testParser(singleInvalidToken, singleInvalidTokenExpected);
        testParser(invalidOptionWithField, invalidOptionWithFieldExpected);

        // Test cases for having an invalid flag followed by task name
        String invalidFlagThenTaskName = "add -s 'Do Homework'";
        String invalidFlagThenTaskNameExpected = "[RESERVED=add][WHITESPACE]"
                + "[FLAG=s][WHITESPACE][DATE_INVALID='Do][WHITESPACE]"
                + "[ALPHA_NUM=Homework']";
        testParser(invalidFlagThenTaskName, invalidFlagThenTaskNameExpected);
    }

}