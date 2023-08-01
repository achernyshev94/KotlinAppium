package lib.ui.factories

import io.appium.java_client.AppiumDriver
import lib.Platform
import lib.ui.SavedListsPageObject
import lib.ui.android.AndroidMyListPagePageObject
import lib.ui.ios.IOSMyListPageObject
import org.openqa.selenium.WebElement

class SavedListsPageObjectFactory {
    companion object {
        fun get(driver: AppiumDriver<WebElement>): SavedListsPageObject {
            if (Platform.getInstance().isAndroid()) return AndroidMyListPagePageObject(driver)
            else return IOSMyListPageObject(driver)
        }
    }
}