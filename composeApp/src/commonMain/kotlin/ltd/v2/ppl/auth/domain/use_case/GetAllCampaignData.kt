package ltd.v2.ppl.auth.domain.use_case

import kotlinx.coroutines.flow.Flow
import ltd.v2.ppl.auth.domain.model.SurveyDomainModel
import ltd.v2.ppl.auth.domain.repository.LogInRepo
import ltd.v2.ppl.core.domain.DataError
import ltd.v2.ppl.core.domain.Result

class GetAllCampaignData(private val surveyRepo : LogInRepo) {

    suspend  operator fun invoke(userId: String) : Result<List<SurveyDomainModel?>, DataError> {
        return  surveyRepo.getAllCampaignData(userId)
    }
}


