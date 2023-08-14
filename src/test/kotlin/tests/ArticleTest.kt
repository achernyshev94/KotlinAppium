package tests

import lib.CoreTestCase
import lib.Platform
import lib.ui.ArticlePageObject
import lib.ui.AuthorizationPageObject
import lib.ui.NavigationPageObject
import lib.ui.SavedListsPageObject
import lib.ui.SearchPageObject
import lib.ui.factories.ArticlePageObjectFactory
import lib.ui.factories.NavigationPageObjectFactory
import lib.ui.factories.SavedListsPageObjectFactory
import lib.ui.factories.SearchPageObjectFactory
import org.junit.Test

class ArticleTests: CoreTestCase()  {

    @Test
    fun testCheckArticleTitle() {
        val SearchPageObject: SearchPageObject = SearchPageObjectFactory.get(driver)
        val ArticlePageObject: ArticlePageObject = ArticlePageObjectFactory.get(driver)

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
        val SearchPageObject: SearchPageObject = SearchPageObjectFactory.get(driver)
        val ArticlePageObject: ArticlePageObject = ArticlePageObjectFactory.get(driver)
        val NavigationPageObject: NavigationPageObject = NavigationPageObjectFactory.get(driver)
        val SavedListsPageObject: SavedListsPageObject = SavedListsPageObjectFactory.get(driver)

        val searchFirst = "Java"
        val searchSecond = "JavaScript"
        val login= "adcher94"
        val password = "adcher2712"

        // Поиск и сохранение первой статьи
        SearchPageObject.initSearchInput()
        SearchPageObject.typeSearchLine(searchFirst)
        SearchPageObject.clickAtSearchResult()
        if(Platform.getInstance().isMW()) {
            val auth = AuthorizationPageObject(driver)
            auth.clickButton()
            auth.enterLoginData(login, password)
            auth.submitForm()
            ArticlePageObject.waitForTitleElement()
            assertEquals("We are not on the same page after login", searchFirst, ArticlePageObject.getArticleTitle())
            ArticlePageObject.clickButtonSave()
        } else ArticlePageObject.clickButtonSave()

        // Поиск и сохранение второй статьи
        SearchPageObject.initSearchInput()
        if (Platform.getInstance().isIOS()) {
            SearchPageObject.clickCancelSearch()
        }
        SearchPageObject.typeSearchLine(searchSecond)
        SearchPageObject.clickAtSearchResult()
        ArticlePageObject.clickButtonSave()

        //Удаление одной из статей
        NavigationPageObject.clickButtonBackArticle()
        NavigationPageObject.clickButtonBackArticle()
        NavigationPageObject.clickButtonBackMain()
        NavigationPageObject.clickButtonSaved()
        if (Platform.getInstance().isAndroid()) {
            SavedListsPageObject.clickOnSavedFolder()
            // Закрываем модальное окно на iOS
        } else {
            SavedListsPageObject.clickButtonCloseSyncMyArticlesWindow()
        }
        if (Platform.getInstance().isAndroid()) {
            SavedListsPageObject.swipeArticle(searchFirst)
        } else {
            SavedListsPageObject.swipeAndDeleteFirstArticleIos()
        }
        if(Platform.getInstance().isIOS()){
            SavedListsPageObject.articleIsPresent(searchSecond)
        }
        if (Platform.getInstance().isAndroid()) {
            SavedListsPageObject.waitForTitleElementFromMyListScreen()
            //Сохраняем название статьи с экрана списка для чтения
            val titleFromListScreen = SavedListsPageObject.getArticleTitleFromMyListScreen()
            //Открываем статью
            SavedListsPageObject.waitForArticleAndClick(searchSecond)
            //Сохраняем название статьи с экрана самой статьи
            ArticlePageObject.waitForTitleElement()
            val titleFromArticleScreen = ArticlePageObject.getArticleTitle()
            //Сравнимаем названия статей
            assertEquals("Title not equals", titleFromListScreen, titleFromArticleScreen)
        } else {
            //Открываем статью
            SavedListsPageObject.waitForArticleAndClick(searchSecond)
            //Проверяем что заданный заголовок
            ArticlePageObject.titleIsPresent(searchSecond)
        }
    }
}