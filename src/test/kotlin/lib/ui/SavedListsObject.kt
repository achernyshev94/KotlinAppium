package lib.ui

import io.appium.java_client.AppiumDriver
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

class SavedListsObject(driver: AppiumDriver<WebElement>): MainPageObject(driver) {

    private val SAVED_FOLDER = "//*[@resource-id='org.wikipedia:id/recycler_view']//*[@resource-id='org.wikipedia:id/item_title']"
    private val ARTICLE = "//*[contains(@text, '{SEARCH_RESULT}')]"

    fun getArticleName(searchResult: String): String {
        return ARTICLE.replace("{SEARCH_RESULT}", searchResult)
    }

    fun clickOnSavedFolder() {
        this.waitForElementAndClick(By.xpath(SAVED_FOLDER), "Cannot find and click on saved folder", 5)
    }

    fun swipeArticle(searchResult: String) {
        waitForArticleToAppear(searchResult)
        val article = getArticleName(searchResult)
        this.swipeElementToLeft(By.xpath(article), "Cannot find saved article $searchResult")
        this.waitForArticleToDisappear(article)
    }

    fun waitForArticleToAppear(searchResult: String) {
        val article = getArticleName(searchResult)
        this.waitForElementNotPresent(By.id(article), "Cannot find saved article $searchResult", 15)
    }

    fun waitForArticleToDisappear(searchResult: String) {
        val article = getArticleName(searchResult)
        this.waitForElementNotPresent(By.id(article), "Saved article $searchResult still present", 15)
    }

    fun waitForArticleAndClick(searchResult: String) {
        val article = getArticleName(searchResult)
        this.waitForElementAndClick(By.id(article), "Cannot find and click article $searchResult", 5)
    }
}