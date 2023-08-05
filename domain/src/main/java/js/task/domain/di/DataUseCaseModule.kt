package js.task.domain.di

import dagger.Module
import dagger.Provides
import js.task.domain.interfaces.IDataProvider
import js.task.domain.usecase.GetDataUseCase
import js.task.domain.usecase.interfaces.IGetDataUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Named


class DataUseCaseModule() {}

//@Module
//class DataUseCaseModule(
//    private val coroutineScope : CoroutineScope = CoroutineScope(Dispatchers.IO)
//)
//{
//    @Provides
//    fun getDataUseCase(//@Named("CoroutineScope") coroutineScope : CoroutineScope,
//                       @Named("ValueDataProvider") dataProvider : IDataProvider
//    ) : IGetDataUseCase
//    {
//        return GetDataUseCase(coroutineScope, dataProvider)
//    }
//}