package com.uop.fithr

import android.content.Intent.getIntent
import android.support.v4.app.Fragment
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.tab_hr.*
import okhttp3.*
import org.w3c.dom.Text
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import org.json.JSONArray

import java.text.SimpleDateFormat
import java.util.*


// class for manipulating tab_HR xml

public class TabHR : Fragment() {
val TAG = "HRvalue "
    val url: String = "https://api.fitbit.com/1/user/-/"
    val endpoint: String = "activities/heart/date/today/1d/1sec/time/00:00/00:15.json"
    val client = OkHttpClient()
    val gson = GsonBuilder().create()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.tab_hr, container, false)

        return rootView
    }

    override fun onResume() {
        super.onResume()
//        activity?.intent
        val extras: Bundle? =  activity?.intent?.extras
        val accessToken = extras?.getString("accessToken")
        val tokenType = extras?.getString("tokenType")

        GetEndpointData(url+endpoint, accessToken, tokenType)

    }

    fun GetEndpointData(url: String, accessToken: String?, tokenType: String?){
        val request = Request.Builder()
                .url(url)
                .header("Authorization", tokenType+ " " + accessToken)
                .addHeader("Accept-Language", "en_GB")
                .build()

        val call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Toast.makeText(context, "didn't work", Toast.LENGTH_SHORT)
            }

            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()

                val stream = BufferedInputStream(body!!.byteStream())

                val hrData = readStream(stream)
                DisplayResponse(hrData)
            }
        })
    }

    //    Display Heart rate result
    fun DisplayResponse(result : String){
        Handler(Looper.getMainLooper()).post(Runnable{
            val dataset = gson.fromJson(result, HeartRateValues::class.java)
//            val set = gson.fromJson(result, HeartRateValues::class.java)
            if(dataset != null){
//           time and value || 00:00:00 : Xx.x
                val text = dataset.activitiesHeartIntraday?.dataset.toString()
                Log.d(TAG, "the dataset is: $text")
//            shows last heart rate data from dataset :HeartRateValues
                HR.text = dataset.activitiesHeartIntraday?.dataset?.last()?.lastHr()
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