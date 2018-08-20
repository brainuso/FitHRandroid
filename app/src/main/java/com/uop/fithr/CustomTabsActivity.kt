package com.uop.fithr

import android.content.ComponentName
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.support.customtabs.CustomTabsClient
import android.support.customtabs.CustomTabsIntent
import android.support.customtabs.CustomTabsServiceConnection
import android.support.customtabs.CustomTabsSession
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class CustomTabsActivity : AppCompatActivity() {
    val CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";


    private var mCustomTabsServiceConnection: CustomTabsServiceConnection? = null
    private var mClient: CustomTabsClient? = null
    private var mCustomTabsSession: CustomTabsSession? = null
    //FItbit content
    private val CLIENT_ID: String = "22CTQS"
    private val CLIENT_SECRET: String = "c38350b61da3aa821865ed879a8d80cd"
    private val REDIRECT_URL: String = "fithr://finished"

    private val AUTH_URL: String = "https://www.fitbit.com/oauth2/authorize"
    private val REFRESH_TOKEN: String = "https://api.fitbit.com/oauth2/token"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_custom_tabs)
    }

    override fun onResume(){
        super.onResume()



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
            CustomTabsClient.bindCustomTabsService(this, CUSTOM_TAB_PACKAGE_NAME, mCustomTabsServiceConnection);

    }



    fun loadCustomTabforSite(url: String, color: Int = Color.CYAN){
        val customTabsIntent = CustomTabsIntent.Builder(mCustomTabsSession)
                .setToolbarColor(color)
                .setShowTitle(true)
                .build()

        customTabsIntent.launchUrl(this, Uri.parse(url))

    }

}
