package ltd.v2.ppl.auth.domain.use_case

import ltd.v2.ppl.auth.domain.model.SignInDomainModel
import ltd.v2.ppl.auth.domain.repository.LogInRepo
import ltd.v2.ppl.core.domain.DataError
import ltd.v2.ppl.core.domain.Result

class getSignInData(private val signInRepo: LogInRepo) {

    suspend  operator fun invoke(signInMap: Map<String, Any>) : Result<SignInDomainModel, DataError> {
        return  signInRepo.getSignInDataResponse(signInMap)
    }
}


