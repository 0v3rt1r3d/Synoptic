package ru.andrikeev.android.synoptic.ui.activity.main;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.andrikeev.android.synoptic.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertNotEquals;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

//    private static final int DELAY_MS = 500;
//
//    @Rule
//    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
//
//    @Test
//    public void mainActivityTest() {
//        String firstCity;
//        String secondCity;
//
//        onView(allOf(withId(R.id.cityName)))
//                .perform(scrollTo(), click());
//
//        onView(allOf(withId(R.id.edit_city),
//                        withParent(withId(R.id.fragment)),
//                        isDisplayed()))
//                .perform(click());
//
//        onView(allOf(withId(R.id.edit_city),
//                        withParent(withId(R.id.fragment)),
//                        isDisplayed()))
//                .perform(replaceText("London"), closeSoftKeyboard());
//
//        ViewInteraction recyclerView = onView(
//                allOf(withId(R.id.recycler_city),
//                        withParent(withId(R.id.fragment)),
//                        isDisplayed()));
//
//        sleep(DELAY_MS);
//
//        recyclerView.perform(actionOnItemAtPosition(0, click()));
//
//        sleep(DELAY_MS);
//
//        firstCity = ((TextView) mActivityTestRule
//                .getActivity()
//                .findViewById(R.id.cityName))
//                .getText()
//                .toString();
//
//        ViewInteraction appCompatTextView2 = onView(
//                allOf(withId(R.id.cityName), withText("London")));
//        appCompatTextView2.perform(scrollTo(), click());
//
//        sleep(DELAY_MS);
//
//        ViewInteraction appCompatEditText3 = onView(
//                allOf(withId(R.id.edit_city),
//                        withParent(withId(R.id.fragment)),
//                        isDisplayed()));
//        appCompatEditText3.perform(replaceText("Washington"), closeSoftKeyboard());
//
//        sleep(DELAY_MS);
//
//        ViewInteraction recyclerView2 = onView(
//                allOf(withId(R.id.recycler_city),
//                        withParent(withId(R.id.fragment)),
//                        isDisplayed()));
//        recyclerView2.perform(actionOnItemAtPosition(0, click()));
//
//        sleep(DELAY_MS);
//
//        secondCity = ((TextView) mActivityTestRule
//                .getActivity()
//                .findViewById(R.id.cityName))
//                .getText()
//                .toString();
//
//        assertNotEquals(firstCity,secondCity);
//    }
//
//    private void sleep(int delay){
//        try{
//            Thread.sleep(delay);
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
//    }
}
