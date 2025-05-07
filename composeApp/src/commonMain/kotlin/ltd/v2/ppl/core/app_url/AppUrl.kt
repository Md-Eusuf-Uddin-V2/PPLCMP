package ltd.v2.ppl.core.app_url

object AppUrl {

    const val BASE_URL = "https://stg-backend.ppl.how/"
    const val DOWNLOAD_BASE_URL = "https://gp-web-uploads.s3.ap-southeast-1.amazonaws.com/"


    object Auth {
        const val SIGN_IN_URL = "login-manager/api/v1/auth/signin"
        const val USER_INFO_URL = "campaign-manager/api/v1/survey/get-user"
        const val CAMPAIGN_LIST_URL = "campaign-manager/api/v1/survey/get-campaigns"
        const val SURVEY_DATA_URL = "campaign-manager/api/v1/survey/get-config"

    }

    object  Attendance{
        const val MATERIALS_URL = "campaign-manager/api/v1/material/list/user"
    }


}