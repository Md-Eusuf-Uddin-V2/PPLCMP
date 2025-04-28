package ltd.v2.ppl.auth.domain.repository

import ltd.v2.ppl.auth.domain.model.CampaignListDomainModel
import ltd.v2.ppl.auth.domain.model.CampaignsDomainModel
import ltd.v2.ppl.auth.domain.model.SignInDomainModel
import ltd.v2.ppl.auth.domain.model.UserDataDomainModel
import ltd.v2.ppl.core.domain.DataError
import ltd.v2.ppl.core.domain.Result

interface LogInRepo {
    suspend fun getSignInDataResponse(signInMap: Map<String, Any>): Result<SignInDomainModel, DataError>
    suspend fun getUserInfoData(token: String): Result<UserDataDomainModel, DataError>
    suspend fun getCampaignListResponse(token: String): Result<List<CampaignListDomainModel>, DataError>
}