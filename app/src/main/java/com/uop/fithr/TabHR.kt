package com.uop.fithr

import android.app.*
import android.content.*
import android.content.ContentValues.TAG
import android.content.Context.MODE_PRIVATE
import android.content.Intent.CATEGORY_LAUNCHER
import android.content.Intent.getIntent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.*
import android.support.v4.app.Fragment
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
import androidx.browser.browseractions.BrowserActionItem
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
    val url: String = "https://api.fitbit.com/1/user/-/activities/heart/date/today/1d/1sec/time/"
//    endpoint needs to be updated every minute but couldn't find a way to manipulate time in order to acheive that the
//    current end point is set to 10am and 10:01am.
    val endpoint: String = "12:45/12:46.json"
//    val endpoint: String = "activities/heart/date/today/1d/1sec.json" doesn't work
    val client = OkHttpClient()
    val gson = GsonBuilder().create()

    private var notificationManager: NotificationManager? = null
    private var alarmManager: AlarmManager? = null
    val bpm: String = "bpm"
    val gsonPref = Gson()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.tab_hr, container, false)

        notificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotifcationChannel("www.choice.com", "NotifyDemo", "Example Channel")

        alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

//        val broadcastManager = object: BroadcastReceiver(){
//            override fun onReceive(context: Context?, intent: Intent?) {
//                Toast.makeText(context, "hearing you", Toast.LENGTH_LONG).show()
//                Log.d("MyActivity", "hearing you")
////        GetEndpointData(url + endpoint)
//            }
//        }

//        context?.registerReceiver(broadcastManager, IntentFilter("com.uop.fithr"))

        return rootView
    }

    override fun onStart() {
        super.onStart()


        GetEndpointData(url + endpoint)
    }

    override fun onResume() {
        super.onResume()
        GetEndpointData(url + endpoint)
    }

    fun GetEndpointData(url: String) {
        val extras = activity?.intent?.extras
        val accessToken = extras?.getString("accessToken")
        val tokenType = extras?.getString("tokenType")

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
                val hrDataresponse = readStream(stream)
                DisplayResponse(hrDataresponse)

//                val receiver = ComponentName(context, Broadcast::class.java)
//                val pm = context?.packageManager
//                pm?.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                        PackageManager.DONT_KILL_APP)
//
                var calendar = Calendar.getInstance()
                calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
                calendar.add(Calendar.SECOND, 10)
                val intent = Intent()
                val pi = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                alarmManager?.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.get(Calendar.MILLISECOND).toLong(), pi)

            }
        })
    }

    //    Display Heart rate result
    fun DisplayResponse(result: String) {
        Handler(Looper.getMainLooper()).post(Runnable {
            HRvalue.text = getJsonHeartRate(result)

            val mPrefs: SharedPreferences? = PreferenceManager.getDefaultSharedPreferences(context)
            val json = mPrefs?.getString("person", "")
            val foo = gsonPref.fromJson(json, Person::class.java)
            val id = foo.id
            val age = foo.age
            val maxHr = foo.maxHR
//                val thresholdHr = foo.thresholdHR
            val thresholdHr = 70.0

            resting_heart_rate.setText("Resting Heart Rate: 56.2 " + bpm)
            maximum_heart_rate.setText("Maximum Heart Rate: ${maxHr} " + bpm)
            threshold_heart_rate.setText("Threshold Heart Rate: ${thresholdHr} " + bpm)

            paramCheck(getJsonHeartRate(result)!!.toDouble(), thresholdHr)

        })
    }

    fun getJsonHeartRate(value: String): String? {
        val dataset = gson.fromJson(value, HeartRateValues::class.java)
//           time and value || 00:00:00 : Xx.x
        val hrData = dataset.activitiesHeartIntraday?.dataset.toString()
        Log.d(TAG, "dataset: $hrData")
//            shows last heart rate data from dataset :HeartRateValues
        var currHr = dataset.activitiesHeartIntraday?.dataset?.last()?.lastHr()

        return currHr
    }

    fun readStream(inputStream: BufferedInputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        bufferedReader.forEachLine { stringBuilder.append(it) }
        return stringBuilder.toString()
    }

    fun paramCheck(currHr: Double, thresholdHr: Double) {
        if (currHr >= thresholdHr) {
            val highHr: String = "Hello, your heart rate is high. Are you alright?"
            sendNotification(highHr)
        } else if (currHr < 35) {
            val lowHr: String = "Hello, your heart rate is low. Are you alright?"
            sendNotification(lowHr)
        }
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