package js.task.domain.usecase

import io.reactivex.Observable
import js.task.domain.usecase.model.DomainModel
import javax.inject.Inject


class GetLocalDataUseCase @Inject constructor(
    private val dataProvider : IDomainDataProvider
) : IGetLocalDataUseCase
{
    override fun invokeLocal() : Observable<List<DomainModel>>
    {
        return dataProvider.getData()
    }
}