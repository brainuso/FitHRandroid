package com.uop.fithr

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

//class to store user details

public class Person() : Parcelable {
    var id: String = ""
    var age: Int = 0
    var maxHR: Double = 0.0
    var thresholdHR: Double = 0.0

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        age = parcel.readInt()
        maxHR = parcel.readDouble()
        thresholdHR = parcel.readDouble()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(age)
        parcel.writeDouble(maxHR)
        parcel.writeDouble(thresholdHR)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }

}
//Class to store HR details and calculate maximum HR
class HRCalc() : Parcelable {
    var rest: Double = 0.0
    var now: Double = 0.0
    var min: Double = 0.0
    var max: Double = 0.0

    constructor(parcel: Parcel) : this() {
        rest = parcel.readDouble()
        now = parcel.readDouble()
        min = parcel.readDouble()
        max = parcel.readDouble()
    }

    fun calcMaxHR(age: Int): Double{
        //put HR calculation here
        //Heil method of heart rate
        /*var kg2lbs: Double = weight * 2.2046
        var max = 211.415 - (0.5 * age) - (0.05 * kg2lbs) + 4.5
       */
        //Accurate formula for MaxHr for people
         var max = 208 - (0.7 * age)

        return max
    }

    fun calcThresholdHR(max: Double): Double{
        var thr = 0.8 * max
        return thr
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(rest)
        parcel.writeDouble(now)
        parcel.writeDouble(min)
        parcel.writeDouble(max)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HRCalc> {
        override fun createFromParcel(parcel: Parcel): HRCalc {
            return HRCalc(parcel)
        }

        override fun newArray(size: Int): Array<HRCalc?> {
            return arrayOfNulls(size)
        }
    }

}
/*

fun main(args: Array<String>){
    val person = Person()
    person.id = "Carl"
    person.weight = 68.8
    person.age = 23
    person.height = 1.77
    val HR = HRCalc()
    HR.rest = 56.2
    val HRmax = HR.calc(person.weight, person.age)
    println("The max HR for ${person.id} is ${HRmax}")
}
*/

