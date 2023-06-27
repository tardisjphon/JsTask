package js.task.views

import js.task.screens.main.MainActivity
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class MainActivityTest
{
    private var activity : MainActivity? = null


    @Before
    @Throws(Exception::class)
    fun setUp()
    {
        activity = Robolectric.buildActivity(MainActivity::class.java)
            .create()
            .resume()
            .get()
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun shouldNotBeNull()
    {
        assertNotNull(activity)
    }
}