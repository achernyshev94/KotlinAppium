package lib

import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import junit.framework.TestCase
import lib.ui.WelcomePageObject
import org.openqa.selenium.ScreenOrientation
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL

open class CoreTestCase: TestCase() {

    lateinit var driver: AppiumDriver<WebElement>

    override fun setUp() {
        super.setUp()

        driver = Platform.getInstance().getDriver()
        this.rotateScreenPortrait()
        this.skipWelcomePageForIOSApp()
    }

    protected fun rotateScreenPortrait() {
        driver.rotate(ScreenOrientation.PORTRAIT)
    }

    protected fun rotateScreenLandscape() {
        driver.rotate(ScreenOrientation.LANDSCAPE)
    }


    private fun skipWelcomePageForIOSApp() {
        if (Platform.getInstance().isIOS()) {
            val WelcomePageObject: WelcomePageObject = WelcomePageObject(driver)
            WelcomePageObject.clickSkip()
        }

        fun tearDown() {
            driver.quit()
            super.tearDown()
        }
    }
}