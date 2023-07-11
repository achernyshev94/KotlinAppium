package tests

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.TouchAction
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.remote.MobileCapabilityType
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URL

class SaveAndDeleteArticleTest {

    private var driver: AppiumDriver<MobileElement>? = null

    @Before
    fun setUp() {
        val capabilities = DesiredCapabilities()
        val serverAddress = URL("http://127.0.0.1:4723/wd/hub")

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android")
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium")
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8")
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "and80")
        capabilities.setCapability(
            MobileCapabilityType.APP,
            "/Users/alexanderchernyshev/IdeaProjects/AppiumKotlinAutomation/src/test/resources/apks/org.wikipedia.apk")

        driver = AndroidDriver(serverAddress, capabilities)
    }

    @After
    fun tearDown() {
        driver?.quit()
    }

    @Test
    fun saveAndRemoveArticleTest() {
        val firstArticle = "Java"
        val secondArticle = "Appium"

        waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find main search line",
            5
        )
        waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            firstArticle,
            "Cannot find inside search line",
            5
        )
        waitForElementAndClick(
            By.xpath("//*[@class='android.view.ViewGroup'][@index='0']//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
            "Cannot find first result of searching",
            15
        )
        waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Save')]"),
            "Cannot find button Save",
            5
        )
        waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find main search line",
            5
        )
        waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            secondArticle,
            "Cannot find inside search line",
            5
        )
        waitForElementAndClick(
            By.xpath("//*[@class='android.view.ViewGroup'][@index='0']//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
            "Cannot find first result of searching",
            15
        )
        waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Save')]"),
            "Cannot find button Save",
            5
        )
        waitForElementAndClick(
            By.xpath("//*[@class='android.widget.ImageButton'][@content-desc='Navigate up']"),
            "Cannot find button back",
            5
        )
        waitForElementAndClick(
            By.xpath("//*[@class='android.widget.ImageButton'][@content-desc='Navigate up']"),
            "Cannot find button back",
            5
        )
        waitForElementAndClick(
            By.xpath("//*[@class='android.widget.ImageButton'][@index='0']"),
            "Cannot find button back",
            5
        )
        waitForElementAndClick(
            By.xpath("//*[@class='android.widget.FrameLayout'][@content-desc='Saved']"),
            "Cannot find button Saved",
            5
        )
        waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/recycler_view']//*[@resource-id='org.wikipedia:id/item_title']"),
            "Cannot find folder with saved articles",
            5
        )
        swipeElementToLeft(
            By.xpath("//*[contains(@text, '$secondArticle')]"),
            "Cannot find saved article"
        )
        waitForElementNotPresent(
            By.xpath("//*[contains(@text, '$secondArticle')]"),
            "Cannot delete saved article",
            5
        )
        waitForElementAndClick(
            By.xpath("//*[contains(@text, '$firstArticle')]"),
            "Cannot find article '$firstArticle'",
            5
        )
        waitForElementAndCheckContainsText(
            By.id("org.wikipedia:id/view_page_title_text"),
            firstArticle.toRegex(),
            "Cannot find title of article",
            15
        )
    }

    private fun swipeUp(timeOfSwipe: Int) {
        val action: TouchAction = TouchAction(driver)
        val size: Dimension = driver?.manage()?.window()!!.size
        val x = size.width / 2
        val startY  = (size.height * 0.8).toInt()
        val endY  = (size.height * 0.2).toInt()
        action
            .press(x, startY)
            .waitAction(timeOfSwipe)
            .moveTo(x, endY)
            .release()
            .perform()
    }

    private fun swipeUpQuick() {
        swipeUp(200)
    }

    private fun swipeUpToWaitElement(by: By, errorMessage: String, maxSwipes: Int,) {
        var doneSwipes = 0

        while (driver?.findElements(by)?.size == 0) {
            if (doneSwipes > maxSwipes) {
                waitForElementPresent(by, "Cannot find element by swiping up. \n" + errorMessage, 0)
                return
            }
            swipeUpQuick()
            ++doneSwipes
        }
    }

    private fun swipeElementToLeft(by: By, errorMessage: String) {
        val element: WebElement = waitForElementPresent(by, errorMessage, 10)
        val leftX = element.location.getX()
        val rightX = leftX + element.size.width
        val upperY = element.location.getY()
        val lowerY = upperY + element.size.height
        val middleY = (upperY + lowerY) / 2

        val action: TouchAction = TouchAction(driver)

        action
            .press(rightX, middleY)
            .waitAction(300)
            .moveTo(leftX, middleY)
            .release()
            .perform()
    }

    private fun waitForElementAndCheckContainsText(by: By, text: Regex, errorMessage: String, timeout: Long) {
        val element: WebElement = waitForElementPresent(by, errorMessage, timeout)
        val textElement = element.text
        assertTrue("'${textElement}' not contains '${text}'", textElement.contains(text))
    }

    private fun waitForElementAndClick(by: By, errorMessage: String, timeout: Long): WebElement {
        val element: WebElement = waitForElementPresent(by, errorMessage, timeout)
        element.click()
        return element
    }
    private fun waitForElementAndSendKeys(by: By, text: String, errorMessage: String, timeout: Long): WebElement {
        val element: WebElement = waitForElementPresent(by, errorMessage, timeout)
        element.sendKeys(text)
        return element
    }
    private fun waitForElementPresent(by: By, errorMessage: String, timeout: Long): WebElement {
        val wait = WebDriverWait(driver, timeout)
        wait.withMessage(errorMessage + "\n")
        return wait.until(ExpectedConditions.presenceOfElementLocated(by))
    }
    private fun waitForElementNotPresent(by: By, errorMessage: String, timeout: Long): Boolean {
        val wait = WebDriverWait(driver, timeout)
        wait.withMessage(errorMessage + "\n")
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by))
    }
}