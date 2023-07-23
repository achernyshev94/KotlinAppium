package lib

import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import junit.framework.TestCase
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL

open class CoreTestCase: TestCase() {

    private val PLATFORM_IOS = "iOS"
    private val PLATFORM_ANDROID = "Android"
    private val appiumURL = "http://127.0.0.1:4723/wd/hub"

    lateinit var driver: AppiumDriver<WebElement>

    override fun setUp() {
        super.setUp()

        val capabilities: DesiredCapabilities = getCapabilitiesByPlatformName()
        driver = AndroidDriver(URL(appiumURL), capabilities)
    }

    fun getCapabilitiesByPlatformName(): DesiredCapabilities {
        val platform = System.getenv("PLATFORM")
        val capabilities = DesiredCapabilities()

        if (platform.equals(PLATFORM_ANDROID)) {
            capabilities.setCapability("platformName", "Android")
            capabilities.setCapability("deviceName", "Nexus5")
            capabilities.setCapability("platformVersion", "11.0")
            capabilities.setCapability("automationName", "Appium")
            capabilities.setCapability("appPackage", "org.wikipedia")
            capabilities.setCapability("appActivity", ".main.MainActivity")
            capabilities.setCapability(
                "app",
                "/Users/alexanderchernyshev/IdeaProjects/AppiumKotlinAutomation/src/test/resources/apks/org.wikipedia.apk"
            )
        } else if (platform.equals(PLATFORM_IOS)) {
            capabilities.setCapability("platformName", "iOS")
            capabilities.setCapability("deviceName", "iPhone 11")
            capabilities.setCapability("platformVersion", "14.0")
            capabilities.setCapability(
                "app",
                "/Users/alexanderchernyshev/IdeaProjects/AppiumKotlinAutomation/src/test/resources/apks/wiki.app"
            )
        } else {
            throw Exception("Cannot get run platform from env variable. Platform value $platform")
        }
        return capabilities
    }

    override fun tearDown() {
        driver.quit()
        super.tearDown()
    }
}