package lib.ui

import io.appium.java_client.AppiumDriver
import org.openqa.selenium.WebElement

abstract  class NavigationPageObject(driver: AppiumDriver<WebElement>): MainPageObject(driver) {

    private val BUTTON_BACK_ARTICLE = "xpath://*[@class='android.widget.ImageButton'][@content-desc='Navigate up']"
    private val BUTTON_BACK_MAIN = "xpath://*[@class='android.widget.ImageButton'][@index='0']"
    private val BUTTON_SAVED = "xpath://*[@class='android.widget.FrameLayout'][@content-desc='Saved']"

    abstract val MY_LIST_LINK: String

    fun clickButtonBackArticle() {
        this.waitForElementAndClick(BUTTON_BACK_ARTICLE, "Cannot find and click button Back", 5)
    }

    fun clickButtonBackMain() {
        this.waitForElementAndClick(BUTTON_BACK_MAIN, "Cannot find and click button Back", 5)
    }

    fun clickButtonSaved() {
        this.waitForElementAndClick(BUTTON_SAVED, "Cannot find and click button Saved", 5)
    }
}