package lib.ui

import lib.Platform
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver
import io.qameta.allure.Step

abstract class ArticlePageObject(driver: RemoteWebDriver) : MainPageObject(driver) {

    abstract var USER_FOLDER_TPL: String
    abstract var TITLE: String
    open var BUTTON_SAVE = ""
    open var TITLE_IOS_FOR_ASSERT = ""
    abstract var FOOTER_ELEMENT: String
    open var OPTIONS_BUTTON: String = ""
    abstract var OPTIONS_ADD_TO_MY_LIST_BUTTON: String
    var OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = ""
    open var ADD_TO_MY_LIST_OVERLAY: String = ""
    open var MY_LIST_NAME_INPUT: String = ""
    open var MY_LIST_OK_BUTTON: String = ""
    abstract var CLOSE_ARTICLE_BUTTON: String
    open var MY_READING_LIST_TPL: String = ""

    fun getArticleName(article_title: String): String {
        return TITLE_IOS_FOR_ASSERT.replace("{TITLE}", article_title)
    }
    @Step("Утверждение, что заголовок статьи присутствует")
    fun titleIsPresent(article_title: String){
        val first_article_xpath = getArticleName(article_title)
        this.waitForElementPresent(first_article_xpath,"Article not present",10)
    }

    @Step("Ожидание появления заголовка")
    fun waitForTitleElement(): WebElement {
        return this.waitForElementPresent(TITLE, "Cannot find article title on page", 15)
    }

    @Step("Получение заголовка статьи")
    fun getArticleTitle(): String?
    {
        val titleElement = waitForTitleElement()
        screenshot(this.takeScreenShot("at"))
        return when {
            Platform.getInstance().isAndroid() -> titleElement?.getAttribute("text")
            Platform.getInstance().isMW() -> titleElement?.text
            else -> throw Exception("Unknown platform")
        }
    }
    @Step("Тап на кнопку сохранить")
    fun clickButtonSave() {
        this.waitForElementAndClick(BUTTON_SAVE, "Cannot find and click button Save", 5)
    }


    fun addArticleToMySaved() {
        if(Platform.getInstance().isMW()) {
            this.removeArticleFromSavedIfItAdded()
        }
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find option to add article to reading list", 5)
    }
    @Step("Удаление сохраненной статьи")
    private fun removeArticleFromSavedIfItAdded() {
        if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)) {
            this.waitForElementAndClick(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON, "Cannot click to remove an article from saved", 2)
            this.waitForElementPresent(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find button to add an article to saved list after removing it from this list before", 2)
        }
    }

    fun getURLArticle(): String? {
        if(Platform.getInstance().isMW()) {
            return getURL()?.replace('_', ' ')
        } else {
            println("Method getURLArticle() does nothing for platform ${Platform.getInstance().getPlatformName()}")
        }
        return null
    }
}