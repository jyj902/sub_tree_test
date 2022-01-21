package com.hnh.presentation.ui.login

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hnh.presentation.BuildConfig
import com.hnh.presentation.R
import com.hnh.presentation.base.BaseActivity
import com.hnh.presentation.databinding.ActivityLoginBinding
import com.hnh.presentation.ui.MainActivity
import com.hnh.presentation.util.PreferenceUtil
import com.hnh.presentation.util.SOCIAL_TYPE
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {
    private var isUser = false
    override val viewModel: LoginViewModel by viewModels()

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        subscribeToLiveData()
        initGoogleLogin()
    }

    private fun initViewModel() {
        binding.buttonLoginGoogle.setOnClickListener {
            checkTokenGoogle()
        }
        binding.buttonLoginKakao.setOnClickListener {
            checkTokenKakao()
        }


        if (BuildConfig.IS_DEBUG) {
            binding.buttonLoginDeveloper.visibility = View.VISIBLE
            binding.buttonLoginDeveloper.setOnClickListener {
                goMain()
            }
        } else {
            binding.buttonLoginDeveloper.visibility = View.GONE
        }

    }

    private fun subscribeToLiveData() {
        viewModel.user.observe(this) {
            when {
                it == null -> {
                    makeDialogFailLogin()
                    Log.d(TAG, "user null")
                }
                it!!.id == 10000L -> {
                    makeDialogNotFound()
                }
                it!!.isActivated -> {
                    PreferenceUtil.preferenceInstance(this).isToken = true
                    PreferenceUtil.preferenceInstance(this).authToken = it.token
                    PreferenceUtil.preferenceInstance(this).socialType = it.socialType
                    PreferenceUtil.preferenceInstance(this).userUid = it.id
                    PreferenceUtil.preferenceInstance(this).userName = it.name
                    goMain()
                }
                else -> {
                    makeDialogNotActivate()
                    Log.d(TAG, "권한 없음")
                }
            }
        }
    }

    private fun initGoogleLogin() {
        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(BuildConfig.GOOGLE_CLIENT_ID)
                    // Show all accounts on the device.
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
    }

    private fun checkTokenGoogle() {
        if (PreferenceUtil.preferenceInstance(this).isToken) {
            var token = PreferenceUtil.preferenceInstance(this).authToken
            var type = PreferenceUtil.preferenceInstance(this).socialType
            if (token != null && type == SOCIAL_TYPE.GOOGLE) {
                viewModel.getUser(token, type)
            } else {
                savePrefFailData()
                Toast.makeText(
                    this,
                    getString(R.string.dialog_login_retry_google),
                    Toast.LENGTH_SHORT
                ).show()
                loginGoogle()
            }
        } else {
            savePrefFailData()
            loginGoogle()
        }
    }


    private fun loginGoogle() {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(this) { result ->
                try {
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    googleLoginIntentResultLauncher.launch(intentSenderRequest)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e(TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
                    makeDialogFailLogin()
                }
            }
            .addOnFailureListener(this) { e ->
                // No saved credentials found. Launch the One Tap sign-up flow, or
                // do nothing and continue presenting the signed-out UI.
                makeDialogFailLogin()
                Log.d(TAG, e.localizedMessage)
            }
    }

    private val googleLoginIntentResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result != null) {
                val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                val idToken = credential.googleIdToken
                when {
                    idToken != null -> {
                        // Got an ID token from Google. Use it to authenticate
                        // with your backend.
                        PreferenceUtil.preferenceInstance(this).isToken = true
                        PreferenceUtil.preferenceInstance(this).authToken = idToken
                        PreferenceUtil.preferenceInstance(this).socialType = SOCIAL_TYPE.GOOGLE
                        viewModel.getUser(idToken, SOCIAL_TYPE.GOOGLE)
                        Log.d(TAG, "Got ID token.")
                    }
                    else -> {
                        // Shouldn't happen.
                        Log.d(TAG, "No ID token!")
                        makeDialogFailLogin()
                    }
                }
            }
        }

    /**
     * TODO : preference로 이용하는 부분 repository로 이동 필요
     * 토큰 확인 메소드
     * 1. 토큰이 있을 경우
     *  1-1. 토큰 정보와, 가입 사이트 정보가 null이 아닌 경우 정보 가져와서 로그인 API 호출
     *  1-2. 토큰 정보와, 가입 사이트 정보가 null인 경우 재로그인 안내 및 preference 초기화 -> 카카오 로그인
     * 2. 토큰이 없을 경우 preference 초기화 -> 카카오 로그인
     */
    private fun checkTokenKakao() {
        if (PreferenceUtil.preferenceInstance(this).isToken) {
            var token = PreferenceUtil.preferenceInstance(this).authToken
            var type = PreferenceUtil.preferenceInstance(this).socialType
            if (token != null && type == SOCIAL_TYPE.KAKAO) {
                viewModel.getUser(token, type)
            } else {
                savePrefFailData()
                Toast.makeText(
                    this,
                    getString(R.string.dialog_login_retry_kakao),
                    Toast.LENGTH_SHORT
                ).show()
                loginKakao()
            }
        } else {
            savePrefFailData()
            loginKakao()
        }
    }

    //TODO:구글/네이버 로그인으로 TYPE 변경 필요
    private fun loginKakao() {
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    // 로그인 공통 callback 구성
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            makeDialogFailLogin()
            Log.e(TAG, "로그인 실패", error)
        } else if (token != null) {
            PreferenceUtil.preferenceInstance(this).isToken = true
            PreferenceUtil.preferenceInstance(this).authToken = token.accessToken
            PreferenceUtil.preferenceInstance(this).socialType = SOCIAL_TYPE.KAKAO
            viewModel.getUser(token.accessToken, SOCIAL_TYPE.KAKAO)
            Log.i(TAG, "로그인 성공 ${token.accessToken}")
        }
    }

    private fun goMain() {
        //TODO : Debug 모드인 경우 바로 메인화면으로 이동
//            || BuildConfig.IS_DEBUG
//        if (isUser) {
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
//        } else {
//            showLoginFailed()
//        }
    }

    private fun savePrefFailData() {
        PreferenceUtil.preferenceInstance(this).isToken = false
        PreferenceUtil.preferenceInstance(this).authToken = null
        PreferenceUtil.preferenceInstance(this).socialType = null
        PreferenceUtil.preferenceInstance(this).userUid = 0
        PreferenceUtil.preferenceInstance(this).userName = null
    }

    private fun makeDialogNotActivate() {
        MaterialAlertDialogBuilder(this)
            .setMessage(getString(R.string.dialog_login_social_not_activate))
            .setPositiveButton(getString(R.string.all_ok)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun makeDialogFailLogin() {
        MaterialAlertDialogBuilder(this)
            .setMessage(getString(R.string.dialog_login_social_fail))
            .setPositiveButton(getString(R.string.all_ok)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun makeDialogNotFound() {
        MaterialAlertDialogBuilder(this)
            .setMessage(getString(R.string.dialog_login_hnh_not_found))
            .setPositiveButton(getString(R.string.all_ok)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    companion object {
        const val TAG = "LoginActivity"
    }

}