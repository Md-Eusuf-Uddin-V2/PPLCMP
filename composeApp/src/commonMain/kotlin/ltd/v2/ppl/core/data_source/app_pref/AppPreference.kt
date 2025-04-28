package ltd.v2.ppl.core.data_source.app_pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import ltd.v2.ppl.auth.data.mappers.toDomain
import ltd.v2.ppl.auth.data.response_model.SignInDataModel
import ltd.v2.ppl.auth.domain.model.CampaignListDomainModel
import ltd.v2.ppl.auth.domain.model.SignInDomainModel
import ltd.v2.ppl.auth.domain.model.UserDataDomainModel
import ltd.v2.ppl.common_utils.constants.PrefConstants

class AppPreference(private val dataStore: DataStore<Preferences>) {


    val isFirstLogin: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[booleanPreferencesKey(PrefConstants.isFirstLogin)] ?: true
    }


    suspend fun setFirstLoginDone() {
        dataStore.edit { prefs ->
            prefs[booleanPreferencesKey(PrefConstants.isFirstLogin)] = false
        }
    }

    suspend fun setLoginData(loginData: String){
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey(PrefConstants.loginData)] = loginData
        }
    }

    suspend fun getLoginData(): SignInDomainModel {
        val loginData = dataStore.data
            .map { prefs ->
                prefs[stringPreferencesKey(PrefConstants.loginData)] ?: ""
            }
            .first()

        val signInDomainModel = Json.decodeFromString<SignInDataModel>(loginData)
        return signInDomainModel.toDomain()
    }

    suspend fun setUserInfoData(userInfoData: String){
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey(PrefConstants.userInfoData)] = userInfoData
        }
    }

    suspend fun getUserInfoData(): UserDataDomainModel {
        val userInfoData = dataStore.data
            .map { prefs ->
                prefs[stringPreferencesKey(PrefConstants.userInfoData)] ?: ""
            }

        val userDataDomainModel = Json.decodeFromString<UserDataDomainModel>(userInfoData.first())
        return userDataDomainModel
    }

    suspend fun setCampaignData(campaignData: String){
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey(PrefConstants.campaignData)] = campaignData
        }
    }

    suspend fun getCampaignData(): List<CampaignListDomainModel> {
        val campaignData = dataStore.data
            .map { prefs ->
                prefs[stringPreferencesKey(PrefConstants.campaignData)] ?: ""
            }
        val campaignList = Json.decodeFromString<List<CampaignListDomainModel>>(campaignData.first())
        return campaignList
    }
}
