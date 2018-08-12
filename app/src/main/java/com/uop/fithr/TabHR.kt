package com.uop.fithr

import android.content.Intent.getIntent
import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// class for manipulating tab_HR xml

public class TabHR : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.tab_hr, container, false)

        val displayHR = rootView.findViewById<TextView>(R.id.HR)




        displayHR.text = person.toString()

        return rootView


    }


}