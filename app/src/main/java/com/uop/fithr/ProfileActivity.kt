package com.uop.fithr

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

    }
    fun launchSaveProfile(){
//        Declare a new person from Person class and Parse editText input to person instance
//        val person = Person()
    /*    person.id = (EditText) findViewById<String>(R.id.agent_edit)
        person.age = (EditText) findViewById<String>(R.id.age_edit)
        person.height = (EditText) findViewById<String>(R.id.height_edit)
        person.weight = (EditText) findViewById<String>(R.id.weight_edit)

//        Declare a new HR from Person class inner HR class and parse editText input to HR instance
        val HR = Person().HR()
        HR.rest = (EditText) findViewById<String>(R.id.restHR_edit)
    */}

    fun goBackMainActivity(view: View){
        val intent = Intent(this, MainActivity::class.java)

        startActivity(intent)
    }

}
