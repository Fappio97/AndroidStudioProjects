package com.example.affittostudentitorino

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NuovoAccountTest {

    @get:Rule
    var activityRule: ActivityTestRule<Nuovoaccount> = ActivityTestRule(Nuovoaccount::class.java)

    @Test
    fun  verificaCampiInizialiVuoti() {
        Espresso.onView(withHint("nome"))
            .check(ViewAssertions.matches(withText("")))
        Espresso.onView(withHint("cognome"))
            .check(ViewAssertions.matches(withText("")))
        Espresso.onView(withHint("username"))
            .check(ViewAssertions.matches(withText("")))
        Espresso.onView(withHint("email"))
            .check(ViewAssertions.matches(withText("")))
        Espresso.onView(withHint("password"))
            .check(ViewAssertions.matches(withText("")))
        Espresso.onView(withHint("telefono"))
            .check(ViewAssertions.matches(withText("")))
        Espresso.onView(withId(R.id.studente))
            .check(ViewAssertions.matches(isNotChecked()))
    }

    @Test
    fun verificaMatricolaCorso(){
        Espresso.onView(withHint("matricola universitaria"))
            .check(ViewAssertions.matches(not(isDisplayed())))
        Espresso.onView(withId(R.id.corso))
            .check(ViewAssertions.matches(not(isDisplayed())))
    }

    @Test
    fun bottonePresente(){
        Espresso.onView(withText("registrati"))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun erroreRegistrazione(){
        Espresso.onView(withId(R.id.registrati))
            .perform(ViewActions.click())
        Espresso.onView(withId(R.id.errreg))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun registrazione(){
        Espresso.onView(withHint("nome"))
            .perform(ViewActions.typeText("nome"))
        Espresso.onView(withHint("cognome"))
            .perform(ViewActions.typeText("cognome"))
        Espresso.onView(withHint("username"))
            .perform(ViewActions.typeText("username"))
        Espresso.onView(withHint("email"))
            .perform(ViewActions.typeText("nome.cognome@email.com"))
        Espresso.onView(withHint("password"))
            .perform(ViewActions.typeText("password"))
        Espresso.onView(withHint("telefono"))
            .perform(ViewActions.typeText("1234567890"),closeSoftKeyboard())
        Espresso.onView(withText("registrati"))
            .perform(ViewActions.click())
        Espresso.onView(withText("Benvenuto username"))
            .check(ViewAssertions.matches(isDisplayed()))
    }
}