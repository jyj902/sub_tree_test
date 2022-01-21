package com.hnh.presentation.progressdialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.hnh.presentation.R

/**
 * Created by hyerim on 9/14/21...
 */
class ProgressDialog(context: Context, message: String) {
    companion object {
        private const val TAG = "hnh ProgressDialog"
    }

    private val progressDialog: Dialog =
        AlertDialog.Builder(context, R.style.Theme_Hnh_AlertDialog).apply {
            val view = LayoutInflater.from(context).inflate(R.layout.layout_progressbar, null)
            setCancelable(false)
            setView(view)

            val progressMessage = view.findViewById<TextView>(R.id.textview_progress)
            progressMessage.text = message
        }.create()

    fun show() {
        progressDialog.show()
    }

    fun hide() {
        progressDialog.hide()
    }

    fun dismiss() {
        progressDialog.dismiss()
    }
}