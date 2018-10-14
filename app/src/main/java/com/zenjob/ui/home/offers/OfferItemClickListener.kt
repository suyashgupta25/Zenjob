package com.zenjob.ui.home.offers

import android.view.View

interface OfferItemClickListener {

    fun onSeeDetailsClick(view: View, position: Int)

    fun onOfferDeclineClick(view: View, position: Int)

    fun onRetryClick(position: Int)

}
