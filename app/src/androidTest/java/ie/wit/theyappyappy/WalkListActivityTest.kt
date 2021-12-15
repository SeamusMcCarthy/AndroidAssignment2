package ie.wit.theyappyappy

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import ie.wit.theyappyappy.activities.WalkListActivity
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class WalkListActivityTest {
    @Test
    fun test_navWalkActivity() {
        val activityScenario = ActivityScenario.launch(WalkListActivity::class.java)
        onView(withId(R.id.item_add)).perform(click())
        onView(withId(R.id.walkLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun test_navWalkActivityAndCancel() {
        val activityScenario = ActivityScenario.launch(WalkListActivity::class.java)
        onView(withId(R.id.item_add)).perform(click())
        onView(withId(R.id.item_cancel)).perform(click())
        onView(withId(R.id.walkListLayout)).check(matches(isDisplayed()))
    }

}