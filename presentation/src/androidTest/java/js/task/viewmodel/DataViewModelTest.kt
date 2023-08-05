package js.task.viewmodel

import androidx.test.platform.app.InstrumentationRegistry
import js.task.data.di.DataProviderModule
import js.task.di.DaggerApplicationGraph
import js.task.di.DataViewModelModule
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
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
            .dataViewModelModule(DataViewModelModule())
            .build()
        val getDataUseCase = applicationGraph.dataUseCase()

        viewModel = DataViewModel(getDataUseCase)
    }

    private suspend fun isLocalDataEmpty() = viewModel.dataList.first().isEmpty()
    private suspend fun getSize() = viewModel.dataList.first().size

    @Test
    fun getData() =
        runTest {

            if (isLocalDataEmpty())
            {
                viewModel.getData()

                withContext(Dispatchers.IO) {
                    delay(3.seconds)
                    assertTrue(!isLocalDataEmpty())
                    val size = getSize()
                    println("data retrieved from remote. size: $size")
                }
            }
            else
            {
                assertTrue(true)
                val size = getSize()
                println("data loaded from local. size: $size")
            }
        }
}