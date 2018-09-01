package com.uop.fithr

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_notification.*
import android.content.Intent
import android.net.Uri


class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

//        var notified = Notifier()

        val extras = intent.extras
        val notificationText = extras.getString("notificationText")

        if (notificationText == getString(R.string.lowHr_text)){
            msg?.setText(getString(R.string.make_call))
            yes.setOnClickListener(object :View.OnClickListener{
                override fun onClick(v: View?) {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:07518945846")
                    startActivity(intent)
                }
            })
            no.setOnClickListener(object :View.OnClickListener{
                override fun onClick(v: View?) {
                    Toast.makeText(applicationContext, getString(R.string.no_make_call), Toast.LENGTH_SHORT).show()

                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }
            })

        }
        else if( notificationText == getString(R.string.highHr_text)){
            msg?.setText(getString(R.string.exercising_activity))

            yes.setOnClickListener(object :View.OnClickListener{
                override fun onClick(v: View?) {
                    Toast.makeText(applicationContext, getString(R.string.yes_exercising), Toast.LENGTH_SHORT).show()
                }
            })
            no.setOnClickListener(object :View.OnClickListener{
                override fun onClick(v: View?) {
                    msg?.setText(getString(R.string.make_call))

                    yes.setOnClickListener(object :View.OnClickListener{
                        override fun onClick(v: View?) {
                            val intent = Intent(Intent.ACTION_DIAL)
                            intent.data = Uri.parse("tel:07518945846")
                            startActivity(intent)
                        }
                    })
                    no.setOnClickListener(object :View.OnClickListener{
                        override fun onClick(v: View?) {
                            Toast.makeText(applicationContext, getString(R.string.no_make_call), Toast.LENGTH_SHORT).show()

                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                        }
                    })

                }
            })
        }

    }
}



// class Notifier: NotificationListenerService(){
//
//    override fun onCreate() {
//        super.onCreate()
//      var  context = applicationContext
//    }
//
//    override fun onNotificationPosted(sbn: StatusBarNotification) {
//        // We can read notification while posted.
//        for (sbm in this@Notifier.getActiveNotifications()) {
//            val title = sbm.getNotification().extras.getString("android.title")
//            val text = sbm.getNotification().extras.getString("android.text")
//            val package_name = sbm.getPackageName()
//            Log.v("Notification title is:", title)
//            Log.v("Notification text is:", text)
//            Log.v("Package Name:", package_name)
//        }
//    }
//}
