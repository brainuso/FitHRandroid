package com.uop.fithr

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val person = Person()
        val HR = HRCalc()
        val btnShow = findViewById<Button>(R.id.profile_save_button)

        btnShow.setOnClickListener(object: View.OnClickListener {
           override  fun onClick(v: View){
               //Assign user input to class instance person of Person()
                person.id = findViewById<EditText>(R.id.agent_edit).text.toString()
                person.age = findViewById<EditText>(R.id.age_edit).text.toString().toInt()
                person.weight = findViewById<EditText>(R.id.weight_edit).text.toString().toDouble()
               person.height = findViewById<EditText>(R.id.height_edit).text.toString().toDouble()

               //Assign user input to class instance HR of HRCalc()
               HR.rest = findViewById<EditText>(R.id.restHR_edit).text.toString().toDouble()
              person.maxHR = HR.calcMaxHR(person.weight, person.age)
               //parse value to calculate maximum HR using Heil method
               //Plug max HR value to display TextView
               val  display =  findViewById<TextView>(R.id.max_hr)
                display.text =  "${person.maxHR}"

               //Toast.makeText(applicationContext, person.id, Toast.LENGTH_LONG).show()


            }
        })




    }


  /*  fun launchSaveProfile(view: View) {
        person.id
        val intent = Intent(this, MainActivity::class.java)

        startActivity(intent)}*/
}

