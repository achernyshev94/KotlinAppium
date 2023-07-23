package lib.ui

import io.appium.java_client.AppiumDriver
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

class SearchPageObject(driver: AppiumDriver<WebElement>): MainPageObject(driver) {
    private val SEARCH_INIT_ELEMENT = "xpath://*[contains(@text,'Search Wikipedia')]"
    private val SEARCH_INPUT = "id:org.wikipedia:id/search_src_text"
    private val SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/search_results_container']//*[@text='{SUBSTRING}']"
    private val SEARCH_RESULT = "xpath://*[@class='android.view.ViewGroup'][@index='0']//*[@resource-id='org.wikipedia:id/page_list_item_title']"
    private val SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn"
    private val SEARCH_RESULT_EMPTY_MESSAGE = "id:org.wikipedia:id/search_empty_message"

    // TEMPLATES METHODS
    fun getResultSearchElement(substring: String): String {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring)
    }
    // TEMPLATES METHODS

    fun initSearchInput() {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5)
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "Cannot find search input after clicking search init element", 5)
    }

    fun waitForCancelButtonToAppear() {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", 5)
    }

    fun clickButtonCancelSearch() {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click cancel button", 5)
    }

    fun waitForSearchResultEmptyMessage() {
        this.waitForElementPresent(SEARCH_RESULT_EMPTY_MESSAGE, "Cannot find empty message at search result", 5)
    }

    fun typeSearchLine(search_line: String) {
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find and type into search line", 5)
    }

    fun waitForSearchResult(substring: String) {
        val search_result_substring = getResultSearchElement(substring)
        this.waitForElementPresent(search_result_substring, "Cannot find search result with substring + $substring", 5)
    }

    fun clickByArticleWithSubstring(substring: String) {
        val search_result_substring = getResultSearchElement(substring)
        this.waitForElementAndClick(search_result_substring, "Cannot find and click search result with substring + $substring", 10)
    }

    fun clickAtSearchResult() {
        this.waitForElementAndClick(SEARCH_RESULT, "Cannot find and click search result", 10)
    }
}