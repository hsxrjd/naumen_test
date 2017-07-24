package com.wagon.hsxrjd.computerdatabase.module.container

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.MediumTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.wagon.hsxrjd.computerdatabase.MainActivity
import com.wagon.hsxrjd.computerdatabase.R
import com.wagon.hsxrjd.computerdatabase.adapter.RecyclerAdapterFactory
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by erychkov on 7/21/17.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
open class ContainerFragmentTest {

    @Rule
    @JvmField
    val mActivityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun onClickNext() {
        onView(withId(R.id.button_next)).perform(click())
        onView(withId(R.id.button_prev)).check(matches(isEnabled()))
    }

    @Test
    fun onClickPrev() {
        onView(withId(R.id.button_next)).perform(click())
        onView(withId(R.id.button_prev)).perform(click())
        onView(withId(R.id.button_prev)).check(matches(not(isEnabled())))
    }

    @Test
    fun onListItemClick() {
        val recyclerView: RecyclerView = mActivityRule.activity.findViewById(R.id.recycler_view_cards) as RecyclerView
        val itemCount: Int = recyclerView.adapter.itemCount
        if (itemCount > 0) {
            onView(withId(R.id.recycler_view_cards)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerAdapterFactory.BaseViewHolder>(itemCount - 1, click()))
            onView(withId(R.id.main_list)).check(matches(isDisplayed()))
        } else {
            check(false)
        }
    }
}