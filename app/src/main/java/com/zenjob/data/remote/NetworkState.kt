package com.zenjob.data.remote

class NetworkState(val status: Status, val msg: String) {
    companion object {

        val LOADED: NetworkState
        val LOADING: NetworkState

        init {
            LOADED = NetworkState(Status.SUCCESS, NetworkState.MSG_SUCCESS)
            LOADING = NetworkState(Status.RUNNING, NetworkState.MSG_RUNNING)
        }

        val MSG_SUCCESS = "Success"
        val MSG_FAILURE = "Can not connect now. We regret the inconvenience caused."
        val MSG_RUNNING = "Running"
    }

}
