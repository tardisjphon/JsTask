package js.task

import android.app.Application
import js.task.data.DataProvider
import js.task.data.local.DbRepository
import js.task.data.local.db.AppDatabase
import js.task.data.remote.RetrofitRepository
import js.task.domain.interfaces.IDataProvider
import js.task.domain.usecase.GetDataUseCase
import js.task.domain.usecase.interfaces.IGetDataUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module


class App : Application()
{
    private val coroutineScope : CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate()
    {
        val moduleDataProvider = module {
            single {
                getDataProvider()
            }
        }
        val moduleDataUseCase = module {
            single {
                getDataUseCase()
            }
        }

        startKoin {
            modules(
                    moduleDataProvider,
                    moduleDataUseCase
            )
        }

        super.onCreate()
    }

    private fun getDataProvider() : IDataProvider
    {
        return DataProvider(
                coroutineScope,
                DbRepository(
                        AppDatabase.getInstance(applicationContext),
                        coroutineScope
                ),
                RetrofitRepository()
        )
    }

    private fun getDataUseCase() : IGetDataUseCase
    {
        return GetDataUseCase(
                coroutineScope,
                getDataProvider()
        )
    }
}
