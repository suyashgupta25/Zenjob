package com.zenjob.utils

/**
 * Created by suyashg
 *
 * If we want to dump error somewhere locally this is the place for it
 */
fun defaultErrorHandler(): (Throwable) -> Unit = { e -> System.err.println(e) }