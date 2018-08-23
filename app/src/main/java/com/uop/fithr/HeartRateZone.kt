package com.uop.fithr

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HeartRateZone {

    @SerializedName("caloriesOut")
    @Expose
    var caloriesOut: Double? = null
    @SerializedName("max")
    @Expose
    var max: Double? = null
    @SerializedName("min")
    @Expose
    var min: Double? = null
    @SerializedName("minutes")
    @Expose
    var minutes: Double? = null
    @SerializedName("name")
    @Expose
    var name: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param min
     * @param minutes
     * @param max
     * @param name
     * @param caloriesOut
     */
    constructor(caloriesOut: Double?, max: Double?, min: Double?, minutes: Double?, name: String) : super() {
        this.caloriesOut = caloriesOut
        this.max = max
        this.min = min
        this.minutes = minutes
        this.name = name
    }

    fun withCaloriesOut(caloriesOut: Double?): HeartRateZone {
        this.caloriesOut = caloriesOut
        return this
    }

    fun withMax(max: Double?): HeartRateZone {
        this.max = max
        return this
    }

    fun withMin(min: Double?): HeartRateZone {
        this.min = min
        return this
    }

    fun withMinutes(minutes: Double?): HeartRateZone {
        this.minutes = minutes
        return this
    }

    fun withName(name: String): HeartRateZone {
        this.name = name
        return this
    }

}