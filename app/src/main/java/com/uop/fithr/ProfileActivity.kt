package com.uop.fithr

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.support.customtabs.CustomTabsClient
import android.support.customtabs.CustomTabsIntent
import android.support.customtabs.CustomTabsServiceConnection
import android.support.customtabs.CustomTabsSession
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_profile.*
import org.w3c.dom.Text
import java.io.Serializable
import java.text.DecimalFormat

class ProfileActivity : AppCompatActivity() {
    val CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";
    val person = Person()
    val HR = HRCalc()
    val TAG = "TabHR "
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

    //Shared Preferences

    val gsonPref = Gson()
    //Formatting
    private val twoDecimalFormat = DecimalFormat("00.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        var mPrefs: SharedPreferences = getDefaultSharedPreferences(this)
//        check
        if (person.id != "") {
            agent_edit.setText(person.id)
            age_edit.setText(person.age)
            max_hr.text = person.maxHR.toString()
        }

        profile_save_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
//               Validate user input
                InputValidation(agent_edit, age_edit)
//               Calculate max hr and max_hr in text view and Toast.
                DisplayMaxHR(max_hr)
//               thresholdHr = threshold heart rate
                person.thresholdHR = HR.calcThresholdHR(person.maxHR)

                val prefEditor: SharedPreferences.Editor? = mPrefs?.edit()
                val json = gsonPref.toJson(person)
                prefEditor?.putString("person", json)
                prefEditor?.apply()

                //Delay for toast to max_hr and them move to MainActivity
                val background = object : Thread() {
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
                                            "&client_id=" + CLIENT_ID +
                                            "&redirect_uri=" + REDIRECT_URL +
                                            "&scope=heartrate" +
                                            "&expires_in=604800")
                                }

                                override fun onServiceDisconnected(name: ComponentName) {
                                    mClient = null
                                }
                            }
                            CustomTabsClient.bindCustomTabsService(applicationContext, CUSTOM_TAB_PACKAGE_NAME, mCustomTabsServiceConnection);
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                background.start()
            }
        })

    }

    fun loadCustomTabforSite(url: String, color: Int = Color.CYAN) {
        val customTabsIntent = CustomTabsIntent.Builder(mCustomTabsSession)
                .setToolbarColor(color)
                .setShowTitle(true)
                .build()
        customTabsIntent.launchUrl(this, Uri.parse(url))

    }

    //    INput field validation
    fun InputValidation(agent_edit: EditText, age_edit: EditText) {
        //Check the inputs if any is empty
        if (agent_edit.text.toString().equals("")) {
            Toast.makeText(applicationContext, "Please enter an ID", Toast.LENGTH_LONG).show()
            agent_edit.requestFocus()
            return
        }
        if (age_edit.text.toString().equals("")) {
            Toast.makeText(applicationContext, "Please enter age", Toast.LENGTH_LONG).show()
            age_edit.requestFocus()
            return
        }

        SetClassValues(agent_edit, age_edit)
    }

    //Parse input to Person() class
    fun SetClassValues(agent_edit: EditText, age_edit: EditText) {
        person.id = agent_edit.text.toString().trim()
        person.age = age_edit.text.toString().toInt()
    }

    //Calculate maximum HR and convert to 2 decimal place
    fun calcHr(): Double {
        person.maxHR = twoDecimalFormat.format(HR.calcMaxHR(person.age)).toDouble()
        return person.maxHR
    }

    //Display maximum Heart Rate
    fun DisplayMaxHR(max_hr: TextView): Double {
        calcHr()
        Toast.makeText(applicationContext, "Max Heart rate Saved ${person.maxHR}", Toast.LENGTH_LONG).show()
        max_hr.text = "${person.maxHR}"
        return person.maxHR
    }
}

