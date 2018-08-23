package com.uop.fithr

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ActivitiesHeart {

    @SerializedName("customHeartRateZones")
    @Expose
    var customHeartRateZones: List<Any>? = null
    @SerializedName("dateTime")
    @Expose
    var dateTime: String? = null
    @SerializedName("heartRateZones")
    @Expose
    var heartRateZones: List<HeartRateZone>? = null
    @SerializedName("value")
    @Expose
    var value: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param dateTime
     * @param heartRateZones
     * @param customHeartRateZones
     * @param value
     */
    constructor(customHeartRateZones: List<Any>, dateTime: String, heartRateZones: List<HeartRateZone>, value: String) : super() {
        this.customHeartRateZones = customHeartRateZones
        this.dateTime = dateTime
        this.heartRateZones = heartRateZones
        this.value = value
    }

    fun withCustomHeartRateZones(customHeartRateZones: List<Any>): ActivitiesHeart {
        this.customHeartRateZones = customHeartRateZones
        return this
    }

    fun withDateTime(dateTime: String): ActivitiesHeart {
        this.dateTime = dateTime
        return this
    }

    fun withHeartRateZones(heartRateZones: List<HeartRateZone>): ActivitiesHeart {
        this.heartRateZones = heartRateZones
        return this
    }

    fun withValue(value: String): ActivitiesHeart {
        this.value = value
        return this
    }

}