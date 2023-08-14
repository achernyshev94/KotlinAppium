package lib.ui.ios

import lib.ui.ArticlePageObject
import org.openqa.selenium.remote.RemoteWebDriver

class IOSArticlePageObject(driver: RemoteWebDriver): ArticlePageObject(driver) {
    override var TITLE = "id:Java (programming language)"
    override var TITLE_IOS_FOR_ASSERT = "id:{TITLE}"
    override var FOOTER_ELEMENT = "id:View article in browser"
    override val OPTIONS_ADD_TO_MY_LIST_BUTTON = "id:Save for later"
    override val CLOSE_ARTICLE_BUTTON =
        "xpath://XCUIElementTypeButton[@name='Search']"
}