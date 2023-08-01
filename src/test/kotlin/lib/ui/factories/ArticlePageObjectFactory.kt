package lib.ui.factories


import io.appium.java_client.AppiumDriver
import lib.Platform
import lib.ui.ArticlePageObject
import lib.ui.android.AndroidArticlePageObject
import lib.ui.ios.IOSArticlePageObject
import org.openqa.selenium.WebElement

class ArticlePageObjectFactory {

    companion object {
        fun get(driver: AppiumDriver<WebElement>): ArticlePageObject {
            if (Platform.getInstance().isAndroid()) return AndroidArticlePageObject(driver)
            else return IOSArticlePageObject(driver)
        }
    }
}