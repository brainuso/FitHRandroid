package com.uop.fithr

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_fitbit_data.*
import okhttp3.*
import android.os.Looper

import android.util.Log
import com.google.gson.Gson
import org.json.JSONObject
import java.io.*


class FitbitDataActivity : AppCompatActivity() {
    val client = OkHttpClient()

    val gson = Gson()
    val TAG = "MyActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fitbit_data)

                //works but now to figure out how to extract time and hr value
                 // val url: String = "https://api.fitbit.com/1/user/-/activities/heart/date/2018-08-16/1d/1sec.json"
        //hr value in the last 15 minutes.
                  val url: String = "https://api.fitbit.com/1/user/-/activities/heart/date/today/1d/1sec/time/00:00/00:15.json"

 val extras: Bundle = intent.extras
        val accessToken = extras.getString("accessToken")
       run(url, accessToken)

    }

    fun DisplayResponse(result : String){
        Handler(Looper.getMainLooper()).postDelayed(Runnable{


             textView.text = result
          Log.d(TAG,"$result")
            /* Create an Intent that will start the Menu-Activity. *//*
            val mainIntent = Intent(this, Menu::class.java)
            this.startActivity(mainIntent)
            this.finish()*/
        }, 3000)
    }
    fun DisplayJsonResponse(result : JSONObject){
        Handler(Looper.getMainLooper()).postDelayed(Runnable{


             textView.text = result.toString()
            /* Create an Intent that will start the Menu-Activity. *//*
            val mainIntent = Intent(this, Menu::class.java)
            this.startActivity(mainIntent)
            this.finish()*/
        }, 3000)
    }

    fun run(url: String, accessToken: String){
        val request = Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + accessToken)
                .addHeader("Accept-Language", "en_GB")
                //.addHeader("scope", "heartrate")
                .build()

        val call = client.newCall(request)

        call.enqueue(object : Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                Toast.makeText(applicationContext, "didn't work", Toast.LENGTH_SHORT)
            }

            override fun onResponse(call: Call?, response: Response?) {
                 val body = response?.body()
                  /*  val json = JSONObject(body)

DisplayJsonResponse(json)*/
                Log.d(TAG, body.toString())
                val stream = BufferedInputStream(body!!.byteStream())

                DisplayResponse(readStream(stream))

            }
        })
    }

    fun readStream(inputStream: BufferedInputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        bufferedReader.forEachLine { stringBuilder.append(it) }
        return stringBuilder.toString()
    }
}
