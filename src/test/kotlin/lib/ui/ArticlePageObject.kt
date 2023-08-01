package lib.ui

import io.appium.java_client.AppiumDriver
import lib.Platform
import org.openqa.selenium.WebElement

abstract class ArticlePageObject(driver: AppiumDriver<WebElement>) : MainPageObject(driver) {

    abstract val TITLE: String
    open var BUTTON_SAVE = ""
    open var TITLE_IOS_FOR_ASSERT = ""
    abstract val FOOTER_ELEMENT: String
    open var OPTIONS_BUTTON: String = ""
    abstract val OPTIONS_ADD_TO_MY_LIST_BUTTON: String
    open var ADD_TO_MY_LIST_OVERLAY: String = ""
    open var MY_LIST_NAME_INPUT: String = ""
    open var MY_LIST_OK_BUTTON: String = ""
    abstract val CLOSE_ARTICLE_BUTTON: String
    open var MY_READING_LIST_TPL: String = ""

    fun getArticleName(article_title: String): String {
        return TITLE_IOS_FOR_ASSERT.replace("{TITLE}", article_title)
    }

    fun titleIsPresent(article_title: String){
        val first_article_xpath = getArticleName(article_title)
        this.waitForElementPresent(first_article_xpath,"Article not present",10)
    }



    fun waitForTitleElement(): WebElement {
        return this.waitForElementPresent(TITLE, "Cannot find article title on page", 15)
    }

    fun getArticleTitle(): String {
        val title_element: WebElement = waitForTitleElement()
        return if (Platform.getInstance().isAndroid())
            title_element.text
        else
            title_element.tagName
    }

    fun clickButtonSave() {
        this.waitForElementAndClick(BUTTON_SAVE, "Cannot find and click button Save", 5)
    }
}