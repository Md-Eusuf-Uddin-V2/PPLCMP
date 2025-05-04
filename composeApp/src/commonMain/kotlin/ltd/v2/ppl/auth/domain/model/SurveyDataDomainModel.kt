package ltd.v2.ppl.auth.domain.model



data class SurveyDomainModel(
    val id: Int?,
    val name: String?,
    val versions: VersionsDomainModel?,
    val video: List<VideoDomainModel>?,
    val image: List<String>?,
    val target: Int?,
    val products: List<ProductDomainModel>?,
    val surveyFlow: List<SurveyFlowDomainModel>?,
    val conditions: ConditionsDomainModel?,
    val locations: List<RoutePlanLocationDomainModel>?,
    val routePlan: List<RoutePlanDomainModel>?,
    val consumerLocation: List<RoutePlanDomainModel>?,
    val themeConfig: ThemeConfigDomainModel?
)

data class VersionsDomainModel(
    val campaign: Int?,
    val video: Int?,
    val image: Int?
)

data class VideoDomainModel(
    val md5: String?,
    val name: String?
)

data class ProductDomainModel(
    val name: String?,
    val id: String?
)

data class SurveyFlowDomainModel(
    val type: String?,
    val group: String?,
    val blocks: List<BlockDomainModel>?,
    val jumpingLogic: List<JumpingLogicDomainModel>?
)

data class BlockDomainModel(
    val options: List<OptionModelDomainModel>?,
    val subCheckListOptions: List<OptionModelDomainModel>? = null,
    val submittedAns: String? = null,
    val id: String?,
    val skip: SkipDomainModel?,
    val type: String?,
    val subQuestion: String?,
    val question: SurveyQuestionsDomainModel?,
    val validations: ValidationsDomainModel?,
    val subValidations: ValidationsDomainModel?,
    val referTo: ReferToDomainModel?,
    val required: Boolean?
)

data class JumpingLogicDomainModel(
    val id: String?,
    val conditions: List<JumpConditionDomainModel>?,
    val groupNo: String?
)

data class ConditionsDomainModel(
    val audio: Boolean?,
    val submit: SubmitDomainModel?,
    val geoFence: GeoFenceDomainModel?,
    val overAchievement: Boolean?
)

data class OptionModelDomainModel(
    val value: String?,
    val referTo: ReferToDomainModel?,
    val status: Boolean = false,
    val slug: String? = null,
    val alias: String? = null
)

data class SkipDomainModel(
    val id: String?,
    val groupNo: String?
)

data class SurveyQuestionsDomainModel(
    val slug: String?,
    val alias: String?
)

data class ValidationsDomainModel(
    val regex: String?,
    val max: Int?,
    val min: Int?,
    val terms: List<String>?,
    val bypass: Boolean?,
    val device: Boolean?,
    val server: Boolean?,
    val editable: Boolean?,
    val prefix: String?,
    val partial: Boolean?
)

data class ReferToDomainModel(
    val id: String?,
    val groupNo: String?
)



data class RoutePlanLocationDomainModel(
    val slug: String?,
    val locations: String?
)

data class RoutePlanDomainModel(
    val id: Int?,
    val name: String?,
    val typeSlug: String?,
    val parent: Int?,
    val type: Int?,
    val locations: List<RoutePlanDomainModel>?
)

data class ThemeConfigDomainModel(
    val themeColor: ThemeColorDomainModel?,
    val themeIcon: ThemeIconDomainModel?
)

data class ThemeIconDomainModel(
    val titlebarImage: String?
)

data class JumpConditionDomainModel(
    val id: String?,
    val answer: String?
)

data class SubmitDomainModel(
    val failedContact: FailedContactDomainModel?
)

data class FailedContactDomainModel(
    val survey: List<SurveyDomainModel>?
)

data class ThemeColorDomainModel(
    val primaryColor: String?,
    val primaryColorLight: String?,
    val questionColor: String?,
    val titleFontColor: String?,
    val statusBarColor: String?,
    val retakeReloadColor: String?
)

