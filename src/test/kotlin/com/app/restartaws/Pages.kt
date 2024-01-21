package com.app.restartaws

import com.codeborne.selenide.Selectors.byXpath
import com.codeborne.selenide.Selenide.element

// url = https://awsacademy.instructure.com/login/canvas
class LoginPage {
    val usernameInput = element(byXpath("//*[@id='pseudonym_session_unique_id']"))
    val passwordInput = element(byXpath("//*[@id='pseudonym_session_password']"))
    val loginButton = element(byXpath("//input[@type='submit']"))

}

// url = https://awsacademy.instructure.com
class DashboardPage {
    val spanDashboard = element(byXpath("//span[@class='hidden-phone']"))
}

// url = https://awsacademy.instructure.com/courses/${courseId}/modules
class ModulesPage {
    val launchAWSAcademyLearnerLab = element(byXpath("//a[@title='Launch AWS Academy Learner Lab']"))
}

// url = https://awsacademy.instructure.com/courses/${courseId}/modules/items/${ItemId}
class ContentPage {
    val iframe = element(byXpath("//*[@title='Launch AWS Academy Learner Lab']"))
}

// iframe inside ContentPage
class IFramePage {
    val launchLabButton = element(byXpath("//*[@id='launchclabsbtn']"))
    val vmStatus = element(byXpath("//*[@id='vmstatus']"))
    val sessionTimer = element(byXpath("//*[@id='sessiontimer']"))
}
