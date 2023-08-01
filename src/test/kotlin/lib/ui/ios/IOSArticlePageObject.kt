package lib.ui.ios

import io.appium.java_client.AppiumDriver
import lib.ui.ArticlePageObject
import org.openqa.selenium.WebElement

class IOSArticlePageObject(driver: AppiumDriver<WebElement>): ArticlePageObject(driver) {
    override val TITLE = "id:Java (programming language)"
    override var TITLE_IOS_FOR_ASSERT = "id:{TITLE}"
    override val FOOTER_ELEMENT = "id:View article in browser"
    override val OPTIONS_ADD_TO_MY_LIST_BUTTON = "id:Save for later"
    override val CLOSE_ARTICLE_BUTTON =
        "xpath://XCUIElementTypeButton[@name='Search']"
}