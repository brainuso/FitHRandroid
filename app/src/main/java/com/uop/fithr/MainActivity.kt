package com.uop.fithr

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu)
    }
    */
    fun launchProfileActivity(view: View){
          val intent = Intent(this, ProfileActivity::class.java)

          startActivity(intent)
      }

   fun launchMain3Activity(view: View){
    val intent = Intent(this, Main3Activity::class.java)

    startActivity(intent)
}

    /*fun launchSettingsActivity(view: View){
        val intent = Intent(this, SettingsActivity::class.java)

        startActivity(intent)
    }*/
}
