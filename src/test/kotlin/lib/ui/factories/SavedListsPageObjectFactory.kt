package lib.ui.factories

import lib.Platform
import lib.ui.SavedListsPageObject
import lib.ui.android.AndroidMyListPagePageObject
import lib.ui.ios.IOSMyListPageObject
import lib.ui.mobile_web.MWMyListsPageObject
import org.openqa.selenium.remote.RemoteWebDriver

class SavedListsPageObjectFactory {
    companion object {
        fun get(driver: RemoteWebDriver): SavedListsPageObject {
            return when {
                Platform.getInstance().isAndroid() -> AndroidMyListPagePageObject(driver)
                Platform.getInstance().isIOS() -> IOSMyListPageObject(driver)
                Platform.getInstance().isMW() -> MWMyListsPageObject(driver)
                else -> throw Exception("Unknown platform")
            }
        }
    }
}