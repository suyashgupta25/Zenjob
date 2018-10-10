package com.zenjob.ui.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.zenjob.R
import com.zenjob.databinding.ActivityHomeBinding
import com.zenjob.ui.common.base.BaseActivity
import com.zenjob.ui.home.offers.OffersFragment
import com.zenjob.utils.ext.replaceFragment

class HomeActivity: BaseActivity() {

    val binding: ActivityHomeBinding by lazy {
        DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentContainerId = binding.flHomeContent.id
        replaceFragment(fragmentContainerId, ::OffersFragment)
    }

}