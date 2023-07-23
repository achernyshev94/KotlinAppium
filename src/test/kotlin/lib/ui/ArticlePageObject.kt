package lib.ui

import io.appium.java_client.AppiumDriver
import org.openqa.selenium.WebElement

open class ArticlePageObject(driver: AppiumDriver<WebElement>): MainPageObject(driver) {

    private val TITLE = "xpath://*[@class='android.view.View']//*[@class='android.view.View'][@index='0']//*[@class='android.widget.TextView'][@index='0']"
    private val BUTTON_SAVE = "xpath://*[contains(@text, 'Save')]"

    fun waitForTitleElement(): WebElement {
        return this.waitForElementPresent(TITLE, "Cannot find article title on page", 15)
    }

    fun getArticleTitle(): String {
        val title_element: WebElement = waitForTitleElement()
        return title_element.text
    }

    fun clickButtonSave() {
        this.waitForElementAndClick(BUTTON_SAVE, "Cannot find and click button Save", 5)
    }
}