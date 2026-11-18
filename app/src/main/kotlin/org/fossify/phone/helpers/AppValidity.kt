package org.fossify.phone.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import org.fossify.phone.activities.AppExpiredActivity
import java.time.LocalDate

object AppValidity {
    private val lastValidDay: LocalDate = LocalDate.of(2026, 12, 31)

    fun isExpired(today: LocalDate = LocalDate.now()): Boolean {
        return today.isAfter(lastValidDay)
    }
}

fun Context.openExpiredScreen() {
    if (this is AppExpiredActivity) {
        return
    }
    startActivity(Intent(this, AppExpiredActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    })
}

fun Activity.showExpiredScreen() {
    if (this is AppExpiredActivity || isFinishing) {
        return
    }
    openExpiredScreen()
    finish()
}
