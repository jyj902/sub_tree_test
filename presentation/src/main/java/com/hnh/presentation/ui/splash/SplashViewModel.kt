package com.hnh.presentation.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hnh.domain.entity.Version
import com.hnh.domain.usecase.GetVersionUseCase
import com.hnh.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by hyerim on 2021/09/17...
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getVersionUseCase: GetVersionUseCase,
) : BaseViewModel() {

    private val _version = MutableLiveData<Version>()
    val version: LiveData<Version> = _version

    fun getVersion() {
        getVersionUseCase(viewModelScope) {
            _version.value = it
        }
    }
}