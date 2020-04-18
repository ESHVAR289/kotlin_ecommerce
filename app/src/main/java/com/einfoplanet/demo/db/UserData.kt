package com.einfoplanet.demo.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// This class is counterpart of User entity in domain layer. This class is used by Room as well
// as retrofit to parse fetched data. We could have further created classes for Room
// and network parsing to separate the concerns. Right now we are mapping the database and network
// class exactly so I have left it like this.
@Entity(tableName = "users")
data class UserData(
        @PrimaryKey var id: String,
        @SerializedName("title") var title: String,
        @SerializedName("first") var first: String,
        @SerializedName("last") var last: String,
        @SerializedName("city") var city: String,
        @SerializedName("state") var state: String,
        @SerializedName("gender")var gender:String,
        @SerializedName("country") var country: String,
        @SerializedName("latitude") var latitude: String,
        @SerializedName("longitude") var longitude: String,
        @SerializedName("date") var date: String,
        @SerializedName("age") var age: Int,
        @SerializedName("large") var large: String,
        @SerializedName("medium") var medium: String,
        @SerializedName("thumbnail") var thumbnail: String,
        @SerializedName("seed") var seed: String,
        @SerializedName("page") var page: Int,
        var flagAcceptDecline: Int = 0)


//Assignment Details:
//
//You have to make a project which has a card which looks alike Shaadi matches card with 2 action button in a recycle view
// and the same should be stored in a database.
//
//On click of a button(accept/decline) on the card, the button should be replaced with a message saying member declined/member accepted and
// the same should be updated in the database. It should work in offline mode and the state should persist.