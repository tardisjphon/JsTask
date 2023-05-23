package js.task.di.conversion

import js.task.data.net.utils.NetworkStatus
import js.task.domain.model.DomainNetworkStatus

class PresentationNetworkProvider(private val networkStatus : NetworkStatus) : DomainNetworkStatus
{
    override fun isOnline() : Boolean
    {
        return networkStatus.isOnline()
    }
}