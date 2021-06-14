package rk.information.news.utils

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsIntent.COLOR_SCHEME_LIGHT
import androidx.core.content.ContextCompat
import rk.information.news.R


object Utility {

    @JvmStatic
    fun openChromeCustomTab(context: Context, url: String) {
        try {

            val darkParams = CustomTabColorSchemeParams.Builder()
                    .setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                    .setNavigationBarColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .build()

            val builder = CustomTabsIntent.Builder()
            builder.setColorSchemeParams(COLOR_SCHEME_LIGHT, darkParams)
            

            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(context, Uri.parse(url))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}