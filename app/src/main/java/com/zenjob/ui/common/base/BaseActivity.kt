package com.zenjob.ui.common.base


import android.os.Build
import android.support.v4.content.ContextCompat
import android.view.WindowManager
import dagger.android.support.DaggerAppCompatActivity


/**
 * Created by suyashg
 *
 * Base class for activity extensibility and re-usability
 */
abstract class BaseActivity : DaggerAppCompatActivity() {
    //common for all activities
    var fragmentContainerId: Int = 0

    fun updateStatusBarColor(color: Int) {// Color must be in hexadecimal format
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, color)
        }
    }

}