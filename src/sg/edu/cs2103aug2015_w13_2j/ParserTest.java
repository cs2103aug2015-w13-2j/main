package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.cs2103aug2015_w13_2j.ParserInterface.IllegalDateFormatException;
import sg.edu.cs2103aug2015_w13_2j.commands.AddHandler;

public class ParserTest {
    // Use the same Parser object across all test cases
    private Parser mParser = Parser.getInstance();
    private Logic mLogic = Logic.getInstance();

    @BeforeClass
    public static void setup() {
        Logic.getInstance().registerCommandHandler(new AddHandler());
    }

    /**
     * Compares this command to the specified expected format of parsed tokens.
     * 
     * @param command
     *            User command to test
     * @param expected
     *            Expected parsed tokens format for the command input
     */
    private void testParser(String command, String expected) {
        new FunDUE();
        System.out.println("Parsing: " + command);
        mParser.parseCommand(mLogic, command);
        System.out.println("Parsed Tokens: " + mParser.getParsedTokens());
        System.out.println("Expected: " + expected);

        assertEquals(expected, mParser.getParsedTokens());
    }

    @Test
    public void parserTokenizerTest() {
        Calendar date1 = new GregorianCalendar(2015, 9 - 1, 23);

        String command = "add -s 23/09 -e 4pm *@(*#(!&@! 'Task name'";
        String expected = "[RESERVED=add][FLAG=s][DATE="
                + date1.getTimeInMillis() + "]"
                + "[FLAG=e][DATE_INVALID=4pm][ALPHA_NUM=*@(*#(!&@!]"
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
        // Equivalence partition for case of 'Valid command Token'
        // These are cases where a valid command is recognized:
        // 1.) Valid first token and random symbols
        // 2.) Single token that is, itself, a valid keyword
        String correctCommand = "add **(#!(ab*@&#!(@&";
        String correctCommandExpected = "[RESERVED=add][ALPHA_NUM=**(#!(ab*@&#!(@&]";
        String singleTokenCommand = "add";
        String singleTokenCommandExpected = "[RESERVED=add]";

        testParser(correctCommand, correctCommandExpected);
        testParser(singleTokenCommand, singleTokenCommandExpected);

        // Equivalence partitions for cases of 'Invalid command Token'
        // These are cases where an invalid command is found:
        // 1.) Invalid command spelling
        // 2.) Incorrect number of tokens
        String incorrectKeywordCommand = "addd *(&(!*&@*!*(@&(";
        String incorrectKeywordCommandExpected = "[ALPHA_NUM=addd]"
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
        // Equivalence partition for a 'Valid task name'
        // These are test cases for a valid task name recognized:
        // 1.) Test with enclosing wrappers single quote ' and double quote "
        // 2.) Test with task names that are integers with enclosing wrappers
        // 3.) Test with no closing wrapper single quote ' or double quote "
        String validTaskName = "'Eat Lunch'";
        String validTaskNameExpected = "[NAME=Eat Lunch]";
        String validTaskNameAlternative = "\"Eat Lunch\"";
        String validTaskNameAlternativeExpected = "[NAME=Eat Lunch]";
        String validNumericTaskName = "'12345'";
        String validNumericTaskNameExpected = "[NAME=12345]";
        String noClosingWrapper = "'Eat Lunch";
        String noClosingWrapperExpected = "[NAME=Eat Lunch]";

        testParser(validTaskName, validTaskNameExpected);
        testParser(validTaskNameAlternative, validTaskNameAlternativeExpected);
        testParser(validNumericTaskName, validNumericTaskNameExpected);
        testParser(noClosingWrapper, noClosingWrapperExpected);

        // Equivalence partition for an 'Invalid task name'
        // These are test cases for a task name with incomplete/unaccepted
        // wrappers:
        // 1.) Test with an absence of both opening and closing wrappers with
        // letters as names
        // 2.) Test with an absence of both opening and closing wrappers with
        // integers as names
        // 3.) Test with an absence of opening wrapper
        String noWrappers = "Eat Lunch";
        String noWrappersExpected = "[ALPHA_NUM=Eat][ALPHA_NUM=Lunch]";
        String numericTaskName = "12345";
        String numericTaskNameExpected = "[ID=12345]";
        String noOpeningWrapper = "Eat Lunch'";
        String noOpeningWrapperExpected = "[ALPHA_NUM=Eat][ALPHA_NUM=Lunch']";

        testParser(noWrappers, noWrappersExpected);
        testParser(numericTaskName, numericTaskNameExpected);
        testParser(noOpeningWrapper, noOpeningWrapperExpected);
    }

    /*****************************************************************
     * TESTING PARSE OPTIONS IN COMMANDLINE METHODS
     *****************************************************************/
    @Test
    public void parseAllOptionsTest() {
        // Initialize dates used for this test case
        Calendar date1 = new GregorianCalendar(2015, 9 - 1, 23);
        Calendar date2 = new GregorianCalendar(2015, 9 - 1, 24);
        Calendar date3 = new GregorianCalendar(2015, 9 - 1, 25);

        // Equivalence partition for an 'Valid option(s)'
        // These are test cases for valid flag-date pairs recognized:
        // 1.) Test with single valid option
        // 2.) Test with multiple valid options
        // 3.) Test with empty option field
        String singleValidOption = "-s 23/09";
        String singleValidOptionExpected = "[FLAG=s][DATE="
                + date1.getTimeInMillis() + "]";
        String validOptions = "-s 23/09 -e 24/09";
        String validOptionsExpected = "[FLAG=s][DATE=" + date1.getTimeInMillis()
                + "][FLAG=e][DATE=" + date2.getTimeInMillis() + "]";
        String emptyToken = "";
        String emptyTokenExpected = "";

        testParser(singleValidOption, singleValidOptionExpected);
        testParser(validOptions, validOptionsExpected);
        testParser(emptyToken, emptyTokenExpected);

        // Equivalence partition for 'Invalid option(s)'
        // These are test cases for invalid flag-date pairs recognized:
        // 1.) Test with flag but no specified date
        // 2.) Test with a date that is not attached to any flag/option
        // 3.) Test with a single valid flag-date pair, followed by
        // a flag with no specified date
        String singleToken = "-s";
        String singleTokenExpected = "[FLAG=s]";
        String invalidSecondOption = "-s 23/09 24/09 -e 25/09";
        String invalidSecondOptionExpected = "[FLAG=s][DATE="
                + date1.getTimeInMillis() + "][ID_INVALID=24/09][FLAG=e]"
                + "[DATE=" + date3.getTimeInMillis() + "]";
        String invalidSecondOptionField = "-s 23/09 -e";
        String invalidSecondOptionFieldExpected = "[FLAG=s]" + "[DATE="
                + date1.getTimeInMillis() + "][FLAG=e]";

        testParser(singleToken, singleTokenExpected);
        testParser(invalidSecondOption, invalidSecondOptionExpected);
        testParser(invalidSecondOptionField, invalidSecondOptionFieldExpected);

        // Equivalence partition for 'Invalid flag(s)'
        // These are test cases for invalid flags recognized
        // 1.) Test with flag with wrong spelling
        // 2.) Test with flag with wrong spelling, followed by a date
        // 3.) Test with flag followed by a task name instead of a valid date
        String singleInvalidToken = "-ss";
        String singleInvalidTokenExpected = "[FLAG_INVALID=ss]";
        String invalidOptionWithField = "-ss 23/09";
        String invalidOptionWithFieldExpected = "[FLAG_INVALID=ss]"
                + "[ID_INVALID=23/09]";
        String invalidFlagThenTaskName = "add -s 'Do Homework'";
        String invalidFlagThenTaskNameExpected = "[RESERVED=add]"
                + "[FLAG=s][DATE_INVALID='Do]" + "[ALPHA_NUM=Homework']";

        testParser(singleInvalidToken, singleInvalidTokenExpected);
        testParser(invalidOptionWithField, invalidOptionWithFieldExpected);
        testParser(invalidFlagThenTaskName, invalidFlagThenTaskNameExpected);
    }

}