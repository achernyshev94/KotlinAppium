package lib.ui.factories


import io.appium.java_client.AppiumDriver
import lib.Platform
import lib.ui.SearchPageObject
import lib.ui.android.AndroidSearchPageObject
import lib.ui.ios.IOSSearchPageObject
import org.openqa.selenium.WebElement

open class SearchPageObjectFactory {

    companion object {
        fun get(driver: AppiumDriver<WebElement>): SearchPageObject {
            if (Platform.getInstance().isAndroid()) return AndroidSearchPageObject(driver)
            else return IOSSearchPageObject(driver)
        }
    }
}