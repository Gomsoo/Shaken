package com.gomsoo.shaken.core.extension

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

fun <T1, T2> Flow<T1>.combine(flow: Flow<T2>): Flow<Pair<T1, T2>> =
    combine(flow) { flow1, flow2 -> flow1 to flow2 }

fun <T1, T2, T3> Flow<T1>.combine(flow: Flow<T2>, flow2: Flow<T3>): Flow<Triple<T1, T2, T3>> =
    combine(this, flow, flow2) { f1, f2, f3 -> Triple(f1, f2, f3) }
