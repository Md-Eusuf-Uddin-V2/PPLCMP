package ltd.v2.ppl.auth.domain.use_case

import ltd.v2.ppl.auth.domain.model.SurveyDomainModel
import ltd.v2.ppl.auth.domain.repository.LogInRepo
import ltd.v2.ppl.core.domain.DataError
import ltd.v2.ppl.core.domain.Result

class InsertCampaignData(private val surveyRepo : LogInRepo) {

    suspend  operator fun invoke(campData: SurveyDomainModel, campaignId: String, userId: String, accessId: String) : Result<Boolean, DataError> {
        return  surveyRepo.insertCampaignData(campData, campaignId, userId, accessId)
    }
}


