package js.task.domain.usecase

import js.task.domain.usecase.model.DomainModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetLocalDataUseCase @Inject constructor(
    private val dataProvider : IDomainDataProvider
) : IGetLocalDataUseCase
{
    override fun invoke() : Flow<List<DomainModel>>
    {
        return dataProvider.getData()
    }
}