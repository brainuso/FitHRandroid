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

        val editTextId = findViewById<EditText>(R.id.agent_edit);
        val editTextAge = findViewById<EditText>(R.id.age_edit);
        val editTextWeight = findViewById<EditText>(R.id.weight_edit);
        val editTextHeight = findViewById<EditText>(R.id.height_edit);
        val editTextHRrest = findViewById<EditText>(R.id.restHR_edit)

        btnShow.setOnClickListener(object: View.OnClickListener {
           override  fun onClick(v: View){

               //Check the inputs if any is empty
               if (editTextId.text.toString().equals("")){
                   Toast.makeText(applicationContext, "Please enter an ID", Toast.LENGTH_LONG).show()
                   editTextId.requestFocus()
                   return
               }
               if (editTextAge.text.toString().equals("")){
                   Toast.makeText(applicationContext, "Please enter age", Toast.LENGTH_LONG).show()
                   editTextAge.requestFocus()
                   return
               }
               if (editTextWeight.text.toString().equals("")){
                   Toast.makeText(applicationContext, "Please enter weight", Toast.LENGTH_LONG).show()
                   editTextWeight.requestFocus()
                   return
               }
               if (editTextHeight.text.toString().equals("")){
                   Toast.makeText(applicationContext, "Please enter height", Toast.LENGTH_LONG).show()
                   editTextHeight.requestFocus()
                   return
               }

               //Assign user input to class instance person of Person()
               person.id = editTextId.text.toString().trim()
               person.age = editTextAge.text.toString().toInt()
               person.weight = editTextWeight.text.toString().toDouble()
               person.height = editTextHeight.text.toString().toDouble()
               //Assign user input to class instance HR of HRCalc()
               HR.rest = editTextHRrest.text.toString().toDouble()
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

