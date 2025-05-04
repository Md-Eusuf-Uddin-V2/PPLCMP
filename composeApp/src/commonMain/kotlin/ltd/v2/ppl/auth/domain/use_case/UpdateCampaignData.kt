package ltd.v2.ppl.auth.domain.use_case

import ltd.v2.ppl.auth.domain.model.SurveyDomainModel
import ltd.v2.ppl.auth.domain.repository.LogInRepo
import ltd.v2.ppl.core.domain.DataError
import ltd.v2.ppl.core.domain.Result

class UpdateCampaignData(private val surveyRepo : LogInRepo) {

    suspend  operator fun invoke(campData: SurveyDomainModel, campaignId: String, userId: String) : Result<Boolean, DataError> {
        return  surveyRepo.updateCampaignData(campData, campaignId, userId)
    }
}


