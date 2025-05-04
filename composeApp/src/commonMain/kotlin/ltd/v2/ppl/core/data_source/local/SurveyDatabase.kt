package ltd.v2.ppl.core.data_source.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ltd.v2.ppl.auth.data.local.CampaignDataDao
import ltd.v2.ppl.auth.data.local.CampaignDataEntity

@Database(
    entities = [CampaignDataEntity::class],
    version = 1
)
@TypeConverters(
    StringListTypeConverter::class
)
@ConstructedBy(SurveyDatabaseConstructor::class)
abstract class SurveyDatabase: RoomDatabase() {
    abstract val campaignDataDao: CampaignDataDao

    companion object {
        const val DB_NAME = "survey.db"
    }
}
