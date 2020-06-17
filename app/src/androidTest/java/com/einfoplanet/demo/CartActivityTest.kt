package com.einfoplanet.demo


import androidx.room.Room
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.einfoplanet.demo.data.FakeAndroidTestRepository
import com.einfoplanet.demo.db.CartProduct
import com.einfoplanet.demo.db.ShoppingDb
import com.einfoplanet.demo.domain.cartlist.CartProductListUseCase
import com.einfoplanet.demo.ui.cart.CartActivity
import com.einfoplanet.demo.ui.cart.CartViewModel
import com.einfoplanet.demo.util.DataBindingIdlingResource
import com.einfoplanet.demo.util.EspressoIdlingResource
import com.einfoplanet.demo.util.monitorActivity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class CartActivityTest {
    // An Idling Resource that waits for Data Binding to have no pending bindings
    private val dataBindingIdlingResource = DataBindingIdlingResource()
    private lateinit var cartViewModel: CartViewModel

    // Use a fake repository to be injected into the viewmodel
    private lateinit var fakeRepository: FakeAndroidTestRepository

    @Before
    fun setupStatisticsViewModel() {
        // We initialise the repository with no tasks
        fakeRepository = FakeAndroidTestRepository()
        val cartProductListUseCase = CartProductListUseCase(fakeRepository)
        cartViewModel = CartViewModel(cartProductListUseCase)
    }

    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                ShoppingDb::class.java
        ).build()

        val cartProduct = CartProduct(restaurantId = "1",
                restaurantName = "Dominoz4",
                url = "",
                averageCostForTwo = 600,
                thumb = "",
                quantity = 3)

        database.cartDao().insertProduct(cartProduct)
        val cartProduct2 = CartProduct(restaurantId = "2",
                restaurantName = "Dominoz4",
                url = "",
                averageCostForTwo = 800,
                thumb = "",
                quantity = 3)

        database.cartDao().insertProduct(cartProduct2)
    }

    @After
    fun closeDb() = database.close()

    private lateinit var database: ShoppingDb

    @Test
    fun placeOrder() = runBlocking {
        // Start up Tasks screen
        val activityScenario = ActivityScenario.launch(CartActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        onView(withId(R.id.btn_place_order)).check(matches(withText("Place Order")))
        onView(withId(R.id.btn_place_order)).check(matches(isDisplayed())).perform(ViewActions.click())
        onView(withId(R.id.et_name)).perform(ViewActions.replaceText("ESHVAR"))
        onView(withId(R.id.et_email_id)).perform(ViewActions.replaceText("eshvar289@gmail.com"))
        onView(withId(R.id.et_mobile_no)).perform(ViewActions.replaceText("9923911289"))
        onView(withId(R.id.et_complete_address)).perform(ViewActions.replaceText("Washington DC, United States of America"))
        onView(withId(R.id.et_pin_code)).perform(ViewActions.replaceText("401209"))
        onView(withId(R.id.btn_order_now)).perform(ViewActions.click())
        onView(withId(R.id.txt_order_placed)).check(ViewAssertions.matches(ViewMatchers.withText("Your Order has been Placed Successfully!")))

        activityScenario.close()
    }

//    fun editTask() = runBlocking {
////        repository.saveTask(Task("TITLE1", "DESCRIPTION"))
//
////        // Click on the task on the list and verify that all the data is correct
////        onView(ViewMatchers.withText("TITLE1")).perform(ViewActions.click())
////        onView(withId(R.id.btn_place_order)).check(ViewAssertions.matches(ViewMatchers.withText("TITLE1")))
////        onView(withId(R.id.task_detail_description_text)).check(ViewAssertions.matches(ViewMatchers.withText("DESCRIPTION")))
////        onView(withId(R.id.task_detail_complete_checkbox)).check(ViewAssertions.matches(IsNot.not(ViewMatchers.isChecked())))
////
////        // Click on the edit button, edit, and save
////        onView(withId(R.id.edit_task_fab)).perform(ViewActions.click())
////        onView(withId(R.id.add_task_title_edit_text)).perform(ViewActions.replaceText("NEW TITLE"))
////        onView(withId(R.id.add_task_description_edit_text)).perform(ViewActions.replaceText("NEW DESCRIPTION"))
////        onView(withId(R.id.save_task_fab)).perform(ViewActions.click())
////
////
////
////        // Verify task is displayed on screen in the task list.
////        onView(ViewMatchers.withText("NEW TITLE")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
////        // Verify previous task is not displayed
////        onView(ViewMatchers.withText("TITLE1")).check(ViewAssertions.doesNotExist())
//
//        // Make sure the activity is closed before resetting the db:
//        activityScenario.close()
//    }

    /**
     * Idling resources tell Espresso that the app is idle or busy. This is needed when operations
     * are not scheduled in the main Looper (for example when executed on a different thread).
     */
    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

}