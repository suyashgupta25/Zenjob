package com.zenjob.data.model

import android.os.Parcel
import android.os.Parcelable
import se.ansman.kotshi.JsonSerializable
import se.ansman.kotshi.KotshiConstructor

@JsonSerializable
data class OfferDeclineResponse @KotshiConstructor constructor(
        val id: String? = null,
        val reason: String? = null,
        val reasonComment: String? = null
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(reason)
        writeString(reasonComment)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<OfferDeclineResponse> = object : Parcelable.Creator<OfferDeclineResponse> {
            override fun createFromParcel(source: Parcel): OfferDeclineResponse = OfferDeclineResponse(source)
            override fun newArray(size: Int): Array<OfferDeclineResponse?> = arrayOfNulls(size)
        }
    }
}
