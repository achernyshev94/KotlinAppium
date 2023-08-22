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
import org.junit.Assert
import org.junit.Test
import io.qameta.allure.*
import io.qameta.allure.junit4.DisplayName

class ArticleTests: CoreTestCase()  {

    @Test
    @Features(Feature("Search"), Feature("Article"))
    @DisplayName("Compare article title with expected one")
    @Description("Мы открываем статью 'Java' " +
            "и проверяем, что открыта именно она")
    @Step("Starting testCheckArticleTitle")
    @Severity(SeverityLevel.BLOCKER)
    fun testCheckArticleTitle() {
        val SearchPageObject: SearchPageObject = SearchPageObjectFactory.get(driver)
        val ArticlePageObject: ArticlePageObject = ArticlePageObjectFactory.get(driver)

        SearchPageObject.initSearchInput()
        SearchPageObject.typeSearchLine("Java")
        SearchPageObject.clickAtSearchResult()
        val article_title = ArticlePageObject.getArticleTitle()
        Assert.assertEquals(
            "Unexpected title",
            "Java",
            article_title
        )
    }

    @Test
    @Features(Feature("Search"), Feature("Article"), Feature("Swipe"))
    @DisplayName("Search, add and swipe article")
    @Description("Поиск и добавление двух статей с последующим удалением одной из них")
    @Step("testSaveTwoArticlesIntoFolder")
    @Severity(SeverityLevel.BLOCKER)
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
            Assert.assertEquals("We are not on the same page after login", searchFirst, ArticlePageObject.getArticleTitle())
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