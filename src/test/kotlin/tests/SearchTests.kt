package tests

import lib.CoreTestCase
import lib.ui.SearchPageObject
import lib.ui.factories.SearchPageObjectFactory
import org.junit.Test
import io.qameta.allure.*
import io.qameta.allure.junit4.DisplayName

@Epic("Тесты для работы с поиском")
class SearchTests : CoreTestCase() {

    @Test
    @Features(Feature("Search"))
    @DisplayName("Проверка нажатия кнопки Отмена поиска")
    @Description("Нажимаем кнопку/крестик Отменить поиск")
    @Step("Starting testCancelSearch")
    @Severity(SeverityLevel.MINOR)
    fun testCancelSearch() {
        val SearchPageObject: SearchPageObject = SearchPageObjectFactory.get(driver)

        SearchPageObject.initSearchInput()
        SearchPageObject.typeSearchLine("Appium")
        SearchPageObject.waitForCancelButtonToAppear()
        SearchPageObject.clickButtonCancelSearch()
        SearchPageObject.waitForSearchResultEmptyMessage()
    }

}