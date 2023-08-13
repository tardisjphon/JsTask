package js.task

import android.app.Application
import io.reactivex.rxjava3.disposables.CompositeDisposable
import js.task.data.DataProvider
import js.task.data.local.DbRepository
import js.task.data.local.db.AppDatabase
import js.task.data.remote.RetrofitRepository
import js.task.domain.interfaces.IDataProvider
import js.task.domain.usecase.GetDataUseCase
import js.task.domain.usecase.interfaces.IGetDataUseCase
import js.task.viewmodel.DataViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin

import org.koin.dsl.module


class App : Application()
{
    private val compositeDisposable = lazy { CompositeDisposable() }

    override fun onCreate()
    {
        super.onCreate()

        val moduleDataProvider = module {
            single {
                getDataProvider()
            }
        }
        val moduleDataUseCase = module {
            single {
                getDataUseCase()
            }
            viewModel { DataViewModel(get()) }
        }

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                    moduleDataProvider,
                    moduleDataUseCase
            )
        }
    }

    private fun getDataProvider() : IDataProvider
    {
        return DataProvider(
                compositeDisposable.value,
                DbRepository(
                        AppDatabase.AppDatabaseCompanion()
                            .getInstance(applicationContext),
                        compositeDisposable.value
                ),
                RetrofitRepository()
        )
    }

    private fun getDataUseCase() : IGetDataUseCase
    {
        return GetDataUseCase(
                getDataProvider(),
                compositeDisposable.value
        )
    }
}
