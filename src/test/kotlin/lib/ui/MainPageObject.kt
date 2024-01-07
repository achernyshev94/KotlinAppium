package lib.ui

import io.appium.java_client.AppiumDriver
import io.appium.java_client.TouchAction
import io.appium.java_client.touch.WaitOptions
import io.appium.java_client.touch.offset.PointOption
import io.qameta.allure.Attachment
import lib.Platform
import org.apache.commons.io.FileUtils
import org.junit.Assert
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.time.Duration

open class MainPageObject(
    private val driver: RemoteWebDriver
) {

    fun waitForElementPresent(locator: String, errorMessage: String, timeout: Long): WebElement {
        val by = this.getLocatorByString(locator)
        val wait = WebDriverWait(driver, timeout)
        wait.withMessage(errorMessage + "\n")
        return wait.until(ExpectedConditions.presenceOfElementLocated(by))
    }

    fun elementPresent(locator: String, errorMessage: String): WebElement {
        val by = this.getLocatorByString(locator)
        val wait = WebDriverWait(driver, 0)
        wait.withMessage(errorMessage + "\n")
        return wait.until(ExpectedConditions.presenceOfElementLocated(by))
    }

    fun waitForElementAndClick(locator: String, errorMessage: String, timeout: Long): WebElement {
        val element: WebElement = waitForElementPresent(locator, errorMessage, timeout)
        element.click()
        return element
    }

    fun assertElementHasText(locator: String, text: String, errorMessage: String, timeout: Long) {
        val element: WebElement = waitForElementPresent(locator, errorMessage, timeout)
        val textElement = element.text
        Assert.assertEquals(
            "We see unexpected text",
            text,
            textElement
        )
    }

    fun waitForElementAndSendKeys(locator: String, text: String, errorMessage: String, timeout: Long): WebElement {
        val element: WebElement = waitForElementPresent(locator, errorMessage, timeout)
        element.sendKeys(text)
        return element
    }

    fun waitForElementNotPresent(locator: String, errorMessage: String, timeout: Long): Boolean {
        val by = this.getLocatorByString(locator)
        val wait = WebDriverWait(driver, timeout)
        wait.withMessage(errorMessage + "\n")
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by))
    }

    fun waitForElementAndClear(locator: String, errorMessage: String, timeout: Long): WebElement {
        val element: WebElement = waitForElementPresent(locator, errorMessage, timeout)
        element.clear()
        return element
    }

    fun waitForElementAndCheckContainsText(locator: String, text: Regex, errorMessage: String, timeout: Long) {
        val element: WebElement = waitForElementPresent(locator, errorMessage, timeout)
        val textElement = element.text
        Assert.assertTrue("'${textElement}' not contains '${text}'", textElement.contains(text))
    }

    private fun swipeUp(timeOfSwipe: Long) {
        if (driver is AppiumDriver<*>) {
            val action =  TouchAction(driver)
            val size = driver.manage()?.window()?.size
            val x: Int? = size?.width?.div(2)
            val startY = size?.height?.times(0.8)?.toInt()
            val endY = size?.height?.times(0.2)?.toInt()

            action
                .press(PointOption.point(x!!, startY!!))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
                .moveTo(PointOption.point(x, endY!!)).release()
                .perform()
        } else {
            println("Method swipeUp(() does nothing for platform ${Platform.getInstance().getPlatformName()}")
        }

    }

    fun isElementLocationOnScreen(locator: String): Boolean {
        val elementLocationByY =
            this.waitForElementPresent(locator, "Cannot find element by locator", 5).location.getY()
        val screenSizeByY = driver.manage().window().size.height
        return elementLocationByY < screenSizeByY
    }

    fun swipeUpTillElementAppear(locator: String, errorMessage: String, maxSwipes: Int) {
        var alreadySwipe = 0
        while (!isElementLocationOnScreen(locator)) {
            if (alreadySwipe > maxSwipes) {
                Assert.assertTrue(errorMessage, this.isElementLocationOnScreen(locator))
            }
            swipeUp(1)
            alreadySwipe++
        }
    }

    fun swipeUpToWaitElement(locator: String, errorMessage: String, maxSwipes: Int, ) {
        val by = this.getLocatorByString(locator)
        var doneSwipes = 0

        while (driver.findElements(by).size == 0) {
            if (doneSwipes > maxSwipes) {
                waitForElementPresent(locator, "Cannot find element by swiping up. \n" + errorMessage, 0)
                return
            }

            swipeUp(1)
            ++doneSwipes
        }
    }

    fun swipeElementToLeft(locator: String, errorMessage: String) {
        if (driver is AppiumDriver<*>) {
            val element = waitForElementPresent(locator, errorMessage, 10)
            val leftX = element?.location?.getX()
            println(leftX)
            val rightX = leftX!! + element.size.getWidth()
            println(rightX)
            val upperY = element.location.getY()
            println(upperY)
            val lowerY = upperY + element.size.getHeight()
            println(lowerY)
            val middleY = (upperY + lowerY) / 2
            println(middleY)
            val action = TouchAction(driver)
            action
                .press(PointOption.point(rightX, middleY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(150)))
                .moveTo(PointOption.point(leftX, middleY))
                .release()
                .perform()
        } else {
            println("Method swipeElementToLeft() does nothing for platform ${Platform.getInstance().getPlatformName()}")
        }

    }

    fun scrollWebPageUp() {
        if(Platform.getInstance().isMW()) {
            val jSExecutor: JavascriptExecutor = driver as JavascriptExecutor
            jSExecutor.executeScript("window.scrollBy(0,250)")
        } else {
            println(
                "Method scrollWebPageUp() does nothing for platform ${Platform.getInstance().getPlatformName()}")
        }
    }

    fun scrollWebPageTillElementNotVisible(locator: String, errorMessage: String, maxSwipes: Int) {
        var alreadySwipe = 0
        val element = this.waitForElementPresent(locator, errorMessage, timeout = 10)

        while (!this.isElementLocatedOnTheScreen(locator)) {
            scrollWebPageUp()
            alreadySwipe++

            if(alreadySwipe > maxSwipes) {
                Assert.assertTrue(errorMessage, element!!.isDisplayed)
            }
        }
    }

    private fun isElementLocatedOnTheScreen(locator: String): Boolean {
        var elementLocationByY = this.waitForElementPresent(locator, "Cannot find element", 1)?.location?.getY()
        if (Platform.getInstance().isMW()) {
            val jSExecutor: JavascriptExecutor = driver as JavascriptExecutor
            val jsResult = jSExecutor.executeScript("return window.pageYOffset")
            elementLocationByY = elementLocationByY?.minus(jsResult.toString().toInt())
        }
        val screenSizeByY = driver?.manage()?.window()?.size?.height
        return elementLocationByY ?: 0 < screenSizeByY ?: 0
    }

    fun getAmountOfElements(locator: String): Int {
        val by = this.getLocatorByString(locator)
        val elements = driver.findElements(by)
        return elements.size
    }

    fun assertElementNotPresent(locator: String, errorMessage: String) {
        val amountOfElements = getAmountOfElements(locator)

        if (amountOfElements > 0) {
            val defaultMessage = "An element '${locator}' supposed to be not present"
            throw AssertionError(defaultMessage + "" + errorMessage)
        }
    }

    fun assertElementPresent(locator: String, errorMessage: String): WebElement {
        return elementPresent(locator, errorMessage)
    }

    fun getLocatorByString(locator_with_type: String): By {
        val exploded_locator = locator_with_type.split(":", limit = 2)
        val by_type = exploded_locator[0]
        val locator = exploded_locator[1]

        if (by_type.equals("xpath")) {
            return By.xpath(locator)
        } else if (by_type.equals("id")) {
            return By.id(locator)
        } else {
            throw java.lang.IllegalArgumentException("Cannot get type of locator. Locator $locator_with_type")
        }
    }

    fun isElementPresent(locator: String) = this.getAmountOfElements(locator)?:0 > 0

    fun tryClickElementWithFewAttempts(locator: String, errorMessage: String, amountOfAttempts: Int) {
        var currentAttempts = 0
        var needMoreAttempts = true

        while(needMoreAttempts) {
            try {
                this.waitForElementAndClick(locator, errorMessage, 2)
                needMoreAttempts = false
            } catch (e: Exception) {
                if(currentAttempts > amountOfAttempts) {
                    this.waitForElementAndClick(locator, errorMessage, 2)
                }
            }
            ++currentAttempts
        }
    }

    fun getURL(): String? = driver?.currentUrl

    fun takeScreenShot(name: String): String {
        val ts: TakesScreenshot = (this.driver as TakesScreenshot)
        val source = ts.getScreenshotAs(OutputType.FILE)
        val path = System.getProperty("user.dir") + "/" + name + "_screenshot.png"
        try {
            FileUtils.copyFile(source, File(path))
            println("The screenshot was taken: $path")
        } catch (e: Exception) {
            println("Cannot take screenshot. Error: ${e.message}")
        }
        return path
    }

    @Attachment
    fun screenshot(path: String): ByteArray {
        var bytes =  byteArrayOf()
        try {
            bytes = Files.readAllBytes(Paths.get(path))
        } catch (e: IOException) {
            println("Cannot get bytes from screenshot. Error: ${e.message}")
        }
        return bytes
    }
}