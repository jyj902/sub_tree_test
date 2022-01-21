package com.hnh.presentation.progressdialog

/**
 * Created by hyerim on 9/14/21...
 */
interface ProgressDialogInterface {
    fun showProgressDialog()
    fun hideProgressDialog()
}

fun ProgressDialogInterface.changeProgressState(isShowing: Boolean) {
    if (isShowing) showProgressDialog()
    else hideProgressDialog()
}