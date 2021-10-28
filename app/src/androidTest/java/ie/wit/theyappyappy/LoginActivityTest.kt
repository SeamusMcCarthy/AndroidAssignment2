package ie.wit.theyappyappy

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import ie.wit.theyappyappy.activities.LoginActivity
import ie.wit.theyappyappy.activities.RegisterActivity
import ie.wit.theyappyappy.activities.WalkListActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {
    @Test
    fun test_navLoginActivity() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)
        onView(withId(R.id.txtRegister)).perform(click())
        onView(withId(R.id.registerLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun test_navRegisterActivity() {
        val activityScenario = ActivityScenario.launch(RegisterActivity::class.java)
        onView(withId(R.id.txtLogin)).perform(click())
        onView(withId(R.id.loginLayout)).check(matches(isDisplayed()))
    }
}