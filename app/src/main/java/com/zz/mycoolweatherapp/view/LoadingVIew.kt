package com.zz.mycoolweatherapp.view

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.zz.mycoolweatherapp.R

class Loadingview(context: Context?,themeId : Int) : ProgressDialog(context,themeId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(context)
    }

    private fun init(context: Context) {
        setCancelable(true)
        setCanceledOnTouchOutside(false)
        setContentView(R.layout.loading) //loading的xml文件
        val params = window!!.attributes
        params.width = WindowManager.LayoutParams.WRAP_CONTENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes = params
        window!!.setDimAmount(0f)
    }

}