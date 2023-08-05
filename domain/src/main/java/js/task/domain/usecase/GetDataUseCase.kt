package js.task.domain.usecase

import js.task.domain.interfaces.IDataProvider
import js.task.domain.usecase.interfaces.IGetDataUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class GetDataUseCase @Inject constructor(
    private val coroutineScope : CoroutineScope = CoroutineScope(Dispatchers.IO),
    private val dataProvider: IDataProvider
    ) : IGetDataUseCase()
{
    override fun getData()
    {
        coroutineScope.launch {
            dataProvider.getData()
                .collect {
                    if (it.isEmpty())
                    {
                        dataProvider.download()
                    }
                    else
                    {
                        println("$it")
                    }
                }
        }
    }
}