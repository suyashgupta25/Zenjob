package com.zenjob.ui.common.base


import dagger.android.support.DaggerAppCompatActivity

/**
 * Created by suyashg
 *
 * Base class for activity extensibility and re-usability
 */
abstract class BaseActivity : DaggerAppCompatActivity() {
    //common for all activities
    var fragmentContainerId: Int = 0

}