package com.einfoplanet.demo.model

// The highest class without any dependency and representing the User entity around which this
// app is designed.

data class User(
        val id: String,
        var title: String,
        var first: String,
        var last: String,
        var gender: String,
        var city: String,
        var state: String,
        var country: String,
        var latitude: String,
        val longitude: String,
        var date: String,
        var age: Int,
        var large: String,
        var medium: String,
        var thumbnail: String,
        var seed: String,
        var page: Int,
        var flagAcceptDecline: Int
)