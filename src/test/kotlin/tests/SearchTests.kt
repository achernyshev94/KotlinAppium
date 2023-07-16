package tests

import lib.CoreTestCase
import lib.ui.SearchPageObject
import org.junit.Test

class SearchTests : CoreTestCase() {

    @Test
    fun testCancelSearch() {
        val SearchPageObject: SearchPageObject = SearchPageObject(driver)

        SearchPageObject.initSearchInput()
        SearchPageObject.typeSearchLine("Appium")
        SearchPageObject.waitForCancelButtonToAppear()
        SearchPageObject.clickButtonCancelSearch()
        SearchPageObject.waitForSearchResultEmptyMessage()
    }

}