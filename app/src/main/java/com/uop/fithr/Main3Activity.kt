package com.uop.fithr

import android.content.ComponentName
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.customtabs.CustomTabsClient
import android.support.customtabs.CustomTabsIntent
import android.support.customtabs.CustomTabsServiceConnection
import android.support.customtabs.CustomTabsSession
import android.util.Base64
import android.util.Base64.NO_PADDING
import android.util.Base64.NO_WRAP
import android.util.Base64.URL_SAFE
import android.widget.Button
import java.security.SecureRandom



class Main3Activity : AppCompatActivity() {
    val CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";


    private var mCustomTabsServiceConnection: CustomTabsServiceConnection? = null
    private var mClient: CustomTabsClient? = null
    private var mCustomTabsSession: CustomTabsSession? = null

    //FItbit content
    private val CLIENT_ID: String = "22CTQS"
    private val CLIENT_SECRET: String = "c38350b61da3aa821865ed879a8d80cd"
    private val SECURE_KEY: String = ""
    private val REDIRECT_URL: String = "fithr://finished"

    private val AUTH_URL: String = "https://www.fitbit.com/oauth2/authorize"
    private val REFRESH_TOKEN: String = "https://api.fitbit.com/oauth2/token"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)



        /*val sr = SecureRandom()
      val code = ByteArray(32)
      sr.nextBytes(code)
      val verifier = Base64.encodeToString(code, Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING)

      val bytes: Byte = verifier.getBytes("US-ASCII");
      val md = MessageDigest.getInstance("SHA-256");
      md.update(bytes, 0, bytes.length);
      byte[] digest = md.digest();
//Use Apache "Commons Codec" dependency. Import the Base64 class
//import org.apache.commons.codec.binary.Base64;
      String challenge = Base64.encodeBase64URLSafeString(digest);*/

    }

    override fun onResume(){
        super.onResume()

        val btn = findViewById<Button>(R.id.chromeBtn)

        btn.setOnClickListener {
            mCustomTabsServiceConnection = object : CustomTabsServiceConnection() {
                override fun onCustomTabsServiceConnected(componentName: ComponentName, customTabsClient: CustomTabsClient) {
                    //Pre-warming
                    mClient = customTabsClient
                    mClient?.warmup(0L)
                    mCustomTabsSession = mClient?.newSession(null)

                    //call function and parse tokenisation url
                    loadCustomTabforSite(url = AUTH_URL +
                            "?response_type=token" +
                            "&client_id="+ CLIENT_ID +
                            "&redirect_uri="+ REDIRECT_URL +
                            "&scope=activity%20heartrate" +
                            "&expires_in=604800")
                }

                override fun onServiceDisconnected(name: ComponentName) {
                    mClient = null
                }
            }
            CustomTabsClient.bindCustomTabsService(this, CUSTOM_TAB_PACKAGE_NAME, mCustomTabsServiceConnection);
        }
    }



    fun loadCustomTabforSite(url: String, color: Int = Color.BLUE){
        val customTabsIntent = CustomTabsIntent.Builder(mCustomTabsSession)
                .setToolbarColor(color)
                .setShowTitle(true)
                .build()

        customTabsIntent.launchUrl(this, Uri.parse(url))

    }
}