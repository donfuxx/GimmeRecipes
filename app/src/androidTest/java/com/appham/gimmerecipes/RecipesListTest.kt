package com.appham.gimmerecipes

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.appham.gimmerecipes.utils.RecyclerViewItemCountAssertion
import com.appham.gimmerecipes.utils.ViewActionUtils.atPosition
import com.appham.gimmerecipes.utils.ViewIdlingResource
import com.appham.gimmerecipes.view.MainActivity
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

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
        val progressBar = ViewIdlingResource(mActivityRule.activity.findViewById(R.id.progressBarList))
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
        onView(withId(R.id.editQuery)).perform(pressImeActionButton())
        onView(withId(R.id.listRecipes)).check(RecyclerViewItemCountAssertion(30))
    }

    @Test
    fun testSubmitGimmeRecipesSearchShowsAllRecipes() {
        onView(withId(R.id.editQuery)).perform(typeText("Gimme Recipes"))
        onView(withId(R.id.editQuery)).perform(pressImeActionButton())
        onView(withId(R.id.listRecipes)).check(RecyclerViewItemCountAssertion(30))
    }

    @Test
    fun testSubmitPadThaiSearchShowsValidRecipes() {
        onView(withId(R.id.editQuery)).perform(typeText("Show me some pad thai recipes!"))
        onView(withId(R.id.editQuery)).perform(pressImeActionButton())

        // check if title of first recipe contains "Pad Thai"
        onView(withId(R.id.listRecipes))
                .perform(scrollToPosition<RecyclerView.ViewHolder>(0))
                .check(matches(atPosition(0, hasDescendant(withText(containsString("Pad Thai"))))))
    }

    @Test
    fun testClickRecipeOpensDetails() {
        onView(withId(R.id.editQuery)).perform(typeText("I really need a pizza!"))
        onView(withId(R.id.editQuery)).perform(pressImeActionButton())

        // click first recipe item
        onView(withId(R.id.listRecipes)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1,
                        click()))

        // check if details page displayed
        onView(withId(R.id.imgRecipeDetails)).check(matches(isDisplayed()))
    }
}
