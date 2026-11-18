package org.fossify.phone.activities

import android.content.Intent
import org.fossify.commons.activities.BaseSplashActivity
import org.fossify.phone.helpers.AppValidity
import org.fossify.phone.helpers.showExpiredScreen

class SplashActivity : BaseSplashActivity() {
    override fun initActivity() {
        if (AppValidity.isExpired()) {
            showExpiredScreen()
            return
        }
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
