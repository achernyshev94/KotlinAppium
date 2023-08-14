package lib

import io.appium.java_client.MobileElement
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL

open class Platform {

    companion object {
        const val PLATFORM_IOS = "ios"
        const val PLATFORM_ANDROID = "android"
        const val PLATFORM_MOBILE_WEB = "mobile_web"
        const val APPIUM_URL = "http://127.0.0.1:4723/wd/hub"
        var instance: Platform? = null

        @JvmName("getInstance1")
        fun getInstance(): Platform {

            return if (instance == null) {
                instance = Platform()
                instance!!
            } else instance!!
        }
    }

    fun getDriver(): RemoteWebDriver {
        val URL = URL(APPIUM_URL)
        return when {
            this.isAndroid() -> AndroidDriver<MobileElement>(URL, this.getAndroidDesiredCapabilities())
            this.isIOS() -> IOSDriver<MobileElement>(URL, this.getIOSDesiredCapabilities())
            this.isMW() -> ChromeDriver(this.getMwChromeOptions())
            else -> throw Exception("Cannot detect type of the driver. Platform value: ${this.getPlatformName()}")
        }
    }

    fun isAndroid(): Boolean {
        return isPlatform(PLATFORM_ANDROID)
    }

    fun isIOS(): Boolean {
        return isPlatform(PLATFORM_IOS)
    }

    fun isMW() = isPlatform(PLATFORM_MOBILE_WEB)

    private fun getAndroidDesiredCapabilities(): DesiredCapabilities {
        val capabilities = DesiredCapabilities()
        capabilities.setCapability("platformName", "Android")
        capabilities.setCapability("deviceName", "and80")
        capabilities.setCapability("platformVersion", "9.0")
        capabilities.setCapability("automationName", "Appium")
        capabilities.setCapability("appPackage", "org.wikipedia")
        capabilities.setCapability("appActivity", ".main.MainActivity")
        capabilities.setCapability(
            "app",
            "/Users/indriver/Desktop/KotlinAppiumAutomation/KotlinAppiumAutomation/apks/org.wikipedia.apk"
        )
        return capabilities
    }

    private fun getIOSDesiredCapabilities(): DesiredCapabilities {
        val capabilities = DesiredCapabilities()
        capabilities.setCapability("platformName", "iOS")
        capabilities.setCapability("deviceName", "iPhone 11")
        capabilities.setCapability("platformVersion", "14.0")
        capabilities.setCapability(
            "app",
            "/Users/indriver/Desktop/KotlinAppiumAutomation/KotlinAppiumAutomation/apks/Wikipedia.app"
        )
        return capabilities
    }

    private fun isPlatform(my_platform: String): Boolean {
        val platform = this.getPlatformName()
        return my_platform == platform
    }


    private fun getMwChromeOptions(): ChromeOptions {
        val deviceMetrics = HashMap<String, Any>()
        deviceMetrics["width"] = 360
        deviceMetrics["height"] = 640
        deviceMetrics["pixelRatio"] = 3.0

        val mobileEmulation = HashMap<String, Any>()
        mobileEmulation["deviceMetrics"] = deviceMetrics
        mobileEmulation["userAgent"] = "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) " +
                "AppleWebKit/535.19 (KHTML, like Gecko) " +
                "Chrome/18.0.1025.166 Mobile Safari/535.19"

        val chromeOptions = ChromeOptions()
        chromeOptions.addArguments("window-size=340,640")
        return chromeOptions
    }

    fun getPlatformName() = System.getenv("PLATFORM")!!
}