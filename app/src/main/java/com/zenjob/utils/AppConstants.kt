package com.zenjob.utils

class AppConstants {
    companion object {
        const val EMPTY = ""
        const val ONE = 1

        //Network settings
        const val CONNECTION_TIMEOUT = 10L
        const val READ_TIMEOUT = 30L
        const val WRITE_TIMEOUT = 10L
        const val ERROR_UNAUTHORIZED = 401

        //Validation
        const val MIN_LENGTH_PASSWORD = 5

        //Result transitions
        const val OFFER_DECLINE_DIALOG = 999

    }
}