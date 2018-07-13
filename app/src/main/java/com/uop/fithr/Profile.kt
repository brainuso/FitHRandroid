package com.uop.fithr

//class to store user details
class Person {
    var id: String = ""
    var height: Double = 0.0
    var weight: Double = 0.0
    var age: Int = 0

    //inner class for HR properties
    inner class HR{
        var rest: Double = 0.0
        var max: Double = 0.0
        var now: Double = 0.0
        var min: Double = 0.0

        fun calc(){
            //put HR calculation here
        }
    }
}


fun main(args: Array<String>){
    val person = Person()
    person.id = "Agent123"
    person.height = 1.85
    val HR = Person().HR()
    HR.rest = 56.1

    println("${person.id} is ${person.height}m tall")
    println(HR.rest)
}


