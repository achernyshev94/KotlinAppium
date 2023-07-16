package lib.ui

import io.appium.java_client.AppiumDriver
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

class NavigationPageObject(driver: AppiumDriver<WebElement>): MainPageObject(driver) {

    private val BUTTON_BACK_ARTICLE = "//*[@class='android.widget.ImageButton'][@content-desc='Navigate up']"
    private val BUTTON_BACK_MAIN = "//*[@class='android.widget.ImageButton'][@index='0']"
    private val BUTTON_SAVED = "//*[@class='android.widget.FrameLayout'][@content-desc='Saved']"

    fun clickButtonBackArticle() {
        this.waitForElementAndClick(By.xpath(BUTTON_BACK_ARTICLE), "Cannot find and click button Back", 5)
    }

    fun clickButtonBackMain() {
        this.waitForElementAndClick(By.xpath(BUTTON_BACK_MAIN), "Cannot find and click button Back", 5)
    }

    fun clickButtonSaved() {
        this.waitForElementAndClick(By.xpath(BUTTON_SAVED), "Cannot find and click button Saved", 5)
    }
}