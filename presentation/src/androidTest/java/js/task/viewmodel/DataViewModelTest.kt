package js.task.viewmodel

import androidx.test.platform.app.InstrumentationRegistry
import js.task.data.di.DataProviderModule
import js.task.di.DaggerApplicationGraph
import js.task.di.DataViewModelModule
import js.task.di.GetDataUseCaseModule
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.seconds


class DataViewModelTest
{
    private lateinit var viewModel : DataViewModel

    @Before
    fun setUpDagger()
    {
        val applicationContext = InstrumentationRegistry.getInstrumentation().targetContext
        val applicationGraph = DaggerApplicationGraph.builder()
            .dataProviderModule(DataProviderModule(applicationContext))
            .getDataUseCaseModule(GetDataUseCaseModule())
            .dataViewModelModule(DataViewModelModule())
            .build()
        val getLocalDataUseCase = applicationGraph.getLocalDataUseCase()
        val getRemoteDataUseCase = applicationGraph.getRemoteDataUseCase()
        viewModel = DataViewModel(
                getLocalDataUseCase,
                getRemoteDataUseCase
        )
    }

    private suspend fun getLocalData() =
        viewModel.invokeLocal()
            .firstOrNull()

    private suspend fun isLocalDataEmpty() =
        getLocalData()?.isEmpty() == true

    @Test
    fun getData() =
        runTest {

            if (isLocalDataEmpty())
            {
                viewModel.invokeRemote()

                withContext(Dispatchers.IO) {
                    delay(3.seconds)
                    assertTrue(!isLocalDataEmpty())
                    println("data retrieved from remote")
                }
            }
            else
            {
                assertTrue(true)
                println("data loaded from local")
            }
        }
}