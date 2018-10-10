package com.zenjob.ui.home.offers

import android.arch.lifecycle.ViewModel
import com.zenjob.data.local.PrefsHelper
import com.zenjob.data.remote.ZenjobService
import javax.inject.Inject

class OffersViewModel @Inject constructor(private val webService: ZenjobService, private val prefsHelper: PrefsHelper) : ViewModel() {


}