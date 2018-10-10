package com.zenjob.utils

import android.databinding.BindingAdapter
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.zenjob.R
import com.zenjob.data.remote.NetworkState
import com.zenjob.utils.validation.ValidationRule
import com.zenjob.utils.validation.ValidationUtils


@BindingAdapter("app:glideProfileImageUri")
fun setGlideProfileImageUri(imv: ImageView, url: String) {
    val dimension = imv.context.resources.getDimension(R.dimen.radius_card_corner)
    val transforms = RequestOptions()
            .transforms(CenterInside(), RoundedCorners(dimension.toInt()))
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher_round)
    Glide.with(imv.context.applicationContext)
            .load(url)
            .apply(transforms)
            .into(imv)
}

@BindingAdapter("app:glideBannerImageUri")
fun setGlideBannerImageUri(imv: ImageView, url: String) {
    val transforms = RequestOptions()
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
    Glide.with(imv.context.applicationContext)
            .load(url)
            .apply(transforms)
            .into(imv)
}

@BindingAdapter(value = ["app:error", "app:rule", "android:watcher"], requireAll = true)
fun watcher(textInputLayout: TextInputLayout, errorMsg: String, rule: ValidationRule, watcher: TextWatcher) {
    textInputLayout.editText?.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (rule == ValidationRule.EMPTY) {
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
    } else {
        if (state == NetworkState.LOADING) {
            view.visibility = View.GONE

        } else {
            view.visibility = View.VISIBLE
        }
    }
}
