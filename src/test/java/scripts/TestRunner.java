package scripts;

import org.testng.TestNG;
import scripts.LoginTest;

import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    public static void main(String[] args) {
        TestNG testng = new TestNG();

        // List of test classes to run
        List<Class<?>> testClasses = new ArrayList<>();
//        testClasses.add(LoginTest.class);
//        testClasses.add(SearchTest.class);

        // Set the test classes in TestNG instance
        testng.setTestClasses(testClasses.toArray(new Class[0]));

        // Execute the tests
        testng.run();
    }
}
