package ltd.v2.ppl.auth.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CampaignDataDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCampaignData(campData: CampaignDataEntity): Long

    @Query("UPDATE campaign_table SET campData = :campData WHERE userId = :userId AND campId = :campId")
    suspend fun updateCampaignData(campData: String, userId: String, campId: String): Int


    @Query("SELECT * FROM campaign_table WHERE userId = :userId")
    fun getAllCampaignData(userId: String): List<CampaignDataEntity>


    @Query("SELECT * FROM campaign_table WHERE userId = :userId AND campId = :campId")
    fun getCampaignDataById(userId: String, campId: String): CampaignDataEntity?

    @Query("DELETE FROM campaign_table WHERE userId = :userId AND campId = :campId")
    suspend fun deleteCampaignDataById(userId: String, campId: String)
}
