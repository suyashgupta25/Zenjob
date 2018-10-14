package com.zenjob.ui.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.zenjob.R
import com.zenjob.databinding.ActivityHomeBinding
import com.zenjob.ui.common.base.BaseActivity
import com.zenjob.ui.home.offerdetails.OfferDetailsFragment
import com.zenjob.ui.home.offers.OffersFragment
import com.zenjob.utils.ext.replaceFragment

class HomeActivity : BaseActivity(), FragmentManager.OnBackStackChangedListener {


    val binding: ActivityHomeBinding by lazy {
        DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentContainerId = binding.flHomeContent.id
        supportFragmentManager.addOnBackStackChangedListener(this)
        replaceFragment(fragmentContainerId, ::OffersFragment)
    }

    override fun onBackStackChanged() {
        val findFragmentById = supportFragmentManager.findFragmentById(R.id.fl_home_content)
        if (findFragmentById is OffersFragment) {
            updateStatusBarColor(R.color.colorPrimaryDark)
        } else if (findFragmentById is OfferDetailsFragment) {
            updateStatusBarColor(R.color.colorOfferTitleDark)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.removeOnBackStackChangedListener(this)
    }

}