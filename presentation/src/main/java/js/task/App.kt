package js.task

import android.app.Application
import js.task.data.DataProvider
import js.task.data.local.DbRepository
import js.task.data.local.db.AppDatabase
import js.task.data.remote.RetrofitRepository
import js.task.domain.interfaces.IDataProvider
import js.task.domain.usecase.GetDataUseCase
import js.task.domain.usecase.interfaces.IGetDataUseCase
import js.task.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module


class App : Application()
{
    private val coroutineScope : CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate()
    {
        val moduleDataUseCase = module {
            single {
                getDataUseCase()
            }
            viewModel { MainViewModel(get()) }
        }

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(moduleDataUseCase)
        }

        super.onCreate()
    }

    private fun getDataUseCase() : IGetDataUseCase
    {
        return GetDataUseCase(
                coroutineScope,
                dataProvider
        )
    }

    private val dataProvider : IDataProvider by lazy {

        DataProvider(
                coroutineScope,
                DbRepository(
                        AppDatabase.getInstance(applicationContext),
                        coroutineScope
                ),
                RetrofitRepository()
        )
    }
}
