package js.task.domain

import js.task.domain.model.DomainDataProvider
import js.task.domain.usecase.IGetLocalDataUseCase
import js.task.domain.usecase.model.DomainModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetLocalDataUseCase @Inject constructor(
    private val dataProvider : DomainDataProvider
) : IGetLocalDataUseCase
{
    override fun invoke() : Flow<List<DomainModel>>
    {
        return dataProvider.getData()
    }
}