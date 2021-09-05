package com.stephen.newssearch;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class NewsActivityInstrumentationTest {
    @Rule
    public ActivityScenarioRule<NewsActivity> activityTestRule =
            new ActivityScenarioRule<>(NewsActivity.class);

    private View activityDecorView;

    @Before
    public void setUp() {
        activityTestRule.getScenario().onActivity(new ActivityScenario.ActivityAction<NewsActivity>() {
            @Override
            public void perform(NewsActivity activity) {
                activityDecorView = activity.getWindow().getDecorView();
            }
        });
    }

    @Test
    public void listItemClickDisplaysToastWithCorrectRestaurant() {
        String newsName = "Anti-Abortion Whistleblowing Site Gets New Home With Provider Known for Hosting Extremists - The Daily Beast";
        onData(anything())
                .inAdapterView(withId(R.id.listView))
                .atPosition(0)
                .perform(click());
        onView(withText(newsName)).inRoot(withDecorView(not(activityDecorView)))
                .check(matches(withText(newsName)));
    }
}
