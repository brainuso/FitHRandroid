package com.uop.fithr

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import kotlinx.android.synthetic.main.tab_today.*

// class for manipulating tab_today xml

public class TabToday  : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.tab_today, container, false)

        val lineChart = rootView.findViewById<LineChart>(R.id.line_chart)


       setLineChart(lineChart)
        return rootView
    }

    private fun setLineChart(lineChart: LineChart){

        val entries = ArrayList<Entry>()
        entries.add( Entry(1f, 0))
        entries.add(Entry(8f, 1))
        entries.add( Entry(6f, 2))
        entries.add( Entry(2f, 3))
        entries.add( Entry(18f, 4))
        entries.add( Entry(9f, 5))


        val dataSet = LineDataSet(entries, "Heart rate")
        val labels = ArrayList<String>()

        for (i in 1..6){
            labels.add("${i}")
        }
    //Dispays Label of the xAxis on the bottom
        val xAxis: XAxis = lineChart.xAxis
        xAxis.setAxisMinValue(0f)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        val yAxis: YAxis = lineChart.axisLeft
        yAxis.setAxisMinValue(0f)
        yAxis.setDrawGridLines(false)
        yAxis.setDrawZeroLine(true)

       xAxis.position = XAxis.XAxisPosition.BOTTOM

        val data = LineData(labels, dataSet)
        try {
            lineChart.data = data
        }
        catch (e: Exception){
            Log.e("error",e.message)
        }


        //should be setDrawCubic but then the plugin is a diff version. We'd test later
        dataSet.setDrawCubic(true)
        dataSet.setDrawFilled(true)
    }

}