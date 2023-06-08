package js.task.data.local.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import js.task.data.local.db.dao.DataDao;
import js.task.data.local.db.model.DataModel;


@Database(entities = {DataModel.class}, version = 1, exportSchema = false)
abstract public class AppDatabase
        extends RoomDatabase
{
    public static final class AppDatabaseCompanion
    {
        final static String NAME = "db_js_task";

        private static AppDatabase INSTANCE;

        public AppDatabase getInstance(Context context)
        {
            if (INSTANCE == null)
            {
                synchronized (this)
                {
                    if (INSTANCE == null)
                    {
                        INSTANCE = buildDatabase(context);
                    }
                    return INSTANCE;
                }
            }
            return INSTANCE;
        }

        private AppDatabase buildDatabase(Context context)
        {
            return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, NAME)
                    .build();
        }
    }

    public abstract DataDao dataDao();
}
