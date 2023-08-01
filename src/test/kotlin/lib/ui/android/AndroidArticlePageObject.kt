package lib.ui.android


import io.appium.java_client.AppiumDriver
import lib.ui.ArticlePageObject
import org.openqa.selenium.WebElement

class AndroidArticlePageObject(driver: AppiumDriver<WebElement>): ArticlePageObject(driver) {

    override val TITLE = "id:org.wikipedia:id/view_page_title_text"
    override val FOOTER_ELEMENT = "xpath://*[@text='View page in browser']"
    override var OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']"
    override val OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[@text='Add to reading list']"
    override var ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button"
    override var MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input"
    override var MY_LIST_OK_BUTTON = "xpath://*[@resource-id='org.wikipedia:id/buttonPanel']//*[@text='OK']"
    override val CLOSE_ARTICLE_BUTTON =
        "xpath://*[@resource-id='org.wikipedia:id/page_toolbar']//*[@content-desc='Navigate up']"
    override var MY_READING_LIST_TPL = "xpath://*[@text='{MY_READING_LIST_NAME}']"
}