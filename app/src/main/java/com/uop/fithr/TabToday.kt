package com.uop.fithr

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.tab_hr.*
import kotlinx.android.synthetic.main.tab_today.*
import okhttp3.*
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

// class for manipulating tab_today xml

public class TabToday : Fragment() {
    val TAG = "TabToday"
    val url: String = "https://api.fitbit.com/1/user/-/"
    val endpoint: String = "activities/heart/date/today/1d/1sec/time/00:00/00:15.json"
    val client = OkHttpClient()
    val gson = GsonBuilder().create()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.tab_today, container, false)




        return rootView
    }

    override fun onResume() {
        super.onResume()
//        activity?.intent
        val extras: Bundle? = activity?.intent?.extras
        val accessToken = extras?.getString("accessToken")
        val tokenType = extras?.getString("tokenType")
        GetEndpointData(url + endpoint, accessToken, tokenType)
    }

    private fun setLineChart() {

        val entries = ArrayList<Entry>()
        entries.add(Entry(1f, 0))
        entries.add(Entry(8f, 1))
        entries.add(Entry(6f, 2))
        entries.add(Entry(2f, 3))
        entries.add(Entry(18f, 4))
        entries.add(Entry(9f, 5))


        val dataSet = LineDataSet(entries, "Heart rate")
        val labels = ArrayList<String>()

        for (i in 1..6) {
            labels.add("${i}")
        }
        //Dispays Label of the xAxis on the bottom
        val xAxis: XAxis = line_chart.xAxis
        xAxis.setAxisMinValue(0f)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        val yAxis: YAxis = line_chart.axisLeft
        yAxis.setAxisMinValue(0f)
        yAxis.setDrawGridLines(false)
        yAxis.setDrawZeroLine(true)

        xAxis.position = XAxis.XAxisPosition.BOTTOM

        val data = LineData(labels, dataSet)
        try {
            line_chart.data = data
        } catch (e: Exception) {
            Log.e("error", e.message)
        }


        //should be setDrawCubic but then the plugin is a diff version. We'd test later
        dataSet.setDrawCubic(true)
        dataSet.setDrawFilled(true)
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
                DisplayGraphResponse(hrData)
            }
        })

    }

    fun DisplayGraphResponse(result: String) {
        Handler(Looper.getMainLooper()).post(Runnable {
            val dataset = gson.fromJson(result, HeartRateValues::class.java)
            if (dataset != null) {
//           time and value || 00:00:00 : Xx.x
                val text = dataset.activitiesHeartIntraday?.dataset.toString()
                Log.d(TAG, "the dataset is: $text")
//            shows last heart rate data from dataset :HeartRateValues

            }
            setLineChart()
        })
    }

    fun readStream(inputStream: BufferedInputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        bufferedReader.forEachLine { stringBuilder.append(it) }
        return stringBuilder.toString()
    }

}