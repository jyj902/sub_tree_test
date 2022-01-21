package com.hnh.presentation.util

import android.util.Log
import com.hnh.presentation.BuildConfig
import com.opencsv.CSVWriter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.*
import kotlin.collections.ArrayList

class CsvHelper(private val filePath: String) {
    fun writeData(fileName: String, dataList: ArrayList<Array<String>>) {
        try {
            FileWriter(File("$filePath/$fileName")).use { fw ->
                CSVWriter(fw).use {
                    for (data in dataList) {
                        it.writeNext(arrayOf(data.toString()))
                    }
                }
            }
        } catch (e: IOException) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
        }
    }

    //    fun writeDataFloat1(fileName: String, dataList: ArrayList<Array<Float>>){
//        try{
//            DataOutputStream(FileOutputStream("$filePath/$fileName")).use{ dos ->
//                for(data in dataList) {
//                    for (cell in data) {
//                        dos.writeFloat(cell)
//                        dos.writeChars(",")
//                    }
//                    dos.writeChars("\n")
//                }
//            }
//        } catch (e: IOException){
//            if ( BuildConfig.DEBUG){
//                e.printStackTrace()
//            }
//        }
//    }
    fun writeDataFloat(fileName: String, dataList: ArrayList<Array<Float>>) {

        try {
            OutputStreamWriter(
                DataOutputStream(
                    FileOutputStream("$filePath/$fileName")
                ), "MS949"
            ).use {
                for (data in dataList) {
                    var i = 1
                    for (cell in data) {
                        it.write(cell.toString())
                        if(data.size != i){
                            it.write(",")
                        }
                        i += 1
                    }
                    it.write("\n")
                }
                it.close()
            }
            Log.d("testtest", "csv file ok")
        } catch (e: IOException) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
            Log.d("testtest", "csv file faile")
        }
    }


    companion object {

    }
}