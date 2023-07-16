package tests

import lib.CoreTestCase
import lib.ui.ArticlePageObject
import lib.ui.NavigationPageObject
import lib.ui.SavedListsObject
import lib.ui.SearchPageObject
import org.junit.Test

class ArticleTests: CoreTestCase()  {

    @Test
    fun testCheckArticleTitle() {
        val SearchPageObject: SearchPageObject = SearchPageObject(driver)
        val ArticlePageObject: ArticlePageObject = ArticlePageObject(driver)

        SearchPageObject.initSearchInput()
        SearchPageObject.typeSearchLine("Java")
        SearchPageObject.clickAtSearchResult()
        val article_title = ArticlePageObject.getArticleTitle()
        assertEquals(
            "Unexpected title",
            "Java",
            article_title
        )
    }

    @Test
    fun testSaveTwoArticlesIntoFolder() {
        val SearchPageObject: SearchPageObject = SearchPageObject(driver)
        val ArticlePageObject: ArticlePageObject = ArticlePageObject(driver)
        val NavigationPageObject: NavigationPageObject = NavigationPageObject(driver)
        val SavedListsObject: SavedListsObject = SavedListsObject(driver)

        val searchFirst = "Java"
        val searchSecond = "Appium"

        // Поиск и сохранение первой статьи
        SearchPageObject.initSearchInput()
        SearchPageObject.typeSearchLine(searchFirst)
        SearchPageObject.clickAtSearchResult()
        ArticlePageObject.clickButtonSave()

        // Поиск и сохранение второй статьи
        SearchPageObject.initSearchInput()
        SearchPageObject.typeSearchLine(searchSecond)
        SearchPageObject.clickAtSearchResult()
        ArticlePageObject.clickButtonSave()

        //Удаление одной из статей
        NavigationPageObject.clickButtonBackArticle()
        NavigationPageObject.clickButtonBackArticle()
        NavigationPageObject.clickButtonBackMain()
        NavigationPageObject.clickButtonSaved()
        SavedListsObject.clickOnSavedFolder()
        SavedListsObject.swipeArticle(searchSecond)

        //Проверка нахождения второй статьи и ее заголовка
        SavedListsObject.waitForArticleAndClick(searchFirst)
        val article_title = ArticlePageObject.getArticleTitle()
        assertEquals(
            "Unexpected title",
            searchFirst,
            article_title
        )
    }
}