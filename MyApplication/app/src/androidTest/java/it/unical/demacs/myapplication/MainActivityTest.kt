package it.unical.demacs.myapplication

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test

class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun onLoginButtonPressed() {

        Espresso.onView(withId(R.id.userNameErrorView))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        Espresso.onView(withId(R.id.passwordErrorView))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

        Espresso.onView(ViewMatchers.withId(R.id.userNameLabel))
            .check(ViewAssertions.matches(ViewMatchers.withText(R.string.username)))

        Espresso.onView(ViewMatchers.withId(R.id.passwordLabel))
            .check(ViewAssertions.matches(ViewMatchers.withText((R.string.password))))

        Espresso.onView(ViewMatchers.withId(R.id.loginButton))
            .check(ViewAssertions.matches(ViewMatchers.withText(R.string.entra)))

        Espresso.onView(ViewMatchers.withId(R.id.userNameTextView))
            .perform(ViewActions.typeText("Fabio"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.passwordTextView))
            .perform(ViewActions.typeText("f"), ViewActions.closeSoftKeyboard())

        Espresso.onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.wrongCredential))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

        Espresso.onView(withId(R.id.wrongCredential))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

    }
}