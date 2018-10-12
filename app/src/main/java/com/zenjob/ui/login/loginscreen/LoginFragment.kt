package com.zenjob.ui.login.loginscreen

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zenjob.R
import com.zenjob.data.remote.Status
import com.zenjob.databinding.FragmentLoginBinding
import com.zenjob.ui.common.dialogs.ErrorDialog
import com.zenjob.ui.home.HomeActivity
import com.zenjob.utils.ext.hideKeyboard
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class LoginFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var errorDialog: ErrorDialog

    val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding(view)
        addObservers()
    }

    private fun initBinding(view: View) {
        val binding = DataBindingUtil.bind<FragmentLoginBinding>(view)
        binding.let {
            it!!.viewModel = viewModel
            it.setLifecycleOwner(this)
            initBindingVariable(it)
        }
    }

    private fun initBindingVariable(binding: FragmentLoginBinding) {
        binding.watcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        }
    }

    private fun addObservers() {
        viewModel.networkState.observe(this, Observer {
            if (it?.status == Status.SUCCESS) {
                goToHomeScreen()
            } else if (it?.status == Status.FAILED) {
                errorDialog.showDialog(activity, it.msg)
            } else if (it?.status == Status.RUNNING) {
                activity?.hideKeyboard(activity)
            }
        })
    }

    private fun goToHomeScreen() {
        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (viewModel.checkForAppSession()) goToHomeScreen()
    }

    override fun onResume() {
        super.onResume()
        viewModel.dummy()
    }

}