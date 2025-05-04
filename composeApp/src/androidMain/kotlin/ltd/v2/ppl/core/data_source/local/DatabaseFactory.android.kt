package ltd.v2.ppl.core.data_source.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(private val context: Context) {
    actual fun create(): RoomDatabase.Builder<SurveyDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(SurveyDatabase.DB_NAME)

        return Room.databaseBuilder(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}
