package lib.ui

import io.appium.java_client.AppiumDriver
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

open class ArticlePageObject(driver: AppiumDriver<WebElement>): MainPageObject(driver) {

    private val TITLE = "//*[@class='android.view.View']//*[@class='android.view.View'][@index='0']//*[@class='android.widget.TextView'][@index='0']"
    private val BUTTON_SAVE = "//*[contains(@text, 'Save')]"

    fun waitForTitleElement(): WebElement {
        return this.waitForElementPresent(By.xpath(TITLE), "Cannot find article title on page", 15)
    }

    fun getArticleTitle(): String {
        val title_element: WebElement = waitForTitleElement()
        return title_element.text
    }

    fun clickButtonSave() {
        this.waitForElementAndClick(By.xpath(BUTTON_SAVE), "Cannot find and click button Save", 5)
    }
}