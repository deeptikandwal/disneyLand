package com.disneyland.presentation.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<vs : ViewState, vi : ViewIntent, se : SideEffect> : ViewModel() {
    abstract fun sendIntent(intent: ViewIntent)
    abstract fun navigate(sideEffect: SideEffect)
}