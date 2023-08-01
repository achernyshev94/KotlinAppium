package lib

import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL

open class Platform {

    companion object {
        const val PLATFORM_IOS = "ios"
        const val PLATFORM_ANDROID = "android"
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

    fun getDriver(): AppiumDriver<WebElement> {
        val URL = URL(APPIUM_URL)
        if (this.isAndroid()) return AndroidDriver(URL, this.getAndroidDesiredCapabilities())
        else if (this.isIOS()) return IOSDriver(URL, this.getIOSDesiredCapabilities())
        else throw Exception("Cannot detect type of the Driver. Platform value: " + this.getPlatformVar())
    }

    fun isAndroid(): Boolean {
        return isPlatform(PLATFORM_ANDROID)
    }

    fun isIOS(): Boolean {
        return isPlatform(PLATFORM_IOS)
    }

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
        val platform = this.getPlatformVar()
        return my_platform == platform
    }

    private fun getPlatformVar(): String {
        return System.getenv("PLATFORM")
    }
}