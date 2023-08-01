package lib.ui.ios

import io.appium.java_client.AppiumDriver
import lib.ui.NavigationPageObject
import org.openqa.selenium.WebElement

class IOSNavigationPageObject(driver: AppiumDriver<WebElement>): NavigationPageObject(driver) {
    override val MY_LIST_LINK = "xpath://XCUIElementTypeButton[@name='Saved']"
}