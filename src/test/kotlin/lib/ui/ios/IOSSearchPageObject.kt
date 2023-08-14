package lib.ui.ios


import lib.ui.SearchPageObject
import org.openqa.selenium.remote.RemoteWebDriver

class IOSSearchPageObject(driver: RemoteWebDriver): SearchPageObject(driver) {

    override val SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']"
    override val SEARCH_INPUT = "xpath://XCUIElementTypeSearchField[@label='Search Wikipedia']"
    override val SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeStaticText[contains(@name,'{SUBSTRING}')]"
    override val SEARCH_RESULT_CONTAINER = "xpath://XCUIElementTypeCell"
    override val SEARCH_CANCEL_BUTTON = "xpath://XCUIElementTypeButton[@name='Clear text']"
    override var CLOSE_SEARCH_SCREEN = "xpath://XCUIElementTypeStaticText[@name='Cancel']"
    override val SEARCH_RESULT_LIST = "xpath://XCUIElementTypeOther[1]/XCUIElementTypeCollectionView"
    override val SEARCH_EMPTY_RESULT_ELEMENT = "xpath://XCUIElementTypeStaticText[@name='No results found']"
}