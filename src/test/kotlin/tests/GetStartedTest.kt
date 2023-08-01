package tests

import lib.CoreTestCase
import lib.Platform
import lib.ui.WelcomePageObject
import org.junit.Test

class GetStartedTest : CoreTestCase() {

    @Test
    fun testPassThroughWelcome() {
        if(Platform.getInstance().isAndroid()) return
        val WelcomePageObject = WelcomePageObject(driver)

        WelcomePageObject.waitForLearnMoreLink()
        WelcomePageObject.clickNextButton()
        WelcomePageObject.waitForNewWayToExploreText()
        WelcomePageObject.clickNextButton()
        WelcomePageObject.waitForAddOrEditPreferredLangText()
        WelcomePageObject.clickNextButton()
        WelcomePageObject.waitForLearnMoreAboutDataCollectedText()
        WelcomePageObject.clickGetStartedButton()
    }
}