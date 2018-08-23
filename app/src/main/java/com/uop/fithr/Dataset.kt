package com.uop.fithr

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Dataset {

    @SerializedName("time")
    @Expose
    var time: String? = null
    @SerializedName("value")
    @Expose
    var value: Double? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param time
     * @param value
     */
    constructor(time: String, value: Double?) : super() {
        this.time = time
        this.value = value
    }

    fun withTime(time: String): Dataset {
        this.time = time
        return this
    }

    fun withValue(value: Double?): Dataset {
        this.value = value
        return this
    }

//    custom functions
    override fun toString():String{
//        return "Time" + this.time + ": " + "HR "+ this.value
        return this.time + ": "+ this.value
    }
//    Show last HR value
    fun lastHr():String{
    return this.value.toString()
    }
}