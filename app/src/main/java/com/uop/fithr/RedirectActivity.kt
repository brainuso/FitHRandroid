package com.uop.fithr

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_fitbit_data.*
import okhttp3.*
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class RedirectActivity : AppCompatActivity() {
    val client = OkHttpClient()

    val gson = Gson()
    val TAG = "MyActivity"
//    val url: String = "https://api.fitbit.com/1/user/-/"
//    val endpoint: String = "activities/heart/date/today/1d/1sec/time/00:00/00:15.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redirect)

        //retrieve parameters from the CustomTabs Intent
        val returnUrl = intent.data
        GetFitbitParameters(returnUrl)
    }

    fun GetFitbitParameters(returnUrl: Uri){
        if (returnUrl != null) {

            //replaced the # with ? in the response.
            val urlReplace = returnUrl.toString().replace('#','?')

            val finalUrl: Uri = Uri.parse(urlReplace)

            val paramNames = finalUrl.queryParameterNames

            val accessToken = finalUrl.getQueryParameter("access_token")
            val userID = finalUrl.getQueryParameter("user_id")
            val scope = finalUrl.getQueryParameter("scope")
            val tokenType = finalUrl.getQueryParameter("token_type")
            val expiresIn = finalUrl.getQueryParameter("expires_in")

//            Start intent to pass access token to data second page
//            val intent = Intent(this, FitbitDataActivity::class.java)



          //  GetEndpointData(url+endpoint, accessToken)

            val intent = Intent(this, FitbitDataActivity::class.java)

            intent.putExtra("accessToken", accessToken)
            intent.putExtra("tokenType", tokenType)

            startActivity(intent)

        } else {
            Log.d(TAG, "Something is wrong with the return value from Fitbit.getIntent().getData() is NULL?")
        }
    }
}

