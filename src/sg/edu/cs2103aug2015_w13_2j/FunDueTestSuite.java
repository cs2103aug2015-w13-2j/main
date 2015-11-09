package sg.edu.cs2103aug2015_w13_2j;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import sg.edu.cs2103aug2015_w13_2j.parser.ParserTest;
import sg.edu.cs2103aug2015_w13_2j.storage.StorageTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ LogicTest.class, ParserTest.class, TaskTest.class,
        StorageTest.class, IntegrationTests.class })
public class FunDueTestSuite {

}
