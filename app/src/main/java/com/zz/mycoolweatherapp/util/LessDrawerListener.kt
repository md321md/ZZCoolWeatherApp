package com.zz.mycoolweatherapp.util

import android.view.View
import androidx.drawerlayout.widget.DrawerLayout

abstract class LessDrawerListener : DrawerLayout.DrawerListener {
    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
    override fun onDrawerOpened(drawerView: View) {}
    override fun onDrawerClosed(drawerView: View) {}
    override fun onDrawerStateChanged(newState: Int) {}

}