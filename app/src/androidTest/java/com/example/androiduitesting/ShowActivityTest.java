package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.instanceOf;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShowActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario =
            new ActivityScenarioRule<>(MainActivity.class);

    // tiny helper so ShowActivity / MainActivity transitions visually settle
    private void tinySleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1. Check whether the activity correctly switched.
     * Tap "Edmonton" in the list -> should navigate to ShowActivity.
     */
    @Test
    public void testSwitch() {
        // Click the first item in the list ("Edmonton" from the default list)
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        tinySleep();

        // Check that ShowActivity views are visible
        onView(withId(R.id.text_city_name)).check(matches(isDisplayed()));
        onView(withId(R.id.button_back)).check(matches(isDisplayed()));
    }

    /**
     * 2. Test whether the city name is consistent.
     * If we clicked "Edmonton", ShowActivity should show "Edmonton".
     */
    @Test
    public void testConsistent() {
        // Click "Edmonton" (position 0 in the default list)
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        tinySleep();

        // Check the text matches "Edmonton"
        onView(withId(R.id.text_city_name))
                .check(matches(withText("Edmonton")));
    }

    /**
     * 3. Test the "back" button.
     * After pressing back, we should be in MainActivity again,
     * and ShowActivity's views should be gone.
     */
    @Test
    public void testBack() {
        // Go to ShowActivity by tapping "Edmonton"
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        tinySleep();

        // Tap the BACK button in ShowActivity
        onView(withId(R.id.button_back)).perform(click());

        tinySleep();

        // We should now see MainActivity UI again:
        onView(withId(R.id.button_add)).check(matches(isDisplayed()));
        onView(withId(R.id.button_clear)).check(matches(isDisplayed()));

        // And ShowActivity's city TextView should not exist anymore
        onView(withId(R.id.text_city_name)).check(doesNotExist());
    }
}
