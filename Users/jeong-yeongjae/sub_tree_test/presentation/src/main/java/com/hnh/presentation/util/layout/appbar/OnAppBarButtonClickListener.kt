package com.hnh.presentation.util.layout.appbar

import android.view.View

/**
 * Created by hyerim on 2021/09/24...
 */
interface OnAppBarButtonClickListener {
    fun onClickViewInLeftContainer(view: View, index: Int)
    fun onClickViewInRightContainer(view: View, index: Int)
}