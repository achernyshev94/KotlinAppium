package lib.ui.android


import io.appium.java_client.AppiumDriver
import lib.ui.NavigationPageObject
import org.openqa.selenium.WebElement

class AndroidNavigationUIPageObject(driver: AppiumDriver<WebElement>): NavigationPageObject(driver) {

    override val MY_LIST_LINK = "xpath://*[@resource-id='org.wikipedia:id/fragment_main_nav_tab_layout']//*[@content-desc='My lists']"

}