package tests

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.remote.MobileCapabilityType
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL

class FirstTest {

    private var driver: AppiumDriver<MobileElement>? = null

    @Before
    fun setUp() {
        val capabilities = DesiredCapabilities()
        val serverAddress = URL("http://127.0.0.1:4723/wd/hub")

        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android")
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium")
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8")
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "and80")
        capabilities.setCapability(MobileCapabilityType.APP,
            "/Users/alexanderchernyshev/IdeaProjects/KotlinAppium/src/test/resources/apks/org.wikipedia.apk")

        driver = AndroidDriver(serverAddress, capabilities)
    }

    @After
    fun tearDown() {
        driver?.quit()
    }

    @Test
    fun firstTest() {
        println("First test run")
    }
}