package ltd.v2.ppl.auth.data.response_model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SurveyDataModel(
    @SerialName("status")
    val status: String? = null,

    @SerialName("message")
    val message: String? = null,

    @SerialName("data")
    val data: List<SurveyData>? = null
)

@Serializable
data class SurveyData(
    @SerialName("id")
    val id: Int? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("versions")
    val versions: Versions? = null,

    @SerialName("video")
    val video: List<Video>? = null,

    @SerialName("image")
    val image: List<String>? = null,

    @SerialName("target")
    val target: Int? = null,

    @SerialName("products")
    val products: List<Product>? = null,

    @SerialName("survey_flow")
    val surveyFlow: List<SurveyFlow>? = null,

    @SerialName("conditions")
    val conditions: Conditions? = null,

    @SerialName("locations")
    val locations: List<RoutePlanLocation>? = null,

    @SerialName("route_plan")
    val routePlan: List<RoutePlan>? = null,

    @SerialName("consumer_location")
    val consumerLocation: List<RoutePlan>? = null,

    @SerialName("theme_config")
    val themeConfig: ThemeConfig? = null
)

@Serializable
data class Versions(
    @SerialName("campaign")
    val campaign: Int? = null,

    @SerialName("video")
    val video: Int? = null,

    @SerialName("image")
    val image: Int? = null
)

@Serializable
data class Video(
    @SerialName("md5")
    val md5: String? = null,

    @SerialName("name")
    val name: String? = null
)

@Serializable
data class Product(
    @SerialName("name")
    val name: String? = null,

    @SerialName("id")
    val id: String? = null
)

@Serializable
data class SurveyFlow(
    @SerialName("type")
    val type: String? = null,

    @SerialName("group")
    val group: String? = null,

    @SerialName("blocks")
    val blocks: List<Block>? = null,

    @SerialName("jumping_logic")
    val jumpingLogic: List<JumpingLogic>? = null
)

@Serializable
data class Block(
    @SerialName("options")
    val options: List<OptionModel>? = null,

    @SerialName("sub_checklist_options")
    val subCheckListOptions: List<OptionModel>? = null,

    @SerialName("submitted_ans")
    val submittedAns: String? = null,

    @SerialName("id")
    val id: String? = null,

    @SerialName("skip")
    val skip: Skip? = null,

    @SerialName("type")
    val type: String? = null,

    @SerialName("sub_question")
    val subQuestion: String? = null,

    @SerialName("question")
    val question: SurveyQuestion? = null,

    @SerialName("validations")
    val validations: Validations? = null,

    @SerialName("sub_validations")
    val subValidations: Validations? = null,

    @SerialName("refer_to")
    val referTo: ReferTo? = null,

    @SerialName("required")
    val required: Boolean? = false
)

@Serializable
data class JumpingLogic(
    @SerialName("id")
    val id: String? = null,

    @SerialName("conditions")
    val conditions: List<JumpCondition>? = null,

    @SerialName("group_no")
    val groupNo: String? = null
)

@Serializable
data class Conditions(
    @SerialName("audio")
    val audio: Boolean? = null,

    @SerialName("submit")
    val submit: Submit? = null,

    @SerialName("geo_fence")
    val geoFence: GeoFence? = null,

    @SerialName("over_achievement")
    val overAchievement: Boolean? = null
)

@Serializable
data class OptionModel(
    @SerialName("value")
    val value: String? = null,

    @SerialName("referTo")
    val referTo: ReferTo? = null,

    @SerialName("status")
    val status: Boolean = false,

    @SerialName("slug")
    val slug: String? = null,

    @SerialName("alias")
    val alias: String? = null
)

@Serializable
data class Skip(
    @SerialName("id")
    val id: String? = null,

    @SerialName("group_no")
    val groupNo: String? = null
)

@Serializable
data class SurveyQuestion(
    @SerialName("slug")
    val slug: String? = null,

    @SerialName("alias")
    val alias: String? = null
)

@Serializable
data class Validations(
    @SerialName("regex")
    val regex: String? = null,

    @SerialName("max")
    val max: Int? = null,

    @SerialName("min")
    val min: Int? = null,

    @SerialName("terms")
    val terms: List<String>? = null,

    @SerialName("bypass")
    val bypass: Boolean? = false,

    @SerialName("device")
    val device: Boolean? = false,

    @SerialName("server")
    val server: Boolean? = false,

    @SerialName("editable")
    val editable: Boolean? = false,

    @SerialName("prefix")
    val prefix: String? = null,

    @SerialName("partial")
    val partial: Boolean? = false
)

@Serializable
data class ReferTo(
    @SerialName("id")
    val id: String? = null,

    @SerialName("group_no")
    val groupNo: String? = null
)

@Serializable
data class RoutePlanLocation(
    @SerialName("slug")
    val slug: String? = null,

    @SerialName("locations")
    val locations: String? = null
)

@Serializable
data class RoutePlan(
    @SerialName("id")
    val id: Int? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("type_slug")
    val typeSlug: String? = null,

    @SerialName("parent")
    val parent: Int? = null,

    @SerialName("type")
    val type: Int? = null,

    @SerialName("locations")
    val locations: List<RoutePlan>? = null
)

@Serializable
data class ThemeConfig(
    @SerialName("theme_color")
    val themeColor: ThemeColor? = null,

    @SerialName("theme_icon")
    val themeIcon: ThemeIcon? = null
)

@Serializable
data class ThemeColor(
    @SerialName("primary_color")
    val primaryColor: String? = null,

    @SerialName("primary_color_light")
    val primaryColorLight: String? = null,

    @SerialName("question_color")
    val questionColor: String? = null,

    @SerialName("title_font_color")
    val titleFontColor: String? = null,

    @SerialName("status_bar_color")
    val statusBarColor: String? = null,

    @SerialName("retake_reload_color")
    val retakeReloadColor: String? = null
)

@Serializable
data class ThemeIcon(
    @SerialName("titlebar_image")
    val titlebarImage: String? = null
)

@Serializable
data class JumpCondition(
    @SerialName("id")
    val id: String? = null,

    @SerialName("answer")
    val answer: String? = null
)

@Serializable
data class Submit(
    @SerialName("failed_contact")
    val failedContact: FailedContact? = null
)

@Serializable
data class FailedContact(
    @SerialName("survey")
    val survey: List<SurveyData>? = null
)
