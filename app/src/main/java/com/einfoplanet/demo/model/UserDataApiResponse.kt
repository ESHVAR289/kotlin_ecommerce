package com.einfoplanet.demo.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class UserDataApiResponse(
        @SerializedName("results") var results: List<Result>,
        @SerializedName("info") var info: Info
) {
    data class Result(
            @PrimaryKey val idr: Long,
            @SerializedName("gender") var gender: String,
            @SerializedName("name") var name: Name,
            @SerializedName("location") var location: Location,
            @SerializedName("email") var email: String,
            @SerializedName("dob") var dob: Dob,
            @SerializedName("registered") var registered: Registered,
            @SerializedName("phone") var phone: String,
            @SerializedName("cell") var cell: String,
            @SerializedName("picture") var picture: Picture,
            @SerializedName("nat") var nat: String
    ) {
        data class Name(
                @SerializedName("title") var title: String,
                @SerializedName("first") var first: String,
                @SerializedName("last") var last: String
        )

        data class Location(
                @SerializedName("street") var street: Street,
                @SerializedName("city") var city: String,
                @SerializedName("state") var state: String,
                @SerializedName("country") var country: String,
                @SerializedName("postcode") var postcode: Any?,
                @SerializedName("coordinates") var coordinates: Coordinates,
                @SerializedName("timezone") var timezone: Timezone
        ) {
            data class Street(
                    @SerializedName("number") var number: Int,
                    @SerializedName("name") var name: String
            )

            data class Coordinates(
                    @SerializedName("latitude") var latitude: String,
                    @SerializedName("longitude") var longitude: String
            )

            data class Timezone(
                    @SerializedName("offset") var offset: String,
                    @SerializedName("description") var description: String
            )
        }

        data class Dob(
                @SerializedName("date") var date: String,
                @SerializedName("age") var age: Int
        )

        data class Registered(
                @SerializedName("date") var date: String,
                @SerializedName("age") var age: Int
        )

        data class Picture(
                @SerializedName("large") var large: String,
                @SerializedName("medium") var medium: String,
                @SerializedName("thumbnail") var thumbnail: String
        )
    }

    data class Info(
            @SerializedName("seed") var seed: String,
            @SerializedName("results") var results: Int,
            @SerializedName("page") var page: Int,
            @SerializedName("version") var version: String
    )
}