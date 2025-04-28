package ltd.v2.ppl.auth.domain.use_case

import io.ktor.client.plugins.auth.providers.BearerTokens
import ltd.v2.ppl.auth.domain.model.CampaignListDomainModel
import ltd.v2.ppl.auth.domain.model.SignInDomainModel
import ltd.v2.ppl.auth.domain.model.UserDataDomainModel
import ltd.v2.ppl.auth.domain.repository.LogInRepo
import ltd.v2.ppl.core.domain.DataError
import ltd.v2.ppl.core.domain.Result

class getCampaignListData(private val campRepo: LogInRepo) {

    suspend  operator fun invoke(token : String) : Result<List<CampaignListDomainModel>, DataError> {
        return  campRepo.getCampaignListResponse(token)
    }
}


