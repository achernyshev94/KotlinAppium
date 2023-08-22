package suites

import org.junit.runner.RunWith
import org.junit.runners.Suite
import tests.ArticleTests
import tests.GetStartedTest
import tests.SearchTests

@RunWith(Suite::class)
@Suite.SuiteClasses(
    ArticleTests::class,
    GetStartedTest::class,
    SearchTests::class
)

class TestSuite {
}