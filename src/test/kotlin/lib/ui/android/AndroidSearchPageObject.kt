package lib.ui.android

import lib.ui.SearchPageObject
import org.openqa.selenium.remote.RemoteWebDriver

class AndroidSearchPageObject(driver: RemoteWebDriver): SearchPageObject(driver) {

    override val SEARCH_INIT_ELEMENT = "id:org.wikipedia:id/search_container"
    override val SEARCH_INPUT = "id:org.wikipedia:id/search_src_text"
    override val SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/search_results_container']//*[@text='{SUBSTRING}']"
    override val SEARCH_RESULT_CONTAINER = "id:org.wikipedia:id/search_results_container"
    override val SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn"
    override val SEARCH_RESULT_LIST = "id:org.wikipedia:id/search_results_list"
    override val SEARCH_EMPTY_RESULT_ELEMENT = "org.wikipedia:id/search_empty_view"
}