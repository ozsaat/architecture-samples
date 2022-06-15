package com.example.android.architecture.blueprints.todoapp.utils

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.android.architecture.blueprints.todoapp.utils.EspressoExtensions.Companion.searchForChildViewInRoot
import org.hamcrest.Matcher

object WaitForViewUtils {

    fun waitForItemToBeDisplayed(matcher: Matcher<View>) {
        waitForView(matcher).check(matches(isDisplayed()))
    }

    fun waitForItemAndTap(matcher: Matcher<View>) {
        waitForView(matcher).perform(click())
    }

    private fun waitForView(
        viewMatcher: Matcher<View>,
        waitMillis: Int = 10000,
        waitMillisPerTry: Long = 100
    ): ViewInteraction {

        // Derive the max tries
        val maxTries = waitMillis / waitMillisPerTry.toInt()

        var tries = 0

        for (i in 0..maxTries)
            try {
                // Track the amount of times we've tried
                tries++

                // Search the root for the view
                onView(isRoot()).perform(searchForChildViewInRoot(viewMatcher))

                // If we're here, we found our view. Now return it
                return onView(viewMatcher)

            } catch (e: Exception) {

                if (tries == maxTries) {
                    throw e
                }
                try {
                    Thread.sleep(waitMillisPerTry)
                } catch (ex: InterruptedException) {
                    // ignore
                }
            }

        throw Exception("Error finding a view matching $viewMatcher")
    }
}
