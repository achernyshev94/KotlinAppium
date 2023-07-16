package lib.ui

import io.appium.java_client.AppiumDriver
import io.appium.java_client.TouchAction
import junit.framework.TestCase
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

open class MainPageObject(
    private val driver: AppiumDriver<WebElement>
) {

    fun waitForElementPresent(by: By, errorMessage: String, timeout: Long): WebElement {
        val wait = WebDriverWait(driver, timeout)
        wait.withMessage(errorMessage + "\n")
        return wait.until(ExpectedConditions.presenceOfElementLocated(by))
    }

    fun elementPresent(by: By, errorMessage: String): WebElement {
        val wait = WebDriverWait(driver, 0)
        wait.withMessage(errorMessage + "\n")
        return wait.until(ExpectedConditions.presenceOfElementLocated(by))
    }

    fun waitForElementAndClick(by: By, errorMessage: String, timeout: Long): WebElement {
        val element: WebElement = waitForElementPresent(by, errorMessage, timeout)
        element.click()
        return element
    }

    fun assertElementHasText(by: By, text: String, errorMessage: String, timeout: Long) {
        val element: WebElement = waitForElementPresent(by, errorMessage, timeout)
        val textElement = element.text
        TestCase.assertEquals(
            "We see unexpected text",
            text,
            textElement
        )
    }

    fun waitForElementAndSendKeys(by: By, text: String, errorMessage: String, timeout: Long): WebElement {
        val element: WebElement = waitForElementPresent(by, errorMessage, timeout)
        element.sendKeys(text)
        return element
    }

    fun waitForElementNotPresent(by: By, errorMessage: String, timeout: Long): Boolean {
        val wait = WebDriverWait(driver, timeout)
        wait.withMessage(errorMessage + "\n")
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by))
    }

    fun waitForElementAndClear(by: By, errorMessage: String, timeout: Long): WebElement {
        val element: WebElement = waitForElementPresent(by, errorMessage, timeout)
        element.clear()
        return element
    }

    fun waitForElementAndCheckContainsText(by: By, text: Regex, errorMessage: String, timeout: Long) {
        val element: WebElement = waitForElementPresent(by, errorMessage, timeout)
        val textElement = element.text
        TestCase.assertTrue("'${textElement}' not contains '${text}'", textElement.contains(text))
    }

    fun swipeUp(timeOfSwipe: Int) {
        val action: TouchAction = TouchAction(driver)
        val size: Dimension = driver.manage().window().size
        val x = size.width / 2
        val startY  = (size.height * 0.8).toInt()
        val endY  = (size.height * 0.2).toInt()

        action
            .press(x, startY)
            .waitAction(timeOfSwipe)
            .moveTo(x, endY)
            .release()
            .perform()
    }

    fun swipeUpQuick() {
        swipeUp(200)
    }

    fun swipeUpToWaitElement(by: By, errorMessage: String, maxSwipes: Int, ) {
        var doneSwipes = 0

        while (driver.findElements(by).size == 0) {
            if (doneSwipes > maxSwipes) {
                waitForElementPresent(by, "Cannot find element by swiping up. \n" + errorMessage, 0)
                return
            }

            swipeUpQuick()
            ++doneSwipes
        }
    }

    fun swipeElementToLeft(by: By, errorMessage: String) {
        val element: WebElement = waitForElementPresent(by, errorMessage, 10)
        val leftX = element.location.getX()
        val rightX = leftX + element.size.width
        val upperY = element.location.getY()
        val lowerY = upperY + element.size.height
        val middleY = (upperY + lowerY) / 2

        val action: TouchAction = TouchAction(driver)

        action
            .press(rightX, middleY)
            .waitAction(300)
            .moveTo(leftX, middleY)
            .release()
            .perform()
    }

    fun getAmountOfElements(by: By): Int {
        val elements = driver.findElements(by)
        return elements.size
    }

    fun assertElementNotPresent(by: By, errorMessage: String) {
        val amountOfElements = getAmountOfElements(by)

        if (amountOfElements > 0) {
            val defaultMessage = "An element '${by.toString()}' supposed to be not present"
            throw AssertionError(defaultMessage + "" + errorMessage)
        }
    }

    fun assertElementPresent(by: By, errorMessage: String): WebElement {
        return elementPresent(by, errorMessage)
    }
}