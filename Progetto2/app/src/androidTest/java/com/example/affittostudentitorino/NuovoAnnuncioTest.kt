package com.example.affittostudentitorino

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.EnumSet.allOf
import org.hamcrest.CoreMatchers.instanceOf


@RunWith(AndroidJUnit4::class)
class NuovoAnnuncioTest {

    @get:Rule
    var activityRule: ActivityTestRule<NuovoAnnuncio> = ActivityTestRule(NuovoAnnuncio::class.java)

    @Test
    fun verificaCampiInizialiVuoti(){
        Espresso.onView(ViewMatchers.withHint("indirizzo"))
            .check(ViewAssertions.matches(withText("")))
        Espresso.onView(ViewMatchers.withId(R.id.zona))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withHint("prezzo al mese"))
            .check(ViewAssertions.matches(withText("")))
        Espresso.onView(ViewMatchers.withId(R.id.singola))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.doppia))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tripla))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.bagno))
            .perform(ViewActions.scrollTo())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun erroreAnnuncio(){
        Espresso.onView(withText("inserisci annuncio"))
            .perform(ViewActions.scrollTo())
            .perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.errann))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun aggiungiAnnuncio(){
        Espresso.onView(ViewMatchers.withHint("indirizzo"))
            .perform(ViewActions.typeText("Giuseppe Barbaroux 5"))
        onView(withId(R.id.zona)).perform(click())
            onView(withText("Crocetta")).perform(click())
        onView(ViewMatchers.withHint("prezzo al mese"))
            .perform(ViewActions.typeText("734"),closeSoftKeyboard())
        Espresso.onView(withId(R.id.singola)).perform(click())
            onView(withText("1")).perform(click())
        onView(withId(R.id.doppia)).perform(click())
            onView(withText("2")).perform(click())
        onView(withId(R.id.tripla)).perform(ViewActions.scrollTo()).perform(click())
            onView(withText("3")).perform(click())
        onView(withId(R.id.bagno))
            .perform(ViewActions.scrollTo())
            .perform(click())
            onView(withText("1")).perform(click())
        Espresso.onView(withText("inserisci annuncio"))
            .perform(ViewActions.scrollTo())
            .perform(click())
    }
}