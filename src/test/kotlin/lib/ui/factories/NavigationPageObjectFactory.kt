package lib.ui.factories


import io.appium.java_client.AppiumDriver
import lib.Platform
import lib.ui.NavigationPageObject
import lib.ui.android.AndroidNavigationUIPageObject
import lib.ui.ios.IOSNavigationPageObject
import org.openqa.selenium.WebElement

class NavigationPageObjectFactory {
    companion object {
        fun get(driver: AppiumDriver<WebElement>): NavigationPageObject {
            if (Platform.getInstance().isAndroid()) return AndroidNavigationUIPageObject(driver)
            else return IOSNavigationPageObject(driver)
        }
    }
}