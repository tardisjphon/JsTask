package js.task.data.local.db.converter

import androidx.room.TypeConverter

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import js.task.data.remote.retrofit.data.DailyMotion
import timber.log.Timber


class DailyMotionConverter
{
    @TypeConverter
    fun toDailyMotionList(json : String?) : List<DailyMotion?>?
    {
        if (json == null) return null
        return try
        {
            Gson().fromJson(
                    json, object : TypeToken<List<DailyMotion?>?>()
            {}.type
            )
        }
        catch (ex : JsonSyntaxException)
        {
            val message = ex.message ?: ""
            Timber.e("toDailyMotionList. exception message: $message")
            null
        }
    }

    @TypeConverter
    fun fromDailyMotionList(comboSegments : List<DailyMotion?>?) : String?
    {
        if (comboSegments == null) return null
        return try
        {
            Gson().toJson(comboSegments)
        }
        catch (ex : JsonSyntaxException)
        {
            val message = ex.message ?: ""
            Timber.e("fromDailyMotionList. exception message: $message")
            null
        }
    }
}