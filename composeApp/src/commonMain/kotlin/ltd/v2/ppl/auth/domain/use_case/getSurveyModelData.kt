package ltd.v2.ppl.auth.domain.use_case

import ltd.v2.ppl.auth.domain.model.SurveyDomainModel
import ltd.v2.ppl.auth.domain.repository.LogInRepo
import ltd.v2.ppl.core.domain.DataError
import ltd.v2.ppl.core.domain.Result

class getSurveyModelData(private val surveyRepo : LogInRepo) {

    suspend  operator fun invoke(token : String, campId: Int) : Result<SurveyDomainModel, DataError> {
        return  surveyRepo.getSurveyData(token, campId)
    }
}


