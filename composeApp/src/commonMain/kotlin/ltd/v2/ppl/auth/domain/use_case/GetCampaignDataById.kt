package ltd.v2.ppl.auth.domain.use_case

import ltd.v2.ppl.auth.domain.model.SurveyDomainModel
import ltd.v2.ppl.auth.domain.repository.LogInRepo
import ltd.v2.ppl.core.domain.DataError
import ltd.v2.ppl.core.domain.Result

class GetCampaignDataById(private val surveyRepo : LogInRepo) {

    suspend  operator fun invoke( userId: String, campId: String) : Result<SurveyDomainModel?, DataError> {
        return  surveyRepo.getCampaignDataById( userId, campId)
    }
}


