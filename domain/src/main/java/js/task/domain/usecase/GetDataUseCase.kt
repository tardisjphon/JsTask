package js.task.domain.usecase

import js.task.domain.interfaces.IDataProvider
import js.task.domain.usecase.interfaces.IGetDataUseCase
import js.task.domain.usecase.model.DomainModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class GetDataUseCase @Inject constructor(
    private val coroutineScope : CoroutineScope = CoroutineScope(Dispatchers.IO),
    private val dataProvider : IDataProvider
) : IGetDataUseCase
{
    override fun invoke(data : MutableSharedFlow<List<DomainModel>>)
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
                        data.emit(it)
                    }
                }
        }
    }
}