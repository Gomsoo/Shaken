package com.gomsoo.shaken.core.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val dispatcher: ShakenDispatchers)

enum class ShakenDispatchers {
    Default,
    IO
}
