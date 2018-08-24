package com.uop.fithr

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

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
    constructor()

    /**
     *
     * @param time
     * @param value
     */
    constructor(time: String, value: Double?) : super() {
//        converts all time to milliseconds
//        this.time = timeFormat(time)
        this.time = time
        this.value = value
    }
//    init {
//        this.time = timeFormat(time)
//        this.value = value
//    }

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
   //        return time in milliseconds // [10000 millsecs, 74.0]
        return timeFormat(this.time) + ", "+ this.value
    }
//    Show last HR value
    fun lastHr():String{
    return this.value.toString()
    }

    fun timeData():String{
        return this.time.toString()
    }

//    converts time to milliseconds
    fun timeFormat(time: String?): String?{
    val tf = SimpleDateFormat("HH:mm:ss")
    tf.setTimeZone(TimeZone.getTimeZone("GMT"));
    val d = tf.parse(time)
    val time = d.time.toString()
    return time
    }
}