package js.task

import android.app.Application
import js.task.data.di.DataProviderModule
import js.task.di.ApplicationGraph
import js.task.di.DaggerApplicationGraph
import js.task.di.DataViewModelModule

class App : Application()
{
    lateinit var applicationGraph : ApplicationGraph

    override fun onCreate()
    {
        applicationGraph = DaggerApplicationGraph.builder()
        .dataProviderModule(DataProviderModule(this))
        .dataViewModelModule(DataViewModelModule())
        .build()

        super.onCreate()
    }
}
