package com.zenjob.ui.home.offers

import android.databinding.ObservableField
import com.zenjob.data.model.OffersItem
import com.zenjob.utils.AppConstants.Companion.EMPTY
import java.util.*

class OfferItemViewModel(offerItem: OffersItem?) {

    val offerCategory = ObservableField<String>(EMPTY)
    val offerTitle = ObservableField<String>(EMPTY)
    val offerEarning = ObservableField<String>(EMPTY)
    val offerEarningHourly = ObservableField<String>(EMPTY)
    val offerLocation = ObservableField<String>(EMPTY)
    val offerBeginDate = ObservableField<String>(EMPTY)
    val offerEndDate = ObservableField<String>(EMPTY)

    init {
        offerCategory.set(offerItem?.jobCategoryKey?.toUpperCase(Locale.getDefault()))
        offerTitle.set(offerItem?.title)
        offerEarning.set(offerItem?.earningTotal)
        offerEarningHourly.set(offerItem?.earningHourly)
        offerLocation.set(offerItem?.location?.locationName)
        offerBeginDate.set(offerItem?.shifts?.firstOrNull()?.beginDate)
        offerEndDate.set(offerItem?.shifts?.firstOrNull()?.endDate)
    }

}