package lib

import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.remote.MobileCapabilityType
import junit.framework.TestCase
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL

open class CoreTestCase: TestCase() {

    lateinit var driver: AppiumDriver<WebElement>

    override fun setUp() {
        super.setUp()

        val capabilities = DesiredCapabilities()
        val serverAddress = URL("http://127.0.0.1:4723/wd/hub")

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android")
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium")
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9")
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "and80")
        capabilities.setCapability(
            MobileCapabilityType.APP,
            "/Users/alexanderchernyshev/IdeaProjects/AppiumKotlinAutomation/src/test/resources/apks/org.wikipedia.apk")

        driver = AndroidDriver(serverAddress, capabilities)
    }

    override fun tearDown() {
        driver.quit()
        super.tearDown()
    }
}