package com.uop.fithr

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.Intent.getIntent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.support.v4.app.Fragment
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.support.v4.app.NotificationCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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

class TabHR : Fragment() {
//class TabHR : Fragment(), SwipeRefreshLayout.OnRefreshListener {


    val TAG = "TabHR "
    val url: String = "https://api.fitbit.com/1/user/-/"
    val endpoint: String = "activities/heart/date/today/1d/1sec/time/00:00/00:15.json"
    val client = OkHttpClient()
    val gson = GsonBuilder().create()

    private var notificationManager: NotificationManager? = null

    val bpm: String = "bpm"

    val gsonPref = Gson()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.tab_hr, container, false)

        notificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        return rootView
    }
//    override fun onRefresh() {
//        Handler(Looper.getMainLooper()).postDelayed(Runnable {
//
//            swipe_container?.setOnRefreshListener(this);
//
//            swipe_container?.setColorSchemeResources(android.R.color.holo_blue_bright,
//                    android.R.color.holo_green_light,
//                    android.R.color.holo_orange_light,
//                    android.R.color.holo_red_light);
//            try {
//                Toast.makeText(context, "hello, got you", Toast.LENGTH_LONG).show()
////                GetEndpointData(url + endpoint, accessToken, tokenType)
//                swipe_container?.isRefreshing = false
//            } catch (e: Exception) {
//                swipe_container?.isRefreshing = false
//                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
//            }
//        }, 5000);
//
//    }

    override fun onStart() {
        super.onStart()
        val extras: Bundle? = activity?.intent?.extras
        val accessToken = extras?.getString("accessToken")
        val tokenType = extras?.getString("tokenType")
        GetEndpointData(url + endpoint, accessToken, tokenType)
    }

    fun GetEndpointData(url: String, accessToken: String?, tokenType: String?) {
        val request = Request.Builder()
                .url(url)
                .header("Authorization", tokenType + " " + accessToken)
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
    fun DisplayResponse(result: String) {
        Handler(Looper.getMainLooper()).post(Runnable {
            val dataset = gson.fromJson(result, HeartRateValues::class.java)
            if (dataset != null) {
//           time and value || 00:00:00 : Xx.x
                val hrData = dataset.activitiesHeartIntraday?.dataset.toString()
                Log.d(TAG, "dataset: $dataset")
//            shows last heart rate data from dataset :HeartRateValues
                var currHr = dataset.activitiesHeartIntraday?.dataset?.last()?.lastHr()

                HRvalue.text = currHr
                resting_heart_rate.append(" 56.2" +bpm)
                val mPrefs: SharedPreferences? = PreferenceManager.getDefaultSharedPreferences(context)
                val json = mPrefs?.getString("person", "")
                val foo = gsonPref.fromJson(json, Person::class.java)
                val id = foo.id
                val age = foo.age
                val maxHr = foo.maxHR
//                val thresholdHr = foo.thresholdHR

                val thresholdHr = 90.0

                maximum_heart_rate.append(" ${maxHr}" + bpm)
                threshold_heart_rate.append(" ${thresholdHr}" + bpm)

                threshholdParamCheck(currHr!!.toDouble(), thresholdHr)


            }
        })
    }

    fun readStream(inputStream: BufferedInputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        bufferedReader.forEachLine { stringBuilder.append(it) }
        return stringBuilder.toString()
    }


    fun threshholdParamCheck(currHr: Double, thresholdHr: Double) {
        if (currHr >= thresholdHr) {
            val highHr: String = "Hello, your heart rate is high. Are you alright?"
            sendNotification(highHr)
        } else if (currHr < 35) {
            val highHr: String = "Hello, your heart rate is low. Are you alright?"
            sendNotification(highHr)
        }
        createNotifcationChannel("www.choice.com",
                "NotifyDemo", "Example Channel")
    }

    private fun sendNotification(text: String) {

        val notificationID = 101
        val notifIntent = Intent(context, NotificationActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(context, 0,
                notifIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val channelID = "www.choice.com"

        val notification = NotificationCompat.Builder(activity!!.applicationContext,
                channelID).setContentTitle("Hello there")
                .setContentText(text)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setChannelId(channelID)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()
        notificationManager?.notify(notificationID, notification)
    }

    private fun createNotifcationChannel(id: String, name: String, description: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(id, name, importance)

            channel.description = description
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern =
                    longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            notificationManager?.createNotificationChannel(channel)
        }
    }
}