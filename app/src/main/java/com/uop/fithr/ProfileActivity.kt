package com.uop.fithr

import android.content.ComponentName
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.customtabs.CustomTabsClient
import android.support.customtabs.CustomTabsIntent
import android.support.customtabs.CustomTabsServiceConnection
import android.support.customtabs.CustomTabsSession
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import java.io.Serializable
import java.text.DecimalFormat

class ProfileActivity : AppCompatActivity() {
    val CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";
    val person = Person()
    val HR = HRCalc()
//    Class declaration
    //Chrome warm up
    private var mCustomTabsServiceConnection: CustomTabsServiceConnection? = null
    private var mClient: CustomTabsClient? = null
    private var mCustomTabsSession: CustomTabsSession? = null

    //Fitbit content
    private val CLIENT_ID: String = "22CTQS"
    private val CLIENT_SECRET: String = "c38350b61da3aa821865ed879a8d80cd"
    private val REDIRECT_URL: String = "fithr://finished"
    //Token details
    private val AUTH_URL: String = "https://www.fitbit.com/oauth2/authorize"
    private val REFRESH_TOKEN: String = "https://api.fitbit.com/oauth2/token"

    //Formatting
    private val twoDecimalFormat = DecimalFormat("00.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        val btnLogin = findViewById<Button>(R.id.profile_save_button)

        val editTextId = findViewById<EditText>(R.id.agent_edit)
        val editTextAge = findViewById<EditText>(R.id.age_edit)
        val  display =  findViewById<TextView>(R.id.max_hr)

//        check
        if(person.id != ""){
            editTextId.setText(person.id)
            editTextAge.setText(person.age)
            display.text = person.maxHR.toString()
        }

        btnLogin.setOnClickListener(object: View.OnClickListener {
           override  fun onClick(v: View){
//               Validate user input
              InputValidation(editTextId, editTextAge)
//               Calculate max hr and display in text view and Toast.
              DisplayMaxHR(display)
//               thresholdHr = threshold heart rate
             val thresholdHr = HR.calcThresholdHR(person.maxHR)

               //Delay for toast to display and them move to MainActivity
               val background = object : Thread (){
                   override fun run() {
                       try {
                           Thread.sleep(3000)
                           mCustomTabsServiceConnection = object : CustomTabsServiceConnection() {
                               override fun onCustomTabsServiceConnected(componentName: ComponentName, customTabsClient: CustomTabsClient) {
                                   //Pre-warming
                                   mClient = customTabsClient
                                   mClient?.warmup(0L)
                                   mCustomTabsSession = mClient?.newSession(null)
                                   loadCustomTabforSite(url = AUTH_URL +
                                           "?response_type=token" +
                                           "&client_id="+ CLIENT_ID +
                                           "&redirect_uri="+ REDIRECT_URL +
                                           "&scope=heartrate" +
                                           "&expires_in=604800")
                               }
                               override fun onServiceDisconnected(name: ComponentName) {
                                   mClient = null
                               }
                           }
                           CustomTabsClient.bindCustomTabsService(applicationContext, CUSTOM_TAB_PACKAGE_NAME, mCustomTabsServiceConnection);
                       }catch (e : Exception){
                           e.printStackTrace()
                       }
                   }
               }
               background.start()
            }
        })

    }

    fun loadCustomTabforSite(url: String, color: Int = Color.CYAN){
        val customTabsIntent = CustomTabsIntent.Builder(mCustomTabsSession)
                .setToolbarColor(color)
                .setShowTitle(true)
                .build()
        customTabsIntent.launchUrl(this, Uri.parse(url))

    }
//    INput field validation
    fun InputValidation(editTextId: EditText, editTextAge: EditText){
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

     SetClassValues(editTextId, editTextAge)
    }

    //Parse input to Person() class
    fun SetClassValues(editTextId: EditText, editTextAge: EditText){
        person.id = editTextId.text.toString().trim()
        person.age = editTextAge.text.toString().toInt()
    }

    //Calculate maximum HR and convert to 2 decimal place
    fun CalcHr(): Double{
        person.maxHR = twoDecimalFormat.format(HR.calcMaxHR(person.age)).toDouble()

        return person.maxHR
    }



    //Display maximum Heart Rate
    fun DisplayMaxHR(display: TextView){
        CalcHr()
        Toast.makeText(applicationContext, "Max Heart rate Saved ${person.maxHR}", Toast.LENGTH_LONG).show()
        display.text =  "${person.maxHR}"
    }
}

