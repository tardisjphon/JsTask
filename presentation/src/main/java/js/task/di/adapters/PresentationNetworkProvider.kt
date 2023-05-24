package js.task.di.adapters

import js.task.data.remote.retrofit.utils.NetworkStatus
import js.task.domain.model.DomainNetworkStatus
import javax.inject.Inject

class PresentationNetworkProvider @Inject constructor(private val networkStatus : NetworkStatus) : DomainNetworkStatus
{
    override fun isOnline() : Boolean
    {
        return networkStatus.isOnline()
    }
}