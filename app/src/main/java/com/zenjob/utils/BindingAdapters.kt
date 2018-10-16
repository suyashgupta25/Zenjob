package com.zenjob.utils

import android.databinding.BindingAdapter
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ProgressBar
import com.zenjob.data.remote.NetworkState
import com.zenjob.utils.validation.ValidationRule
import com.zenjob.utils.validation.ValidationUtils


@BindingAdapter(value = ["app:error", "app:rule", "android:watcher"], requireAll = true)
fun watcher(textInputLayout: TextInputLayout, errorMsg: String, rule: ValidationRule, watcher: TextWatcher) {
    textInputLayout.editText?.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (rule == ValidationRule.EMAIL) {
                if (!ValidationUtils.isValidEmail(p0.toString())) {
                    textInputLayout.error = errorMsg
                } else {
                    textInputLayout.error = null
                }
            }
            if (rule == ValidationRule.PASSWORD) {
                if (!ValidationUtils.isValidPassword(p0.toString())) {
                    textInputLayout.error = errorMsg
                } else {
                    textInputLayout.error = null
                }
            }
            if (rule == ValidationRule.EMPTY) {
                if (p0.isNullOrEmpty()) {
                    textInputLayout.error = errorMsg
                } else {
                    textInputLayout.error = null
                }
            }
        }
    })
}

@BindingAdapter("app:networkStateFlag")
fun setNetworkState(view: View, state: NetworkState?) {
    if (view is ProgressBar) {
        if (state == NetworkState.LOADING) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    } else if (view is TextInputEditText) {
        if (state == NetworkState.LOADING) {
            view.isEnabled = false
        } else {
            view.isEnabled = true
        }
    } else {
        if (state == NetworkState.LOADING) {
            view.visibility = View.GONE

        } else {
            view.visibility = View.VISIBLE
        }
    }
}

@BindingAdapter("android:networkState")
fun setNetworkStateLyt(view: View, state: NetworkState?) {
    if (view is ProgressBar) {
        if (state == NetworkState.LOADING) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    } else {
        if (state == NetworkState.LOADING) {
            view.visibility = View.GONE

        } else {
            view.visibility = View.VISIBLE
        }
    }
}
