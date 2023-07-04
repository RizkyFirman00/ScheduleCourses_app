package com.dicoding.courseschedule.ui.home

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.add.AddCourseActivity
import org.junit.Rule
import org.junit.Test

internal class HomeActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun testAddTaskButton() {
        Espresso.onView(withId(R.id.action_add)).perform(ViewActions.click())

        Espresso.onView(withId(R.id.add_course_layout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}