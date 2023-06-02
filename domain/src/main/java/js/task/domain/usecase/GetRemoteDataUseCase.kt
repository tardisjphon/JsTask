package js.task.domain.usecase

import javax.inject.Inject


class GetRemoteDataUseCase @Inject constructor(
    private val dataProvider : IDomainDataProvider
) : IGetRemoteDataUseCase
{
    override fun invokeRemote()
    {
        dataProvider.download()
    }
}