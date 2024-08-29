package com.gomsoo.shaken.core.extension

import kotlin.time.Duration.Companion.seconds

fun Float.secondsToDurationText(): String =
    toInt().seconds.toComponents { minutes, seconds, _ -> "$minutes:$seconds" }
