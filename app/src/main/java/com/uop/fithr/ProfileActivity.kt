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
import java.io.Serializable
import java.text.DecimalFormat

class ProfileActivity : AppCompatActivity() {
    val person = Person()
    val HR = HRCalc()
   private val percentageFormat = DecimalFormat("00.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        val btnShow = findViewById<Button>(R.id.profile_save_button)

        val editTextId = findViewById<EditText>(R.id.agent_edit);
        val editTextAge = findViewById<EditText>(R.id.age_edit);
        val editTextWeight = findViewById<EditText>(R.id.weight_edit);
        val editTextHeight = findViewById<EditText>(R.id.height_edit);
        val editTextHRrest = findViewById<EditText>(R.id.restHR_edit)
        val  display =  findViewById<TextView>(R.id.max_hr)
        btnShow.setOnClickListener(object: View.OnClickListener {
           override  fun onClick(v: View){

               InputValidation(editTextId, editTextAge,
                       editTextWeight, editTextHeight, editTextHRrest)


               //Assign user input to class instance person of Person()

               Toast.makeText(applicationContext, "Max Heart rate Saved ${person.maxHR}",
                       Toast.LENGTH_SHORT).show()

               //parse value to calculate maximum HR using Heil method
               //Plug max HR value to display TextView

               display.text =  "${person.maxHR}"

               //Delay for toast to display and them move to MainActivity
               val background = object : Thread (){
                   override fun run() {
                       try {
                           Thread.sleep(3000)
                           val intent = Intent(applicationContext, MainActivity::class.java)
                           //intent.putExtra("personKey", person)
                           startActivity(intent)
                       }catch (e : Exception){
                           e.printStackTrace()
                       }
                   }
               }
               background.start()

            }
        })

    }

//    INput field validation
    fun InputValidation(editTextId: EditText, editTextAge: EditText,
                        editTextWeight: EditText, editTextHeight: EditText, editTextHRrest: EditText){
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

     GetClassValues(editTextId, editTextAge, editTextWeight, editTextHeight, editTextHRrest)
    }

    //Parse input to Person() class

    fun GetClassValues(editTextId: EditText, editTextAge: EditText,
                       editTextWeight: EditText, editTextHeight: EditText, editTextHRrest: EditText){
        person.id = editTextId.text.toString().trim()
        person.age = editTextAge.text.toString().toInt()
        person.weight = editTextWeight.text.toString().toDouble()
        person.height = editTextHeight.text.toString().toDouble()

        //Assign user input to class instance HR of HRCalc()
        HR.rest = editTextHRrest.text.toString().toDouble()
        val result = HR.calcMaxHR(person.weight, person.age)

        //convert to 2 decimal place
        person.maxHR = percentageFormat.format(result).toDouble()

    }
}

