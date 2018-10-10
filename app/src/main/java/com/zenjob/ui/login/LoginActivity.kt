package com.zenjob.ui.login

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.zenjob.R
import com.zenjob.databinding.ActivityLoginBinding
import com.zenjob.ui.common.base.BaseActivity
import com.zenjob.ui.login.loginscreen.LoginFragment
import com.zenjob.utils.ext.replaceFragment

class LoginActivity : BaseActivity() {

    val binding: ActivityLoginBinding by lazy {
        DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentContainerId = binding.flLoginContent.id
        replaceFragment(fragmentContainerId, ::LoginFragment)
    }

}