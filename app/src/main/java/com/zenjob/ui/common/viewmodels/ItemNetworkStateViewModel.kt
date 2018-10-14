package com.zenjob.ui.common.viewmodels

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.zenjob.data.remote.NetworkState
import com.zenjob.data.remote.Status
import com.zenjob.utils.AppConstants.Companion.EMPTY

class ItemNetworkStateViewModel(networkState: NetworkState) {

    val showProgress = ObservableBoolean(false)
    val showError = ObservableBoolean(false)
    val errorMsg = ObservableField<String>(EMPTY)

    init {
        if (networkState.status == Status.RUNNING) {
            showProgress.set(true)
        } else {
            showProgress.set(false)
        }
        if (networkState.status == Status.FAILED) {
            showError.set(true)
            errorMsg.set(networkState.msg)
        } else {
            showError.set(false)
            errorMsg.set(EMPTY)
        }
    }
}