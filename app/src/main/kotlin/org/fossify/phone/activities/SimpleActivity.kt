package org.fossify.phone.activities

import android.os.Bundle
import org.fossify.commons.activities.BaseSimpleActivity
import org.fossify.phone.R
import org.fossify.phone.helpers.AppValidity
import org.fossify.phone.helpers.showExpiredScreen

open class SimpleActivity : BaseSimpleActivity() {
    protected var appExpired = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appExpired = AppValidity.isExpired()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (appExpired) {
            showExpiredScreen()
        }
    }

    override fun onResume() {
        super.onResume()
        if (AppValidity.isExpired()) {
            appExpired = true
            showExpiredScreen()
        }
    }

    override fun getAppIconIDs() = arrayListOf(
        R.mipmap.ic_launcher_red,
        R.mipmap.ic_launcher_pink,
        R.mipmap.ic_launcher_purple,
        R.mipmap.ic_launcher_deep_purple,
        R.mipmap.ic_launcher_indigo,
        R.mipmap.ic_launcher_blue,
        R.mipmap.ic_launcher_light_blue,
        R.mipmap.ic_launcher_cyan,
        R.mipmap.ic_launcher_teal,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher_light_green,
        R.mipmap.ic_launcher_lime,
        R.mipmap.ic_launcher_yellow,
        R.mipmap.ic_launcher_amber,
        R.mipmap.ic_launcher_orange,
        R.mipmap.ic_launcher_deep_orange,
        R.mipmap.ic_launcher_brown,
        R.mipmap.ic_launcher_blue_grey,
        R.mipmap.ic_launcher_grey_black
    )

    override fun getAppLauncherName() = getString(R.string.app_launcher_name)

    override fun getRepositoryName() = "Phone"
}
