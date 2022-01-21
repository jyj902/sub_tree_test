package com.hnh.presentation.ui.splash

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hnh.presentation.R
import com.hnh.presentation.base.BaseActivity
import com.hnh.presentation.databinding.ActivitySplashBinding
import com.hnh.presentation.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by hye rim on 2021/09/17...
 */

@AndroidEntryPoint
class SplashActivity :
    BaseActivity<ActivitySplashBinding, SplashViewModel>(R.layout.activity_splash) {
    override val viewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        subscribeToLiveData()
    }

    private fun init() {
        lifecycleScope.launch {
            delay(500)
            viewModel.getVersion()
        }
    }

    private fun subscribeToLiveData() {
        viewModel.version.observe(this) {
            val appVersionName = packageManager.getPackageInfo(packageName, 0).versionName
            if (it == null) {
                makeDialogFailServer()
            }
            when {
                it.version != appVersionName && it!!.isRequired -> makeDialogRequired()
                it.version != appVersionName && !it!!.isRequired -> makeDialog()
                else -> goToLoginActivity()
            }
        }
    }

    private fun makeDialogFailServer() {
        MaterialAlertDialogBuilder(this)
            .setMessage(getString(R.string.dialog_server_network))
            .setPositiveButton(getString(R.string.all_ok)) { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .setCancelable(false)
            .show()
    }

    private fun makeDialogRequired() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.dialog_version_title))
            .setMessage(getString(R.string.dialog_version_title))
            .setPositiveButton(getString(R.string.dialog_version_update_button)) { dialog, _ ->
                dialog.dismiss()
                goToPlayStore()
            }
            .setCancelable(false)
            .show()
    }

    private fun makeDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.dialog_version_title))
            .setMessage(getString(R.string.dialog_version_description))
            .setPositiveButton(getString(R.string.dialog_version_update_button)) { dialog, _ ->
                dialog.dismiss()
                goToPlayStore()
            }
            .setNegativeButton(getString(R.string.all_no)) { dialog, _ ->
                dialog.dismiss()
                goToPlayStore()

            }
            .setCancelable(false)
            .show()
    }

    private fun goToLoginActivity() {
        lifecycleScope.launch {
            delay(1000)
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        }
    }

    private fun goToPlayStore() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(
                "https://play.google.com/store/apps/details?id=com.nhn.android.search"
            )
            setPackage("com.android.vending")
        }
        startActivity(intent)
        finish()
    }
}