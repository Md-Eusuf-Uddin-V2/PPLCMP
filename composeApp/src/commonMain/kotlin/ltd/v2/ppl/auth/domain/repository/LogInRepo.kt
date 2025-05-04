package ltd.v2.ppl.auth.domain.repository

import kotlinx.coroutines.flow.Flow
import ltd.v2.ppl.auth.domain.model.CampaignListDomainModel
import ltd.v2.ppl.auth.domain.model.SignInDomainModel
import ltd.v2.ppl.auth.domain.model.SurveyDomainModel
import ltd.v2.ppl.auth.domain.model.UserDataDomainModel
import ltd.v2.ppl.core.domain.DataError
import ltd.v2.ppl.core.domain.Result

interface LogInRepo {
    suspend fun getSignInDataResponse(signInMap: Map<String, Any>): Result<SignInDomainModel, DataError>
    suspend fun getUserInfoData(token: String): Result<UserDataDomainModel, DataError>
    suspend fun getCampaignListResponse(token: String): Result<List<CampaignListDomainModel>, DataError>
    suspend fun getSurveyData(token: String, campaignId: Int): Result<SurveyDomainModel, DataError>


    suspend fun insertCampaignData(campData: SurveyDomainModel, campaignId: String, userId: String, accessId: String): Result<Boolean, DataError>
    suspend fun updateCampaignData(campData: SurveyDomainModel, campaignId: String, userId: String): Result<Boolean, DataError>
    suspend fun getCampaignDataById(userId: String, campId: String): Result<SurveyDomainModel?, DataError>
    suspend fun getAllCampaignData(userId: String): Result<List<SurveyDomainModel?>, DataError>
}