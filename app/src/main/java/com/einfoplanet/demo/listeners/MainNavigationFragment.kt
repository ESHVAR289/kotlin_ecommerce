package com.einfoplanet.demo.listeners

/**
 * Fragments representing main navigation destinations (shown by [MainActivity]) implement this
 * interface.
 */
interface MainNavigationFragment {

    /**
     * Called by the hosting activity when the Back button is pressed.
     * @return True if the fragment handled the back press, false otherwise.
     */
    fun onBackPressed(): Boolean {
        return false
    }

    /** Called by the hosting activity when the user interacts with it. */
    fun onUserInteraction() {}
}
