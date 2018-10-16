package com.zenjob.data.remote

import com.zenjob.data.model.*
import io.reactivex.Observable
import retrofit2.http.*

interface ZenjobService {

    @POST("/api/employee/v1/auth")
    fun postLogin(
            @Body request: LoginRequest
    ): Observable<LoginResponse>

    @GET("/api/employee/v1/offers?offset=0")
    fun getOffers(): Observable<OffersListResponse>

    @GET("/api/employee/v1/offers/{id}")
    fun getOfferDetails(@Path("id") offerId: String?): Observable<OffersItem>

    @GET("/api/employee/v1/data/declineReasons")
    fun getOfferReasons(): Observable<OfferDeclineReasonsResponse>

    @HTTP(method = "DELETE", path = "/api/employee/v1/offers/{id}", hasBody = true)
    fun declineOffer(@Path("id") offerId: String?,
                     @Body request: OfferDeclineRequest
    ): Observable<OfferDeclineResponse>
}