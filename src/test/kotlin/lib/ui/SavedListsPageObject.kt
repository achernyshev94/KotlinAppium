package lib.ui

import lib.Platform
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver
import io.qameta.allure.Step

abstract class SavedListsPageObject(driver: RemoteWebDriver): MainPageObject(driver) {

    abstract var FOLDER_BY_NAME_TPL: String
    private val SAVED_FOLDER = "xpath://*[@resource-id='org.wikipedia:id/recycler_view']//*[@resource-id='org.wikipedia:id/item_title']"
    private val ARTICLE = "xpath://*[contains(@text, '{SEARCH_RESULT}')]"

    open var FOLDER_MY_NAME_TPL = ""
    open var ARTICLE_BY_TITLE_TPL = ""
    var REMOVE_FROM_SAVED_TPL = ""
    open var FIRST_ARTICLE_ON_MY_LIST_SCREEN = ""
    open var BUTTON_CLOSE_SYNC_MY_ARTICLES = ""
    open var FIRST_ARTICLE_IOS = ""
    open var BUTTON_DELETE_ARTICLE = ""
    open var FIRST_ARTICLE_IOS_TO_DELETE = ""


    fun getArticleName(searchResult: String): String {
        return ARTICLE.replace("{SEARCH_RESULT}", searchResult)
    }

    fun clickOnSavedFolder() {
        this.waitForElementAndClick(SAVED_FOLDER, "Cannot find and click on saved folder", 5)
    }

    @Step("Свайп для удаления сохраненной статьи с заголовком")
    fun swipeArticle(searchResult: String) {
        waitForArticleToAppear(searchResult)
        val article = getArticleName(searchResult)
        this.swipeElementToLeft(article, "Cannot find saved article $searchResult")
        this.waitForArticleToDisappear(article)
    }

    fun waitForArticleToAppear(searchResult: String) {
        val article = getArticleName(searchResult)
        this.waitForElementNotPresent(article, "Cannot find saved article $searchResult", 15)
    }

    fun waitForArticleToDisappear(searchResult: String) {
        val article = getArticleName(searchResult)
        this.waitForElementNotPresent(article, "Saved article $searchResult still present", 15)
    }

    fun waitForArticleAndClick(searchResult: String) {
        val article = getArticleName(searchResult)
        this.waitForElementAndClick(article, "Cannot find and click article $searchResult", 5)
    }

    fun getFirstArticleName(article_title: String): String {
        return FIRST_ARTICLE_IOS.replace("{TITLE}", article_title)
    }

    fun articleIsPresent(article_title: String){
        val first_article_xpath = getFirstArticleName(article_title)
        this.waitForElementPresent(first_article_xpath,"Article not present",10)
    }

    fun swipeAndDeleteFirstArticleIos() {
        this.swipeElementToLeft(FIRST_ARTICLE_IOS_TO_DELETE, "Cannot find first article")
        this.waitForElementAndClick(BUTTON_DELETE_ARTICLE, "Cannot delete first article", 10)
    }

    fun openFirstArticleIos() {
        this.waitForElementAndClick(FIRST_ARTICLE_IOS_TO_DELETE, "Cannot open first article",10)
    }

    fun clickButtonCloseSyncMyArticlesWindow() {
        this.waitForElementAndClick(
            BUTTON_CLOSE_SYNC_MY_ARTICLES,
            "Cannot find and click button close sync articles window)",
            10
        )
    }

    fun waitForTitleElementFromMyListScreen(): WebElement {
        return this.waitForElementPresent(
            FIRST_ARTICLE_ON_MY_LIST_SCREEN,
            "Cannot find article title on page",
            10
        )
    }

    fun getArticleTitleFromMyListScreen(): String {
        val title_element: WebElement = waitForTitleElementFromMyListScreen()
        return title_element.text
    }

    private fun getRemoveButtonByTitle(articleTitle: String): String {
        return REMOVE_FROM_SAVED_TPL.replace("{TITLE}", articleTitle)
    }

    fun swipeByArticleToDelete(articleTitle: String) {
        this.waitForArticleToAppearByTitle(articleTitle)
        val articleTitleXpath = getSavedArticleXpathByTitle(articleTitle)
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
            this.swipeElementToLeft(articleTitleXpath, "Cannot find saved article $articleTitle")
        } else {
            val removeLocator = getRemoveButtonByTitle(articleTitle)
            this.waitForElementAndClick(removeLocator, "Cannot click button to remove article from saved", 10)
        }
        if(Platform.getInstance().isMW()) {
            Platform.getInstance().getDriver().navigate().refresh()

        }
        this.waitForArticleToDisappearByTitle(articleTitle)
    }

    @Step("Ожидание появления заголовка '{articleTitle}' в статье")
    private fun waitForArticleToAppearByTitle(articleTitle: String) {
        val articleTitleXpath = getSavedArticleXpathByTitle(articleTitle)
        this.waitForElementPresent(articleTitleXpath, "Cannot find saved article by title $articleTitle", 15)
    }

    @Step("Ожидание исчезновения заголовка '{articleTitle}' в статье")
    private fun waitForArticleToDisappearByTitle(articleTitle: String) {
        val articleTitleXpath = getSavedArticleXpathByTitle(articleTitle)
        this.waitForElementNotPresent(articleTitleXpath, "Saved article still present with title $articleTitle", 20)
    }

    private fun getSavedArticleXpathByTitle(articleTitle: String): String {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", articleTitle)
    }
}