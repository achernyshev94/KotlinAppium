package tests

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.remote.MobileCapabilityType
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URL

class TitleTest {

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
    fun checkTitleTest() {
        waitForElementAndClick(
            By.xpath("//*[contains(@text,'Search Wikipedia')]"),
            "Cannot find main search line",
            5
        )
        waitForElementAndSendKeys(
            By.id("org.wikipedia:id/search_src_text"),
            "Java",
            "Cannot find inside search line",
            5
        )
        waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']/android.widget.LinearLayout[1]"),
            "Cannot find and click element",
            5
        )
        assertElementPresent(By.id("org.wikipedia:id/view_page_title_text"), "Title is not present")
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
    private fun assertElementPresent(by: By, errorMessage: String): WebElement {
        return waitForElementPresent(by, errorMessage, 0)
    }
}