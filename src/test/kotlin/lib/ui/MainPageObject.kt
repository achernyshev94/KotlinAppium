package lib.ui

import io.appium.java_client.AppiumDriver
import io.appium.java_client.TouchAction
import io.appium.java_client.touch.WaitOptions
import io.appium.java_client.touch.offset.PointOption
import junit.framework.TestCase
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

open class MainPageObject(
    private val driver: AppiumDriver<WebElement>
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
        TestCase.assertEquals(
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
        TestCase.assertTrue("'${textElement}' not contains '${text}'", textElement.contains(text))
    }

    fun swipeUp(timeOfSwipe: Long) {
        val action: TouchAction<*> =  TouchAction(driver)
        val size: Dimension = driver.manage().window().size
        val x = size.width / 2
        val startY  = (size.height * 0.8).toInt()
        val endY  = (size.height * 0.2).toInt()

        action
            .press(PointOption.point(x, startY))
            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
            .moveTo(PointOption.point(x, endY))
            .release()
            .perform()
    }

    fun swipeUpQuick() {
        swipeUp(200)
    }

    fun swipeUpToWaitElement(locator: String, errorMessage: String, maxSwipes: Int, ) {
        val by = this.getLocatorByString(locator)
        var doneSwipes = 0

        while (driver.findElements(by).size == 0) {
            if (doneSwipes > maxSwipes) {
                waitForElementPresent(locator, "Cannot find element by swiping up. \n" + errorMessage, 0)
                return
            }

            swipeUpQuick()
            ++doneSwipes
        }
    }

    fun swipeElementToLeft(locator: String, errorMessage: String) {
        val element: WebElement = waitForElementPresent(locator, errorMessage, 10)
        val leftX = element.location.getX()
        val rightX = leftX + element.size.width
        val upperY = element.location.getY()
        val lowerY = upperY + element.size.height
        val middleY = (upperY + lowerY) / 2

        val action: TouchAction<*> = TouchAction(driver)

        action
            .press(PointOption.point(rightX, middleY))
            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)))
            .moveTo(PointOption.point(leftX, middleY))
            .release()
            .perform()
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
}