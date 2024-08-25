package com.gomsoo.shaken.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

open class BaseViewModel : ViewModel() {
    protected fun <T> Flow<T>.stateIn(
        initialValue: T,
        scope: CoroutineScope = viewModelScope,
        started: SharingStarted = SharingStarted.WhileSubscribed(5_000),
    ): StateFlow<T> = stateIn(scope, started, initialValue)
}
