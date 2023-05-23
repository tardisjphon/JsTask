package js.task.data.remote.retrofit.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.*
import android.os.Build
import androidx.annotation.RequiresApi

class NetworkStatus(context : Context)
{
    private var mConnectivityManager : ConnectivityManager? = null
    val noNetworkMessage = "no network"

    init
    {
        mConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun isLowerAPIThanM() : Boolean
    {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
    }

    fun isOnline() : Boolean
    {
        return if (isLowerAPIThanM()) isOnlineBelowAPI23() else isOnlineFromAPI23()
    }

    @Suppress("DEPRECATION")
    private fun isOnlineBelowAPI23() : Boolean
    {
        val cm = mConnectivityManager ?: return false
        return cm.activeNetworkInfo?.isConnectedOrConnecting == true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isOnlineFromAPI23() : Boolean
    {
        val cm = mConnectivityManager ?: return false
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) && capabilities.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_VALIDATED
        )
    }
}