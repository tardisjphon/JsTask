package js.task.viewmodel

import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Test
import kotlin.time.Duration.Companion.seconds


class DataViewModelTest
{
    private lateinit var viewModel : DataViewModel

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