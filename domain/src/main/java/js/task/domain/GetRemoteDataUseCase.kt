package js.task.domain

import js.task.domain.model.DomainDataProvider
import js.task.domain.usecase.IGetRemoteDataUseCase
import javax.inject.Inject


class GetRemoteDataUseCase @Inject constructor(
    private val dataProvider : DomainDataProvider
) : IGetRemoteDataUseCase
{
    override fun invokeRemote()
    {
        dataProvider.download()
    }
}