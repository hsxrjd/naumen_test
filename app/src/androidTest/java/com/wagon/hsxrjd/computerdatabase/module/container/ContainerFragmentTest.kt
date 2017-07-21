package com.wagon.hsxrjd.computerdatabase.module.container

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.wagon.hsxrjd.computerdatabase.MainActivity
import com.wagon.hsxrjd.computerdatabase.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by erychkov on 7/21/17.
 */
@RunWith(AndroidJUnit4::class)
open class ContainerFragmentTest {

    @Rule @JvmField
    val mActivityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun onClickNext() {
        onView(withId(R.id.button_next)).perform(click())
        onView(withId(R.id.button_next)).perform(click())
        onView(withId(R.id.button_next)).perform(click())
        onView(withId(R.id.text_count)).check(matches(withText("3 of 57")))
        assert(1 == 2)
    }
}