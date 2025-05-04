package ltd.v2.ppl.auth.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "campaign_table")
data class CampaignDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val campData: String,
    val userId: String,
    val campId: String,
    val accessId: String,
)