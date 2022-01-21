package com.hnh.presentation.ui.login

import android.util.Base64
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hnh.domain.entity.User
import com.hnh.domain.usecase.GetUserUseCase
import com.hnh.presentation.base.BaseViewModel
import com.hnh.presentation.util.KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    fun getUser(token: String, type: String) {
        getUserUseCase(token, type, viewModelScope) {
            _user.value = it
        }
    }

    //코드 암호화
    fun isCorrect(text: String): Boolean {
        return text.encryptECB() == KEY.encryptECB()
    }

    private fun String.encryptECB(): String {
        val keySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES") // 키
        val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING") //싸이퍼
        cipher.init(Cipher.ENCRYPT_MODE, keySpec) // 암호화/복호화 모드
        val ciphertext = cipher.doFinal(this.toByteArray())
        val encodedByte = Base64.encode(ciphertext, Base64.DEFAULT)
        return String(encodedByte)
    }

    companion object {
        const val SECRET_KEY = "ABCDEFGH12345678"
        const val SECRET_IV = "1234567812345678"
    }
}