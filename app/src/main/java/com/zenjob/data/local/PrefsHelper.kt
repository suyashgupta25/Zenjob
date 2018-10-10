package com.zenjob.data.local

import com.zenjob.data.model.LoginResponse

interface PrefsHelper {

    fun saveUserAuth(response: LoginResponse)
}