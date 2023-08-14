package lib.ui.android

import lib.ui.SavedListsPageObject
import org.openqa.selenium.remote.RemoteWebDriver

class AndroidMyListPagePageObject(driver: RemoteWebDriver): SavedListsPageObject(driver) {
    override var FOLDER_MY_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']"
    override var ARTICLE_BY_TITLE_TPL = "xpath://*[@text='{TITLE}']"
    override var FIRST_ARTICLE_ON_MY_LIST_SCREEN =
        "xpath://*[@resource-id='org.wikipedia:id/reading_list_contents']/android.widget.FrameLayout[2]//android.widget.TextView[1]"

}