package com.uop.fithr

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.android.synthetic.main.tab_statistics.*
import kotlinx.android.synthetic.main.tab_today.*

// class for manipulating tab_Statistics xml
public class TabStatistics  : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.tab_statistics, container, false)

        val barChart = rootView.findViewById<BarChart>(R.id.barChart)
       setBarChart(barChart)

        return rootView
    }

    private fun setBarChart(barChart: BarChart) {

        //val mXIndex = 2f
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(8f, 0))
        entries.add(BarEntry(2f, 1))
        entries.add(BarEntry(5f, 2))
        entries.add(BarEntry(20f, 3))
        entries.add(BarEntry(15f, 4))
        entries.add(BarEntry(19f, 5))

        val barDataSet = BarDataSet(entries, "Cells")

        //Loop to change label values
        val labels = ArrayList<String>()
        for (i in 1..6) {
            labels.add("${i}")
        }

        val data = BarData(labels, barDataSet)

        //Dispays Label of the xAxis on the bottom


        val xAxis: XAxis = barChart.xAxis

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        val yAxis: YAxis = barChart.axisLeft
        yAxis.setAxisMinValue(0f)
        yAxis.setDrawGridLines(false)
        yAxis.setDrawZeroLine(true)

        barChart.data = data // set the data and list of labels into chart

        barChart.setDescription("Heart rate")  // set the description

        //barDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
        barDataSet.color = resources.getColor(R.color.colorAccent)

        barChart.animateY(5000)
    }

    /*  fun setBarChart() {

          //val mXIndex = 2f
          val entries = ArrayList<BarEntry>()
          entries.add(BarEntry(8f, 0f))
          entries.add(BarEntry(2f, 1f))
          entries.add(BarEntry(5f, 2f))
          entries.add(BarEntry(20f, 3f))
          entries.add(BarEntry(15f, 4f))
          entries.add(BarEntry(19f, 5f))

          val barDataSet = BarDataSet(entries, "Cells")

          val labels = ArrayList<String>()
         for (i in 1..6){
              labels.add("${i}")
          }
          //Loop to change label values
          *//*val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(8f, 0))
        entries.add(BarEntry(2f, 1))
        entries.add(BarEntry(5f, 2))
        entries.add(BarEntry(20f, 3))
        entries.add(BarEntry(15f, 4))
        entries.add(BarEntry(19f, 5))

        val labels = ArrayList<String>()
        for (i in 1..6){
            labels.add("${i}")
        }
*//*
        val data = BarData(labels, barDataSet)

        barChart.data = data // set the data and list of labels into chart

        barChart.setDescription("Heart rate")  // set the description

        //barDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
        barDataSet.color = resources.getColor(R.color.colorAccent)

        barChart.animateY(5000)
    }*/
}