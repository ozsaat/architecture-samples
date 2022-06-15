package com.example.android.architecture.blueprints.todoapp.utils

import android.view.View
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.util.TreeIterables
import org.hamcrest.Matcher

class EspressoExtensions {

    companion object {
        /**
         * Perform action of waiting for a certain view within a single root view
         * @param matcher Generic Matcher used to find our view
         */
        @JvmStatic
        fun searchForChildViewInRoot(matcher: Matcher<View>): ViewAction {

            return object : ViewAction {

                override fun getConstraints(): Matcher<View> {
                    return isRoot()
                }

                override fun getDescription(): String {
                    return "searching for view $matcher in the root view"
                }

                override fun perform(uiController: UiController, view: View) {

                    val childViews: Iterable<View> = TreeIterables.breadthFirstViewTraversal(view)

                    // Look for the match in the tree of childviews
                    if (childViews.any { matcher.matches(it) }) {
                        return
                    }

                    throw NoMatchingViewException.Builder()
                        .withRootView(view)
                        .withViewMatcher(matcher)
                        .build()
                }
            }
        }
    }
}
