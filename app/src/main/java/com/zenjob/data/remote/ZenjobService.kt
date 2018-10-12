package com.zenjob.data.remote

import com.zenjob.data.model.LoginRequest
import com.zenjob.data.model.LoginResponse
import com.zenjob.data.model.OffersListResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ZenjobService {

    @POST("/api/employee/v1/auth")
    fun postLogin(
            @Body request: LoginRequest
    ): Observable<LoginResponse>

    @GET("/api/employee/v1/offers?offset=0")
    fun getOffers(): Observable<OffersListResponse>
}