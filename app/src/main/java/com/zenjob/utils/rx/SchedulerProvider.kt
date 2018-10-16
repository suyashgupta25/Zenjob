package com.zenjob.utils.rx

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun io(): Scheduler

    fun ui(): Scheduler
}