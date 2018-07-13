/*
package com.uop.fithr

import android.app.ActionBar
import android.app.Fragment
import android.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.System.putInt
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class Main2Activity : FragmentActivity() {

    private lateinit var TabPagerAdapter: MyPagerAdapter
    private lateinit var mViewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        TabPagerAdapter = MyPagerAdapter(supportFragmentManager)
        mViewPager = findViewById(R.id.pager)
        mViewPager.adapter = TabPagerAdapter

        actionBar.navigationMode = ActionBar.NAVIGATION_MODE_TABS{

            val tabListener = object: ActionBar.TabListener{

                override fun onTabSelected(tab: ActionBar.Tab?, ft: FragmentTransaction) {

                    mViewPager.currentItem = tab.position
                }

                override fun onTabUnselected(tab: ActionBar.Tab?, ft: FragmentTransaction) {

                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onTabReselected(tab: ActionBar.Tab?, ft: FragmentTransaction) {

                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            }

            for(i in 0 until 3){
                actionBar.addTab(actionBar.newTab().setText("Tab" + (i + 1))
                                .setTabListener(tabListener))
            }
        }
    }
}

class MyPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm){
    override fun getCount(): Int = 10

    override fun getItem(i: Int): android.support.v4.app.Fragment {
        val fragment = DemoObjectFragment()
        fragment.arguments = Bundle().apply{
            putInt(ARG_OBJECT, i + 1)
        }
        return fragment
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return "OBJECT" + (position + 1)
    }
}

private const val ARG_OBJECT = "object"
// Instances of this class are fragments representing a single
// object in our collection.

class DemoObjectFragment: android.support.v4.app.Fragment(){
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       val rootView: ViewGroup = inflater.inflate(
               R.layout.fragment_collection_object, container, false)
       arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
           val textView: TextView = rootView.findViewById(R.id.text1)
           textView.text = getInt(ARG_OBJECT).toString()
       }

        return rootView
    }
}*/
