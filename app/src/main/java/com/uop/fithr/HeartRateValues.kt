package com.uop.fithr

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HeartRateValues {

    @SerializedName("activities-heart")
    @Expose
    var activitiesHeart: List<ActivitiesHeart>? = null
    @SerializedName("activities-heart-intraday")
    @Expose
    var activitiesHeartIntraday: ActivitiesHeartIntraday? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param activitiesHeartIntraday
     * @param activitiesHeart
     */
    constructor(activitiesHeart: List<ActivitiesHeart>, activitiesHeartIntraday: ActivitiesHeartIntraday) : super() {
        this.activitiesHeart = activitiesHeart
        this.activitiesHeartIntraday = activitiesHeartIntraday
    }

    fun withActivitiesHeart(activitiesHeart: List<ActivitiesHeart>): HeartRateValues {
        this.activitiesHeart = activitiesHeart
        return this
    }

    fun withActivitiesHeartIntraday(activitiesHeartIntraday: ActivitiesHeartIntraday): HeartRateValues {
        this.activitiesHeartIntraday = activitiesHeartIntraday
        return this
    }

}