package com.example.android.architecture.blueprints.todoapp.utils

import android.view.View
import org.hamcrest.Matcher

object EspressoTestMatchers {

    fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher? {
        return RecyclerViewMatcher(recyclerViewId)
    }

    fun withDrawable(resourceId: Int): Matcher<View> {
        return DrawableMatcher(resourceId)
    }
}
