package com.einfoplanet.demo.listeners

import com.einfoplanet.demo.model.User

interface AcceptDeclineButtonClickListener {
    fun onButtonClick(selectedUser: User, flag: Int)
    fun onCardSelected(selectedUser: User)
}