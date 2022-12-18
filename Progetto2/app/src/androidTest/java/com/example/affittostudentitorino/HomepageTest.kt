package com.example.affittostudentitorino

import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.DrawerActions.open
import android.support.test.espresso.contrib.NavigationViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomepageTest {

    @get:Rule
    var activityRule: ActivityTestRule<HomePage> = ActivityTestRule(HomePage::class.java)

    @Test
    fun opzioni(){
        openActionBarOverflowOrOptionsMenu(getInstrumentation().run {
            targetContext
        })
        onView(withText("risoluzione problemi"))
            .perform(ViewActions.click())
    }

    @Test
    fun menu(){
        onView(withId(R.id.drawer_layout)).perform(open())
        onView(withText("Profilo"))
            .perform(ViewActions.click())
        onView(isRoot()).perform(ViewActions.pressBack())

        onView(withId(R.id.drawer_layout)).perform(open())
        onView(withText("Preferiti"))
            .perform(ViewActions.click())
        onView(isRoot()).perform(ViewActions.pressBack())

        onView(withId(R.id.drawer_layout)).perform(open())
        onView(withText("Aggiungi annuncio"))
            .perform(ViewActions.click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(isRoot()).perform(ViewActions.pressBack())

        onView(withId(R.id.drawer_layout)).perform(open())
        onView(withText("Ricerca annunci"))
            .perform(ViewActions.click())
        onView(isRoot()).perform(ViewActions.pressBack())
        //onView(isRoot()).perform(ViewActions.pressBack())

        onView(withId(R.id.drawer_layout)).perform(open())
        onView(withText("Logout"))
            .perform(ViewActions.click())
    }

}