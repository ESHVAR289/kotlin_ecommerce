package com.einfoplanet.demo.model


import com.google.gson.annotations.SerializedName

data class Restaurant(
    @SerializedName("R") var r: R,
    @SerializedName("apikey") var apikey: String,
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
    @SerializedName("url") var url: String,
    @SerializedName("location") var location: Location,
    @SerializedName("switch_to_order_menu") var switchToOrderMenu: Int,
    @SerializedName("cuisines") var cuisines: String,
    @SerializedName("timings") var timings: String,
    @SerializedName("average_cost_for_two") var averageCostForTwo: Int,
    @SerializedName("price_range") var priceRange: Int,
    @SerializedName("currency") var currency: String,
    @SerializedName("highlights") var highlights: List<String>,
    @SerializedName("offers") var offers: List<Any>,
    @SerializedName("opentable_support") var opentableSupport: Int,
    @SerializedName("is_zomato_book_res") var isZomatoBookRes: Int,
    @SerializedName("mezzo_provider") var mezzoProvider: String,
    @SerializedName("is_book_form_web_view") var isBookFormWebView: Int,
    @SerializedName("book_form_web_view_url") var bookFormWebViewUrl: String,
    @SerializedName("book_again_url") var bookAgainUrl: String,
    @SerializedName("thumb") var thumb: String,
    @SerializedName("user_rating") var userRating: UserRating,
    @SerializedName("all_reviews_count") var allReviewsCount: Int,
    @SerializedName("photos_url") var photosUrl: String,
    @SerializedName("photo_count") var photoCount: Int,
    @SerializedName("photos") var photos: List<Photo>,
    @SerializedName("menu_url") var menuUrl: String,
    @SerializedName("featured_image") var featuredImage: String,
    @SerializedName("medio_provider") var medioProvider: Int,
    @SerializedName("has_online_delivery") var hasOnlineDelivery: Int,
    @SerializedName("is_delivering_now") var isDeliveringNow: Int,
    @SerializedName("include_bogo_offers") var includeBogoOffers: Boolean,
    @SerializedName("deeplink") var deeplink: String,
    @SerializedName("is_table_reservation_supported") var isTableReservationSupported: Int,
    @SerializedName("has_table_booking") var hasTableBooking: Int,
    @SerializedName("events_url") var eventsUrl: String,
    @SerializedName("phone_numbers") var phoneNumbers: String,
    @SerializedName("all_reviews") var allReviews: AllReviews,
    @SerializedName("establishment") var establishment: List<String>
) {
    var strHighlights: String = ""
    data class R(
        @SerializedName("has_menu_status") var hasMenuStatus: HasMenuStatus,
        @SerializedName("res_id") var resId: Int
    ) {
        data class HasMenuStatus(
            @SerializedName("delivery") var delivery: Int,
            @SerializedName("takeaway") var takeaway: Int
        )
    }

    data class Location(
        @SerializedName("address") var address: String,
        @SerializedName("locality") var locality: String,
        @SerializedName("city") var city: String,
        @SerializedName("city_id") var cityId: Int,
        @SerializedName("latitude") var latitude: String,
        @SerializedName("longitude") var longitude: String,
        @SerializedName("zipcode") var zipcode: String,
        @SerializedName("country_id") var countryId: Int,
        @SerializedName("locality_verbose") var localityVerbose: String
    )

    data class UserRating(
        @SerializedName("aggregate_rating") var aggregateRating: String,
        @SerializedName("rating_text") var ratingText: String,
        @SerializedName("rating_color") var ratingColor: String,
        @SerializedName("rating_obj") var ratingObj: RatingObj,
        @SerializedName("votes") var votes: String
    ) {
        data class RatingObj(
            @SerializedName("title") var title: Title,
            @SerializedName("bg_color") var bgColor: BgColor
        ) {
            data class Title(
                @SerializedName("text") var text: String
            )

            data class BgColor(
                @SerializedName("type") var type: String,
                @SerializedName("tint") var tint: String
            )
        }
    }

    data class Photo(
        @SerializedName("photo") var photo: Photo
    ) {
        data class Photo(
            @SerializedName("id") var id: String,
            @SerializedName("url") var url: String,
            @SerializedName("thumb_url") var thumbUrl: String,
            @SerializedName("user") var user: User,
            @SerializedName("res_id") var resId: Int,
            @SerializedName("caption") var caption: String,
            @SerializedName("timestamp") var timestamp: Int,
            @SerializedName("friendly_time") var friendlyTime: String,
            @SerializedName("width") var width: Int,
            @SerializedName("height") var height: Int
        ) {
            data class User(
                @SerializedName("name") var name: String,
                @SerializedName("foodie_color") var foodieColor: String,
                @SerializedName("profile_url") var profileUrl: String,
                @SerializedName("profile_image") var profileImage: String,
                @SerializedName("profile_deeplink") var profileDeeplink: String
            )
        }
    }

    data class AllReviews(
        @SerializedName("reviews") var reviews: List<Review>
    ) {
        data class Review(
            @SerializedName("review") var review: List<Any>
        )
    }
}