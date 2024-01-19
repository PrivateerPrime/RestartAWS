package com.app.restartaws

import com.codeborne.selenide.Condition.*
import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide.open
import com.codeborne.selenide.Selenide.webdriver
import com.codeborne.selenide.logevents.SelenideLogger
import io.qameta.allure.selenide.AllureSelenide
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import java.util.logging.Logger

class RestartTest {
    private val driver = webdriver().driver()
    private val logger = Logger.getLogger("")

    private val loginPage = LoginPage()
    private val dashboardPage = DashboardPage()
    private val modulesPage = ModulesPage()
    private val contentPage = ContentPage()
    private val iFramePage = IFramePage()

    private val courseId: Int = System.getenv("courseId").toInt()

    companion object {
        @JvmStatic
        @BeforeAll
        fun setUpAll() {
            Configuration.browserSize = "1920x1080"
            SelenideLogger.addListener("allure", AllureSelenide())
        }
    }

    @Test
    fun restart() {
        Configuration.browserCapabilities = ChromeOptions().addArguments("--remote-allow-origins=*", "--headless=new")
        open("https://awsacademy.instructure.com/login/canvas")

        driver.webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60))
        loginPage.usernameInput.sendKeys(System.getenv("login"))
        loginPage.passwordInput.sendKeys(System.getenv("password"))
        loginPage.loginButton.click()

        dashboardPage.spanDashboard.shouldBe(visible)
        logger.info("Login successful")
        assertEquals("https://awsacademy.instructure.com/?login_success=1", driver.currentFrameUrl)

        driver.webDriver.navigate().to("https://awsacademy.instructure.com/courses/${courseId}/modules")
        assertEquals("https://awsacademy.instructure.com/courses/${courseId}/modules", driver.currentFrameUrl)

        modulesPage.launchAWSAcademyLearnerLab.click()
        logger.info("AWS lab tab opened")
        contentPage.iframe.shouldBe(visible)
        driver.switchTo().frame(contentPage.iframe)

        iFramePage.launchLabButton.shouldBe(visible)
        iFramePage.launchLabButton.click()

        logger.info("AWS lab starting")

        val wait = WebDriverWait(driver.webDriver, Duration.ofSeconds(60))
        wait.until {
            iFramePage.vmStatus.getAttribute("class")?.contains("led-green")
        }

        iFramePage.vmStatus.shouldHave(cssClass("led-green"))
        iFramePage.sessionTimer.shouldHave(text("04:00"))
        logger.info("AWS lab started")
    }
}
