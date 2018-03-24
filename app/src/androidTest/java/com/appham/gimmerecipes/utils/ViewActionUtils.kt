package com.appham.gimmerecipes.utils

import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.v7.widget.RecyclerView
import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher

object ViewActionUtils {

    fun clickChildViewWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById<View>(id)
                v?.performClick()
            }
        }
    }

    fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
        checkNotNull(itemMatcher)
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder = view.findViewHolderForAdapterPosition(position)
                // has no item on such position
                return viewHolder != null && itemMatcher.matches(viewHolder.itemView)
            }
        }
    }


}