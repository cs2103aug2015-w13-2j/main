package sg.edu.cs2103aug2015_w13_2j.parser;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.commands.AddHandler;
import sg.edu.cs2103aug2015_w13_2j.parser.ParserInterface.IllegalDateFormatException;

public class ParserTest {
    private static final Logger LOGGER = Logger.getLogger(ParserTest.class
            .getName());
    private Parser mParser = Parser.getInstance();
    private Logic mLogic = Logic.getInstance();

    @BeforeClass
    public static void setup() {
        Logic.getInstance().registerCommandHandler(new AddHandler());
    }

    /**
     * Compares the expected string of parsed tokens from parsing the provided
     * command string to the actual string of parsed tokens
     * 
     * @param command
     *            Command string to parse
     * @param expected
     *            Expected string of parsed tokens
     */
    private void testCommandParser(String command, String expected) {
        LOGGER.log(Level.INFO, "Parsing: " + command);
        mParser.parseCommand(mLogic, command);
        LOGGER.log(Level.INFO, "Parsed Tokens: " + mParser.getParsedTokens());
        LOGGER.log(Level.INFO, "Expected: " + expected);
        assertEquals(expected, mParser.getParsedTokens());
    }

    private void testDatetimeParser(String datetime, String expected)
            throws IllegalDateFormatException {
        LOGGER.log(Level.INFO, "Parsing: " + datetime);
        String actual = ParserInterface.parseDate(datetime);
        LOGGER.log(Level.INFO, "Parsed datetime: " + actual);
        LOGGER.log(Level.INFO, "Expected: " + expected);
        assertEquals(expected, actual);
    }

    @Test
    public void parserTokenizerTest() {
        Calendar date1 = new GregorianCalendar(2015, 9 - 1, 23);

        String command = "add -s 23/09 -e 4pm *@(*#(!&@! 'Task name'";
        String expected = "[RESERVED=add][FLAG=s][DATE="
                + date1.getTimeInMillis() + "]"
                + "[FLAG=e][DATE_INVALID=4pm][ALPHA_NUM=*@(*#(!&@!]"
                + "[NAME=Task name]";

        testCommandParser(command, expected);
    }

    @Test
    public void parserDateTimeFormatsTest() throws IllegalDateFormatException {
        testDatetimeParser("23/09/2015T10:11", "23_9_2015_10_11");
        testDatetimeParser("23/09/2015T10", "23_9_2015_10_mm");
        testDatetimeParser("23/09/2015", "23_9_2015_HH_mm");
        testDatetimeParser("23/09T10:11", "23_9_yyyy_10_11");
        testDatetimeParser("23/09T10", "23_9_yyyy_10_mm");
        testDatetimeParser("23/09", "23_9_yyyy_HH_mm");
        testDatetimeParser("23T10:11", "23_MM_yyyy_10_11");
        testDatetimeParser("23T10", "23_MM_yyyy_10_mm");
        testDatetimeParser("23", "23_MM_yyyy_HH_mm");
    }

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

        testCommandParser(correctCommand, correctCommandExpected);
        testCommandParser(singleTokenCommand, singleTokenCommandExpected);

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

        testCommandParser(incorrectKeywordCommand,
                incorrectKeywordCommandExpected);
        testCommandParser(singleTokenIncorrectKeywordCommand,
                singleTokenIncorrectKeywordCommandExpected);
        testCommandParser(emptyCommand, emptyCommandExpected);
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

        testCommandParser(validTaskName, validTaskNameExpected);
        testCommandParser(validTaskNameAlternative,
                validTaskNameAlternativeExpected);
        testCommandParser(validNumericTaskName, validNumericTaskNameExpected);
        testCommandParser(noClosingWrapper, noClosingWrapperExpected);

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

        testCommandParser(noWrappers, noWrappersExpected);
        testCommandParser(numericTaskName, numericTaskNameExpected);
        testCommandParser(noOpeningWrapper, noOpeningWrapperExpected);
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
        String validOptionsExpected = "[FLAG=s][DATE="
                + date1.getTimeInMillis() + "][FLAG=e][DATE="
                + date2.getTimeInMillis() + "]";
        String emptyToken = "";
        String emptyTokenExpected = "";

        testCommandParser(singleValidOption, singleValidOptionExpected);
        testCommandParser(validOptions, validOptionsExpected);
        testCommandParser(emptyToken, emptyTokenExpected);

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

        testCommandParser(singleToken, singleTokenExpected);
        testCommandParser(invalidSecondOption, invalidSecondOptionExpected);
        testCommandParser(invalidSecondOptionField,
                invalidSecondOptionFieldExpected);

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

        testCommandParser(singleInvalidToken, singleInvalidTokenExpected);
        testCommandParser(invalidOptionWithField,
                invalidOptionWithFieldExpected);
        testCommandParser(invalidFlagThenTaskName,
                invalidFlagThenTaskNameExpected);
    }

    @Test
    public void parserRegression119Test() {
        String command = "a 'lunch with someone' -s ''";
        String commandExpected = "[RESERVED=a][NAME=lunch with someone][FLAG=s][DATE_INVALID='']";
        testCommandParser(command, commandExpected);
    }

    @Test
    public void parserRegression126Test() {
        String command = "add  ";
        String commandExpected = "[RESERVED=add]";
        testCommandParser(command, commandExpected);
    }
}