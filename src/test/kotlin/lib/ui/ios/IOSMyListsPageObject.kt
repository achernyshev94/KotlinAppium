package lib.ui.ios

import lib.ui.SavedListsPageObject
import org.openqa.selenium.remote.RemoteWebDriver

class IOSMyListPageObject(driver: RemoteWebDriver): SavedListsPageObject(driver) {

    override var ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeStaticText[contains(@name='{TITLE}')]"
    override var BUTTON_CLOSE_SYNC_MY_ARTICLES = "xpath://XCUIElementTypeButton[@name='Close']"
    override var FIRST_ARTICLE_IOS = "xpath://XCUIElementTypeStaticText[@name='{TITLE}']"
    override var BUTTON_DELETE_ARTICLE = "xpath://XCUIElementTypeButton[@name='swipe action delete']"
    override var FIRST_ARTICLE_IOS_TO_DELETE = "xpath://XCUIElementTypeCell[1]/XCUIElementTypeOther[2]"
}