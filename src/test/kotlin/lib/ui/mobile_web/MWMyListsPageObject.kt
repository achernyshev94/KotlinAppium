package lib.ui.mobile_web

import lib.ui.SavedListsPageObject
import org.openqa.selenium.remote.RemoteWebDriver

abstract class MWMyListsPageObject(driver: RemoteWebDriver, override var FOLDER_BY_NAME_TPL: String) : SavedListsPageObject(driver) {
    init {
        FOLDER_BY_NAME_TPL = "xpath~//*[@text='{FOLDER_NAME}']"
        ARTICLE_BY_TITLE_TPL = "xpath~//ul[contains(@class, 'mw-mf-watchlist-page-list')]//h3[contains(text(),'{TITLE}')]"
        REMOVE_FROM_SAVED_TPL = "xpath~//ul[contains(@class, 'mw-mf-watchlist-page-list')]//h3[contains(text(),'{TITLE}')]/../../a[contains(@class, 'watch-this-article')]"
    }
}