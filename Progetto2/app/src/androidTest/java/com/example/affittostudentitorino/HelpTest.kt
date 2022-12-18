package com.example.affittostudentitorino

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HelpTest {

    @get:Rule
    var activityRule: ActivityTestRule<Help> = ActivityTestRule(Help::class.java)

    @Test
    fun  verificaCampiInizialiVuoti() {
        Espresso.onView(ViewMatchers.withId(R.id.soggetto))
            .check(ViewAssertions.matches(ViewMatchers.withText("")))
        Espresso.onView(ViewMatchers.withId(R.id.messaggio))
            .check(ViewAssertions.matches(ViewMatchers.withText("")))
    }

    @Test
    fun email(){
        Espresso.onView(ViewMatchers.withId(R.id.soggetto))
            .perform(ViewActions.typeText("test funzione email"))
        Espresso.onView(ViewMatchers.withId(R.id.messaggio))
            .perform(ViewActions.typeText("funziona"),closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.manda))
            .perform(ViewActions.click())
    }
}