package com.example.moviemenu.view;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.moviemenu.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void displaysContent() {
        onView(withId(R.id.movies_recycler_view)).check(matches(hasDescendant(withId(R.id.tv_title))));
    }

    @Test
    public void showsSearchedItems() {
        onView(withId(R.id.search_et))
                .perform(typeText("Jungle"))
                .check(matches(withText("Jungle")));
    }

    @Test
    public void doesNotShowNotMatchingItemsOnSearch() {
        onView(withId(R.id.search_et))
                .perform(typeText("Jungle"));

        onView(not(withText("Coco")));
    }

    @Test
    public void showsPreviouslyNotMatchingItemsWhenClearingEditText() {
        onView(withId(R.id.search_et))
                .perform(typeText("Jungle"));

        onView(not(withText("Coco")));

        onView(withId(R.id.search_et))
                .perform(clearText());

        onView(withText("Coco")).check(matches(isDisplayed()));
    }

    @Test
    public void displaysSearchEditText() {
        onView(withId(R.id.search_et)).perform(typeText("Hello search"));
        onView(withId(R.id.search_et)).check(matches(withText("Hello search")));
    }
}