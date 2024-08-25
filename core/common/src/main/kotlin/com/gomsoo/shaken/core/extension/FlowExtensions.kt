package com.gomsoo.shaken.core.extension

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

fun <T1, T2> Flow<T1>.combine(flow: Flow<T2>): Flow<Pair<T1, T2>> =
    combine(flow) { flow1, flow2 -> flow1 to flow2 }
