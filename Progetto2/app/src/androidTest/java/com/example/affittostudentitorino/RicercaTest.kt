package com.example.affittostudentitorino

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RicercaTest {

    @get:Rule
    var activityRule: ActivityTestRule<Ricerca> = ActivityTestRule(Ricerca::class.java)

    @Test
    fun ricercaPossibile(){
        Espresso.onView(ViewMatchers.withId(R.id.zona3))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tipo2))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withHint("prezzo minimo"))
            .check(ViewAssertions.matches(ViewMatchers.withText("")))
        Espresso.onView(ViewMatchers.withHint("prezzo massimo"))
            .check(ViewAssertions.matches(ViewMatchers.withText("")))
        Espresso.onView(ViewMatchers.withText("RICERCA ANNUNCI"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun erroreRicerca(){
        Espresso.onView(ViewMatchers.withText("RICERCA ANNUNCI"))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.errric))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}