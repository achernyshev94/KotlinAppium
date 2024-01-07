package lib

import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import lib.ui.WelcomePageObject
import org.junit.Before
import io.qameta.allure.Step
import org.junit.After
import org.openqa.selenium.ScreenOrientation
import org.openqa.selenium.remote.RemoteWebDriver
import java.io.FileOutputStream
import java.util.Properties
import java.util.concurrent.TimeUnit

open class CoreTestCase {

    lateinit var driver: RemoteWebDriver

    @Before
    @Step("Run driver and session")
    override fun setUp() {
        super.setUp()

        driver = Platform.getInstance().getDriver()
        this.createAllurePropertyFile()
        this.rotateScreenPortrait()
        this.skipWelcomePageForIOSApp()

        if (driver is AppiumDriver<*>) {
            (driver as AndroidDriver<*>).manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS)
            val width = driver?.manage()?.window()?.size?.getWidth() ?: 0
            val height = driver?.manage()?.window()?.size?.getHeight() ?: 0
            println("width $width, height $height")
            if (width > height) this.rotateScreenPortrait()
        }
        this.openWikiWebPageForMobileWeb()
    }
    @Step("Rotate screen to portrait mode")
    protected fun rotateScreenPortrait() {
        if (driver is AppiumDriver<*>) {
            (driver as AppiumDriver<*>).rotate(ScreenOrientation.PORTRAIT)
        } else {
            println(
                "Method rotateScreenPortrait() does nothing for platform ${Platform.getInstance().getPlatformName()}"
            )
        }
    }

    @Step("Rotate screen to landscape mode")
    protected fun rotateScreenLandscape() {
        if (driver is AppiumDriver<*>) {
            (driver as AppiumDriver<*>).rotate(ScreenOrientation.LANDSCAPE)
        } else {
            println(
                "Method rotateScreenLandscape() does nothing for platform ${Platform.getInstance().getPlatformName()}"
            )
        }
    }


    private fun skipWelcomePageForIOSApp() {
        if (Platform.getInstance().isIOS()) {
            val WelcomePageObject: WelcomePageObject = WelcomePageObject(driver)
            WelcomePageObject.clickSkip()
        }

        @After
        @Step("Remove driver and session")
        fun tearDown() {
            driver.quit()
            super.tearDown()
        }
    }

    @Step("Открыть главную страницу сайта Википедия (только MobileWeb)")
    private fun openWikiWebPageForMobileWeb() {
        if (Platform.getInstance().isMW()) {
            driver?.manage()?.timeouts()?.implicitlyWait(10, TimeUnit.SECONDS);
            driver?.get("https://en.m.wikipedia.org")
        } else {
            println(
                "Method openWikiWebPageForMobileWeb() does nothing for platform ${Platform.getInstance().getPlatformName()}"
            )
        }

    }

    private fun createAllurePropertyFile() {
        val path = System.getProperty("allure.results.directory")
        try {
            val props = Properties()
            val fos = FileOutputStream("$path/environment.properties")
            println(Platform.getInstance().getPlatformName())
            props.setProperty("Environment", Platform.getInstance().getPlatformName())
            props.store(fos, "See Wiki Allure")
            println(props.toString())
            fos.close()
        } catch (e: Exception) {
            System.err.println("IO problem when writing allure properties file")
            e.printStackTrace()

        }
    }
}