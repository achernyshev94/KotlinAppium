package lib.ui

import lib.Platform
import org.openqa.selenium.remote.RemoteWebDriver
import io.qameta.allure.Step

abstract  class NavigationPageObject(driver: RemoteWebDriver): MainPageObject(driver) {

    abstract var MY_LISTS_LINK: String
    private val BUTTON_BACK_ARTICLE = "xpath://*[@class='android.widget.ImageButton'][@content-desc='Navigate up']"
    private val BUTTON_BACK_MAIN = "xpath://*[@class='android.widget.ImageButton'][@index='0']"
    private val BUTTON_SAVED = "xpath://*[@class='android.widget.FrameLayout'][@content-desc='Saved']"

    abstract val MY_LIST_LINK: String
    var OPEN_NAVIGATION = ""

    fun clickButtonBackArticle() {
        this.waitForElementAndClick(BUTTON_BACK_ARTICLE, "Cannot find and click button Back", 5)
    }

    fun clickButtonBackMain() {
        this.waitForElementAndClick(BUTTON_BACK_MAIN, "Cannot find and click button Back", 5)
    }

    fun clickButtonSaved() {
        this.waitForElementAndClick(BUTTON_SAVED, "Cannot find and click button Saved", 5)
    }

    @Step("Открытие выдвигающегося меню")
    fun openNavigation() {
        if(Platform.getInstance().isMW()) {
            this.waitForElementAndClick(OPEN_NAVIGATION, "Cannot find and click open navigatin button", 5)
        }else {
            println(
                "Method openNavigation() does nothing for platform ${Platform.getInstance().getPlatformName()}"
            )
        }
    }

    @Step("Клик на пункт меню 'My List'")
    fun clickMyLists() {
        if(Platform.getInstance().isMW()) {
            this.tryClickElementWithFewAttempts(MY_LIST_LINK, "Cannot find navigation button to My List", 5)
        } else {
            this.waitForElementAndClick(MY_LIST_LINK,"Cannot find navigation button to My List",5)
        }
    }
}