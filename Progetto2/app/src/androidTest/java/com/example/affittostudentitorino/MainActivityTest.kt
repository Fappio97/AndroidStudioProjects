package com.example.affittostudentitorino

import android.support.test.espresso.action.ViewActions.closeSoftKeyboard
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import org.junit.Rule
import org.junit.Test
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest{

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)


    @Test
    fun verificaCampiInizialiVuoti(){
        onView(ViewMatchers.withHint("username"))
            .check(ViewAssertions.matches(ViewMatchers.withText("")))
        onView(ViewMatchers.withHint("password"))
            .check(ViewAssertions.matches(ViewMatchers.withText("")))
    }

    @Test
    fun editextRegistrazione(){
        onView(withId(R.id.reg))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun erroreLogin(){
        onView(withId(R.id.Ricerca))
            .perform(click())
        onView(withId(R.id.errlogin))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun login(){
        onView(ViewMatchers.withHint("username"))
            .perform(typeText("loreg95"))
        onView(ViewMatchers.withHint("password"))
            .perform(typeText("prince95"),closeSoftKeyboard())
        onView(withId(R.id.Ricerca))
            .perform(click())
        onView(ViewMatchers.withText("Bentornato loreg95"))
            .check(ViewAssertions.matches(isDisplayed()))
    }

}