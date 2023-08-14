package lib.ui.mobile_web


import lib.ui.NavigationPageObject
import org.openqa.selenium.remote.RemoteWebDriver

abstract class MWNavigation(driver: RemoteWebDriver) : NavigationPageObject(driver) {
    init {
        MY_LISTS_LINK = "xpath~//a[@data-event-name='menu.unStar']/span[contains(text(),'Watchlist')]"
        OPEN_NAVIGATION = "css~#mw-mf-main-menu-button"
    }
}