package com.hnh.presentation.ui.home.report

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hnh.presentation.R
import com.hnh.presentation.databinding.FragmentReportBinding
import com.hnh.presentation.ui.home.webview.WebViewActivity
import dagger.hilt.android.AndroidEntryPoint
import android.graphics.Color
import android.graphics.Typeface

import com.github.mikephil.charting.data.PieData

import java.util.ArrayList

import com.github.mikephil.charting.data.PieDataSet

import com.github.mikephil.charting.charts.PieChart

import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.hnh.presentation.util.layout.CustomPieChartRenderer
import com.hnh.presentation.util.PreferenceUtil
import java.text.NumberFormat


@AndroidEntryPoint
class ReportFragment : Fragment(R.layout.fragment_report) {
    private var _binding: FragmentReportBinding? = null
    private val binding
        get() = _binding!!


    val viewModel: ReportViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentReportBinding.bind(view)

        val userUid = PreferenceUtil.preferenceInstance(requireContext()).userUid!!
        viewModel.getTotalRecord(userUid)

        init()
        subscribeToLiveData()
    }

    private fun init() {
        binding.buttonGoReport.setOnClickListener {
            val intent = Intent(context, WebViewActivity::class.java)
            startActivity(intent)
        }
    }

    private fun subscribeToLiveData() {
        viewModel.totalRecord.observe(this) {
            if (it == null) {
                binding.textViewEmpty.visibility = View.VISIBLE
                binding.chartExerciseTime.visibility = View.GONE
                binding.chartExerciseCount.visibility = View.GONE
            } else {
                binding.textViewEmpty.visibility = View.GONE
                binding.chartExerciseTime.visibility = View.VISIBLE
                binding.chartExerciseCount.visibility = View.VISIBLE

                val yValuesTime: ArrayList<PieEntry> = ArrayList()
                with(yValuesTime) {
                    add(PieEntry(it.totalRunningTime.toFloat(), parseTime(it.totalRunningTime) + " 운동"))
                    add(PieEntry(it.totalRestTime.toFloat(), parseTime(it.totalRestTime) + " 휴식"))
                }

                val yValuesCount: ArrayList<PieEntry> = ArrayList()
                with(yValuesCount) {
                    add(PieEntry(it.totalSuccessCount.toFloat(), "성공  ${it.totalSuccessCount}회"))
                    add(PieEntry(it.totalFailCount.toFloat(), "실패 ${it.totalFailCount}회"))
                }

                makeChart(binding.chartExerciseTime, yValuesTime, "운동/휴식")
                makeChart(binding.chartExerciseCount, yValuesCount, "성공/실패")

            }

        }
    }

    private fun parseTime(totalRunningTime: Long): String {
        if (totalRunningTime >= 60) {
            val mm = totalRunningTime / 60
            val ss = totalRunningTime % 60
            return "${mm}분 ${ss}초"
        }
        return "${totalRunningTime}초"
    }

    private fun makeChart(chartExercise: PieChart, yValues: ArrayList<PieEntry>, title: String) {
        val colors = listOf(Color.parseColor("#FFB3B3"), Color.parseColor("#765655"))

        chartExercise.apply {
            setExtraOffsets(30f, 10f, 30f, 15f)
            renderer = CustomPieChartRenderer(chartExercise, 10f)
            setUsePercentValues(true)

            val dataSet = PieDataSet(yValues, "")
            dataSet.apply {
                this.colors = colors
                setValueTextColors(colors)
                //Value lines
                valueLinePart1Length = 0.6f
                valueLinePart2Length = 0.3f
                valueLineWidth = 2f
                valueLinePart1OffsetPercentage = 115f
                isUsingSliceColorAsValueLineColor = true

                //Value text appearance
                yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
                valueTextSize = 15f
                valueTypeface = Typeface.DEFAULT_BOLD

                valueFormatter = object : ValueFormatter() {
                    private val formatter = NumberFormat.getPercentInstance()

                    override fun getFormattedValue(value: Float) =
                        formatter.format(value / 100f)
                }
                sliceSpace = 3f
                selectionShift = 3f
            }

            // HOLE
            isDrawHoleEnabled = true
            holeRadius = 50f

            // Center text
            setDrawCenterText(true)
            setCenterTextSize(16f)
            setCenterTextTypeface(Typeface.DEFAULT_BOLD)
            setCenterTextColor(context.getColor(R.color.primaryTextColor))
            centerText = title

            //Disable legend & description
            legend.isEnabled = false
            description = null
            data = PieData(dataSet)

            invalidate()
            refreshDrawableState()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}