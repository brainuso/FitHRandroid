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
import kotlinx.android.synthetic.main.tab_hr.*
import okhttp3.*
import org.w3c.dom.Text
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import org.json.JSONArray



// class for manipulating tab_HR xml

public class TabHR : Fragment() {
val TAG = "HRvalue "
    val url: String = "https://api.fitbit.com/1/user/-/"
    val endpoint: String = "activities/heart/date/today/1d/1sec/time/00:00/00:15.json"
    val client = OkHttpClient()
    val gson = Gson()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.tab_hr, container, false)


        //hrCircle.text = displayHR()
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

            Log.d(TAG, "the JSON is: $result")
//            HR.text = result

          val jsonObject = gson.toJson(result)

          //  section_label.text = jsonObject
            val text = jsonObject.elementAt(2)

            HR.text = text.toString()
            val listdata = ArrayList<String>()


        })
    }

    fun readStream(inputStream: BufferedInputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        bufferedReader.forEachLine { stringBuilder.append(it) }
        return stringBuilder.toString()
    }

    fun displayHR(hrData: String){

    HR.text = hrData

//    Toast.makeText(context, hrData, Toast.LENGTH_LONG).show()
}

}