package unlv.erc.emergo.controller;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import org.junit.Before;

import unlv.erc.emergo.R;

public class SearchHealthUnitControllerUITest extends ActivityInstrumentationTestCase2<SearchHealthUnitController> {

  public SearchHealthUnitControllerUITest() {

    super(SearchHealthUnitController.class);
  }

  @Before
  public void setUp() throws Exception {

    super.setUp();
    getActivity();
  }

  public void testSearchMenuAvailability() {

    onView(withId(R.id.search)).check(matches(isDisplayed()));
  }

  public void testIfSearchFieldIsEnabled() {

    onView(withId(R.id.search)).check(matches(isEnabled()));
  }

  public void testIfSearchMenuIsClickable() {

    onView(withId(R.id.search)).perform(click());
  }

  public void testIfFieldReceivesText() {

    onView(withId(R.id.search)).perform(click());
    View view = getActivity().getCurrentFocus();

    onView(withId(view.getId())).perform(typeText("Hos"));
  }

  public void testIfResultsAreShown() {

    onView(withId(R.id.search)).perform(click());

    View view = getActivity().getCurrentFocus();

    onView(withId(view.getId())).perform(typeText("Hos"));

    onView(withId(R.id.list_of_search_us)).check(matches(isDisplayed()));
  }
}