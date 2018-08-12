package com.uop.fithr

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

//class to store user details

public class Person() : Parcelable {
    var id: String = ""
    var height: Double = 0.0
    var weight: Double = 0.0
    var age: Int = 0
    var maxHR: Double = 0.0

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        height = parcel.readDouble()
        weight = parcel.readDouble()
        age = parcel.readInt()
        maxHR = parcel.readDouble()
    }
    //inner class for HR properties
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeDouble(height)
        parcel.writeDouble(weight)
        parcel.writeInt(age)
        parcel.writeDouble(maxHR)
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
class HRCalc {
    var rest: Double = 0.0
    var now: Double = 0.0
    var min: Double = 0.0

    fun calcMaxHR(weight: Double, age: Int): Double{
        //put HR calculation here
        //Heil method of heart rate
        var kg2lbs: Double = weight * 2.2046
        var max: Double = 211.415 - (0.5 * age) - (0.05 * kg2lbs) + 4.5
        return max
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

