package com.appham.gimmerecipes

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions.pressImeActionButton
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.appham.gimmerecipes.utils.RecyclerViewItemCountAssertion
import com.appham.gimmerecipes.utils.ViewIdlingResource
import com.appham.gimmerecipes.view.MainActivity
import org.hamcrest.CoreMatchers.not

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test for the recipes list view, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RecipesListTest {

    @get:Rule
    var mActivityRule = ActivityTestRule<MainActivity>(
            MainActivity::class.java)

    @Before
    fun setup() {
        val progressBar = ViewIdlingResource(mActivityRule.getActivity().findViewById(R.id.progressBarList))
        IdlingRegistry.getInstance().register(progressBar)
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.appham.gimmerecipes", appContext.packageName)
    }

    @Test
    fun testEditQueryFieldDisplayed() {
        onView(withId(R.id.editQuery)).check(matches(isDisplayed()))
    }

    @Test
    fun testLoadingBarNotDisplayed() {
        onView(withId(R.id.progressBarList)).check(matches(not(isDisplayed())))
    }

    @Test
    fun testSubmitEmptySearchShowsAllRecipes() {
        onView(withId(R.id.editQuery)).perform(typeText(""))
        onView(withId(R.id.editQuery)).perform(pressImeActionButton());
        onView(withId(R.id.listRecipes)).check(RecyclerViewItemCountAssertion(30))
    }

    @Test
    fun testSubmitGimmeRecipesSearchShowsAllRecipes() {
        onView(withId(R.id.editQuery)).perform(typeText("Gimme Recipes"))
        onView(withId(R.id.editQuery)).perform(pressImeActionButton());
        onView(withId(R.id.listRecipes)).check(RecyclerViewItemCountAssertion(30))
    }
}
