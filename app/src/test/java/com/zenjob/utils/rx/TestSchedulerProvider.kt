package com.zenjob.utils.rx

import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler

class TestSchedulerProvider(private val mTestScheduler: TestScheduler) : SchedulerProvider {

    override fun io(): Scheduler {
        return mTestScheduler
    }

    override fun ui(): Scheduler {
        return mTestScheduler
    }
}