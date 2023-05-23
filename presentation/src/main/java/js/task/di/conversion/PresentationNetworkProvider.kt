package js.task.di.conversion

import js.task.data.remote.utils.NetworkStatus
import js.task.domain.model.DomainNetworkStatus
import javax.inject.Inject

class PresentationNetworkProvider @Inject constructor(private val networkStatus : NetworkStatus) : DomainNetworkStatus
{
    override fun isOnline() : Boolean
    {
        return networkStatus.isOnline()
    }
}