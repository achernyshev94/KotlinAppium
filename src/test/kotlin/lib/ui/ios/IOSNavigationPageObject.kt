package lib.ui.ios

import lib.ui.NavigationPageObject
import org.openqa.selenium.remote.RemoteWebDriver

class IOSNavigationPageObject(driver: RemoteWebDriver): NavigationPageObject(driver) {
    override val MY_LIST_LINK = "xpath://XCUIElementTypeButton[@name='Saved']"
}