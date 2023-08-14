package lib.ui.factories


import lib.Platform
import lib.ui.NavigationPageObject
import lib.ui.android.AndroidNavigationUIPageObject
import lib.ui.ios.IOSNavigationPageObject
import lib.ui.mobile_web.MWNavigation
import org.openqa.selenium.remote.RemoteWebDriver

class NavigationPageObjectFactory {
    companion object {
        fun get(driver: RemoteWebDriver): NavigationPageObject {
            return when {
                Platform.getInstance().isAndroid() -> AndroidNavigationUIPageObject(driver)
                Platform.getInstance().isIOS() -> IOSNavigationPageObject(driver)
                Platform.getInstance().isMW() -> MWNavigation(driver)
                else -> throw Exception("Unknown platform")
            }
        }
    }
}
