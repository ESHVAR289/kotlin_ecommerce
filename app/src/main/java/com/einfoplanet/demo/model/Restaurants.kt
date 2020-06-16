package com.einfoplanet.demo.model


import com.google.gson.annotations.SerializedName

data class Restaurants(
    @SerializedName("location") var location: Location,
    @SerializedName("popularity") var popularity: Popularity,
    @SerializedName("link") var link: String,
    @SerializedName("nearby_restaurants") var nearByRestaurant: List<NearbyRestaurant>,
    @SerializedName("restaurants") var restaurants: List<NearbyRestaurant>
) {
    data class Location(
        @SerializedName("entity_type") var entityType: String,
        @SerializedName("entity_id") var entityId: Int,
        @SerializedName("title") var title: String,
        @SerializedName("latitude") var latitude: String,
        @SerializedName("longitude") var longitude: String,
        @SerializedName("city_id") var cityId: Int,
        @SerializedName("city_name") var cityName: String,
        @SerializedName("country_id") var countryId: Int,
        @SerializedName("country_name") var countryName: String
    )

    data class Popularity(
        @SerializedName("popularity") var popularity: String,
        @SerializedName("nightlife_index") var nightlifeIndex: String,
        @SerializedName("nearby_res") var nearbyRes: List<String>,
        @SerializedName("top_cuisines") var topCuisines: List<String>,
        @SerializedName("popularity_res") var popularityRes: String,
        @SerializedName("nightlife_res") var nightlifeRes: String,
        @SerializedName("subzone") var subzone: String,
        @SerializedName("subzone_id") var subzoneId: Int,
        @SerializedName("city") var city: String
    )

    data class NearbyRestaurant(
        @SerializedName("restaurant") var restaurant: Restaurant
    ) {
        data class Restaurant(
            @SerializedName("R") var r: R,
            @SerializedName("apikey") var apikey: String,
            @SerializedName("id") var id: String,
            @SerializedName("name") var name: String,
            @SerializedName("url") var url: String,
            @SerializedName("location") var location: Location,
            @SerializedName("switch_to_order_menu") var switchToOrderMenu: Int,
            @SerializedName("cuisines") var cuisines: String,
            @SerializedName("average_cost_for_two") var averageCostForTwo: Int,
            @SerializedName("price_range") var priceRange: Int,
            @SerializedName("currency") var currency: String,
            @SerializedName("offers") var offers: List<Any>,
            @SerializedName("zomato_events") var zomatoEvents: List<ZomatoEvent>,
            @SerializedName("opentable_support") var opentableSupport: Int,
            @SerializedName("is_zomato_book_res") var isZomatoBookRes: Int,
            @SerializedName("mezzo_provider") var mezzoProvider: String,
            @SerializedName("is_book_form_web_view") var isBookFormWebView: Int,
            @SerializedName("book_form_web_view_url") var bookFormWebViewUrl: String,
            @SerializedName("book_again_url") var bookAgainUrl: String,
            @SerializedName("thumb") var thumb: String,
            @SerializedName("user_rating") var userRating: UserRating,
            @SerializedName("photos_url") var photosUrl: String,
            @SerializedName("menu_url") var menuUrl: String,
            @SerializedName("featured_image") var featuredImage: String,
            @SerializedName("has_online_delivery") var hasOnlineDelivery: Int,
            @SerializedName("is_delivering_now") var isDeliveringNow: Int,
            @SerializedName("include_bogo_offers") var includeBogoOffers: Boolean,
            @SerializedName("deeplink") var deeplink: String,
            @SerializedName("order_url") var orderUrl: String,
            @SerializedName("order_deeplink") var orderDeeplink: String,
            @SerializedName("is_table_reservation_supported") var isTableReservationSupported: Int,
            @SerializedName("has_table_booking") var hasTableBooking: Int,
            @SerializedName("events_url") var eventsUrl: String
        ) {
            var isLike: Boolean = false
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

            data class ZomatoEvent(
                @SerializedName("event") var event: Event
            ) {
                data class Event(
                    @SerializedName("event_id") var eventId: Int,
                    @SerializedName("friendly_start_date") var friendlyStartDate: String,
                    @SerializedName("friendly_end_date") var friendlyEndDate: String,
                    @SerializedName("friendly_timing_str") var friendlyTimingStr: String,
                    @SerializedName("start_date") var startDate: String,
                    @SerializedName("end_date") var endDate: String,
                    @SerializedName("end_time") var endTime: String,
                    @SerializedName("start_time") var startTime: String,
                    @SerializedName("is_active") var isActive: Int,
                    @SerializedName("date_added") var dateAdded: String,
                    @SerializedName("photos") var photos: List<Photo>,
                    @SerializedName("restaurants") var restaurants: List<Any>,
                    @SerializedName("is_valid") var isValid: Int,
                    @SerializedName("share_url") var shareUrl: String,
                    @SerializedName("show_share_url") var showShareUrl: Int,
                    @SerializedName("title") var title: String,
                    @SerializedName("description") var description: String,
                    @SerializedName("display_time") var displayTime: String,
                    @SerializedName("display_date") var displayDate: String,
                    @SerializedName("is_end_time_set") var isEndTimeSet: Int,
                    @SerializedName("disclaimer") var disclaimer: String,
                    @SerializedName("event_category") var eventCategory: Int,
                    @SerializedName("event_category_name") var eventCategoryName: String,
                    @SerializedName("book_link") var bookLink: String,
                    @SerializedName("types") var types: List<Any>,
                    @SerializedName("share_data") var shareData: ShareData
                ) {
                    data class Photo(
                        @SerializedName("photo") var photo: Photo
                    ) {
                        data class Photo(
                            @SerializedName("url") var url: String,
                            @SerializedName("thumb_url") var thumbUrl: String,
                            @SerializedName("order") var order: Int,
                            @SerializedName("md5sum") var md5sum: String,
                            @SerializedName("id") var id: Int,
                            @SerializedName("photo_id") var photoId: Int,
                            @SerializedName("uuid") var uuid: Long,
                            @SerializedName("type") var type: String
                        )
                    }

                    data class ShareData(
                        @SerializedName("should_show") var shouldShow: Int
                    )
                }
            }

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
        }
    }
}