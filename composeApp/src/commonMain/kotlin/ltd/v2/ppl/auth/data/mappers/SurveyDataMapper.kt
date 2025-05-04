package ltd.v2.ppl.auth.data.mappers

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ltd.v2.ppl.auth.data.local.CampaignDataEntity
import ltd.v2.ppl.auth.data.response_model.Block
import ltd.v2.ppl.auth.data.response_model.Conditions
import ltd.v2.ppl.auth.data.response_model.FailedContact
import ltd.v2.ppl.auth.data.response_model.GeoFence
import ltd.v2.ppl.auth.data.response_model.JumpCondition
import ltd.v2.ppl.auth.data.response_model.JumpingLogic
import ltd.v2.ppl.auth.data.response_model.OptionModel
import ltd.v2.ppl.auth.data.response_model.Product
import ltd.v2.ppl.auth.data.response_model.ReferTo
import ltd.v2.ppl.auth.data.response_model.RoutePlan
import ltd.v2.ppl.auth.data.response_model.RoutePlanLocation
import ltd.v2.ppl.auth.data.response_model.Skip
import ltd.v2.ppl.auth.data.response_model.Submit
import ltd.v2.ppl.auth.data.response_model.SurveyData
import ltd.v2.ppl.auth.data.response_model.SurveyFlow
import ltd.v2.ppl.auth.data.response_model.SurveyQuestion
import ltd.v2.ppl.auth.data.response_model.ThemeColor
import ltd.v2.ppl.auth.data.response_model.ThemeConfig
import ltd.v2.ppl.auth.data.response_model.ThemeIcon
import ltd.v2.ppl.auth.data.response_model.Validations
import ltd.v2.ppl.auth.data.response_model.Versions
import ltd.v2.ppl.auth.data.response_model.Video
import ltd.v2.ppl.auth.domain.model.BlockDomainModel
import ltd.v2.ppl.auth.domain.model.ConditionsDomainModel
import ltd.v2.ppl.auth.domain.model.FailedContactDomainModel
import ltd.v2.ppl.auth.domain.model.GeoFenceDomainModel
import ltd.v2.ppl.auth.domain.model.JumpConditionDomainModel
import ltd.v2.ppl.auth.domain.model.JumpingLogicDomainModel
import ltd.v2.ppl.auth.domain.model.OptionModelDomainModel
import ltd.v2.ppl.auth.domain.model.ProductDomainModel
import ltd.v2.ppl.auth.domain.model.ReferToDomainModel
import ltd.v2.ppl.auth.domain.model.RoutePlanDomainModel
import ltd.v2.ppl.auth.domain.model.RoutePlanLocationDomainModel
import ltd.v2.ppl.auth.domain.model.SkipDomainModel
import ltd.v2.ppl.auth.domain.model.SubmitDomainModel
import ltd.v2.ppl.auth.domain.model.SurveyDomainModel
import ltd.v2.ppl.auth.domain.model.SurveyFlowDomainModel
import ltd.v2.ppl.auth.domain.model.SurveyQuestionsDomainModel
import ltd.v2.ppl.auth.domain.model.ThemeColorDomainModel
import ltd.v2.ppl.auth.domain.model.ThemeConfigDomainModel
import ltd.v2.ppl.auth.domain.model.ThemeIconDomainModel
import ltd.v2.ppl.auth.domain.model.ValidationsDomainModel
import ltd.v2.ppl.auth.domain.model.VersionsDomainModel
import ltd.v2.ppl.auth.domain.model.VideoDomainModel

// Data to Domain
fun SurveyData.toDomain(): SurveyDomainModel {
    return SurveyDomainModel(
        id = id,
        name = name,
        versions = versions?.toDomain(),
        video = video?.map { it.toDomain() },
        image = image,
        target = target,
        products = products?.map { it.toDomain() },
        surveyFlow = surveyFlow?.map { it.toDomain() },
        conditions = conditions?.toDomain(),
        locations = locations?.map { it.toDomain() },
        routePlan = routePlan?.map { it.toDomain() },
        consumerLocation = consumerLocation?.map { it.toDomain() },
        themeConfig = themeConfig?.toDomain()
    )
}

 fun Versions.toDomain(): VersionsDomainModel {
    return VersionsDomainModel(
        campaign = campaign,
        video = video,
        image = image
    )
}

 fun Video.toDomain(): VideoDomainModel {
    return VideoDomainModel(
        md5 = md5,
        name = name
    )
}

 fun Product.toDomain(): ProductDomainModel {
    return ProductDomainModel(
        name = name,
        id = id
    )
}

 fun SurveyFlow.toDomain(): SurveyFlowDomainModel {
    return SurveyFlowDomainModel(
        type = type,
        group = group,
        blocks = blocks?.map { it.toDomain() },
        jumpingLogic = jumpingLogic?.map { it.toDomain() }
    )
}

 fun Block.toDomain(): BlockDomainModel {
    return BlockDomainModel(
        options = options?.map { it.toDomain() },
        subCheckListOptions = subCheckListOptions?.map { it.toDomain() },
        submittedAns = submittedAns,
        id = id,
        skip = skip?.toDomain(),
        type = type,
        subQuestion = subQuestion,
        question = question?.toDomain(),
        validations = validations?.toDomain(),
        subValidations = subValidations?.toDomain(),
        referTo = referTo?.toDomain(),
        required = required
    )
}

 fun JumpingLogic.toDomain(): JumpingLogicDomainModel {
    return JumpingLogicDomainModel(
        id = id,
        conditions = conditions?.map { it.toDomain() },
        groupNo = groupNo
    )
}

 fun JumpCondition.toDomain(): JumpConditionDomainModel {
    return JumpConditionDomainModel(
        id = id,
        answer = answer
    )
}

 fun Conditions.toDomain(): ConditionsDomainModel {
    return ConditionsDomainModel(
        audio = audio,
        submit = submit?.toDomain(),
        geoFence = geoFence?.toDomain(),
        overAchievement = overAchievement
    )
}

 fun Submit.toDomain(): SubmitDomainModel {
    return SubmitDomainModel(
        failedContact = failedContact?.toDomain()
    )
}

 fun FailedContact.toDomain(): FailedContactDomainModel {
    return FailedContactDomainModel(
        survey = survey?.map { it.toDomain() }
    )
}

 fun GeoFence.toDomain(): GeoFenceDomainModel {
    return GeoFenceDomainModel(
        lat = lat,
        long = long,
        forced = forced,
        radius = radius,
        status = status
    )
}

 fun OptionModel.toDomain(): OptionModelDomainModel {
    return OptionModelDomainModel(
        value = value,
        referTo = referTo?.toDomain(),
        status = status,
        slug = slug,
        alias = alias
    )
}

 fun Skip.toDomain(): SkipDomainModel {
    return SkipDomainModel(
        id = id,
        groupNo = groupNo
    )
}

 fun SurveyQuestion.toDomain(): SurveyQuestionsDomainModel {
    return SurveyQuestionsDomainModel(
        slug = slug,
        alias = alias
    )
}

 fun Validations.toDomain(): ValidationsDomainModel {
    return ValidationsDomainModel(
        regex = regex,
        max = max,
        min = min,
        terms = terms,
        bypass = bypass,
        device = device,
        server = server,
        editable = editable,
        prefix = prefix,
        partial = partial
    )
}

 fun ReferTo.toDomain(): ReferToDomainModel {
    return ReferToDomainModel(
        id = id,
        groupNo = groupNo
    )
}

 fun RoutePlanLocation.toDomain(): RoutePlanLocationDomainModel {
    return RoutePlanLocationDomainModel(
        slug = slug,
        locations = locations
    )
}

 fun RoutePlan.toDomain(): RoutePlanDomainModel {
    return RoutePlanDomainModel(
        id = id,
        name = name,
        typeSlug = typeSlug,
        parent = parent,
        type = type,
        locations = locations?.map { it.toDomain() }
    )
}

 fun ThemeConfig.toDomain(): ThemeConfigDomainModel {
    return ThemeConfigDomainModel(
        themeColor = themeColor?.toDomain(),
        themeIcon = themeIcon?.toDomain()
    )
}

 fun ThemeColor.toDomain(): ThemeColorDomainModel {
    return ThemeColorDomainModel(
        primaryColor = primaryColor,
        primaryColorLight = primaryColorLight,
        questionColor = questionColor,
        titleFontColor = titleFontColor,
        statusBarColor = statusBarColor,
        retakeReloadColor = retakeReloadColor
    )
}

 fun ThemeIcon.toDomain(): ThemeIconDomainModel {
    return ThemeIconDomainModel(
        titlebarImage = titlebarImage
    )
}


/// Domain to Data

fun SurveyDomainModel.toDomainDto(): SurveyData {
    return SurveyData(
        id = id,
        name = name,
        versions = versions?.toDomainDto(),
        video = video?.map { it.toDomainDto() },
        image = image,
        target = target,
        products = products?.map { it.toDomainDto() },
        surveyFlow = surveyFlow?.map { it.toDomainDto() },
        conditions = conditions?.toDomainDto(),
        locations = locations?.map { it.toDomainDto() },
        routePlan = routePlan?.map { it.toDomainDto() },
        consumerLocation = consumerLocation?.map { it.toDomainDto() },
        themeConfig = themeConfig?.toDomainDto()
    )
}

fun VersionsDomainModel.toDomainDto(): Versions {
    return Versions(
        campaign = campaign,
        video = video,
        image = image
    )
}

fun VideoDomainModel.toDomainDto(): Video {
    return Video(
        md5 = md5,
        name = name
    )
}

fun ProductDomainModel.toDomainDto(): Product {
    return Product(
        name = name,
        id = id
    )
}

fun SurveyFlowDomainModel.toDomainDto(): SurveyFlow {
    return SurveyFlow(
        type = type,
        group = group,
        blocks = blocks?.map { it.toDomainDto() },
        jumpingLogic = jumpingLogic?.map { it.toDomainDto() }
    )
}

fun BlockDomainModel.toDomainDto(): Block {
    return Block(
        options = options?.map { it.toDomainDto() },
        subCheckListOptions = subCheckListOptions?.map { it.toDomainDto() },
        submittedAns = submittedAns,
        id = id,
        skip = skip?.toDomainDto(),
        type = type,
        subQuestion = subQuestion,
        question = question?.toDomainDto(),
        validations = validations?.toDomainDto(),
        subValidations = subValidations?.toDomainDto(),
        referTo = referTo?.toDomainDto(),
        required = required
    )
}

fun JumpingLogicDomainModel.toDomainDto(): JumpingLogic {
    return JumpingLogic(
        id = id,
        conditions = conditions?.map { it.toDomainDto() },
        groupNo = groupNo
    )
}

fun JumpConditionDomainModel.toDomainDto(): JumpCondition {
    return JumpCondition(
        id = id,
        answer = answer
    )
}

fun ConditionsDomainModel.toDomainDto(): Conditions {
    return Conditions(
        audio = audio,
        submit = submit?.toDomainDto(),
        geoFence = geoFence?.toDomainDto(),
        overAchievement = overAchievement
    )
}

fun SubmitDomainModel.toDomainDto(): Submit {
    return Submit(
        failedContact = failedContact?.toDomainDto()
    )
}

fun FailedContactDomainModel.toDomainDto(): FailedContact {
    return FailedContact(
        survey = survey?.map { it.toDomainDto() }
    )
}

fun GeoFenceDomainModel.toDomainDto(): GeoFence {
    return GeoFence(
        lat = lat,
        long = long,
        forced = forced,
        radius = radius,
        status = status
    )
}

fun OptionModelDomainModel.toDomainDto(): OptionModel {
    return OptionModel(
        value = value,
        referTo = referTo?.toDomainDto(),
        status = status,
        slug = slug,
        alias = alias
    )
}

fun SkipDomainModel.toDomainDto(): Skip {
    return Skip(
        id = id,
        groupNo = groupNo
    )
}

fun SurveyQuestionsDomainModel.toDomainDto(): SurveyQuestion {
    return SurveyQuestion(
        slug = slug,
        alias = alias
    )
}

fun ValidationsDomainModel.toDomainDto(): Validations {
    return Validations(
        regex = regex,
        max = max,
        min = min,
        terms = terms,
        bypass = bypass,
        device = device,
        server = server,
        editable = editable,
        prefix = prefix,
        partial = partial
    )
}

fun ReferToDomainModel.toDomainDto(): ReferTo {
    return ReferTo(
        id = id,
        groupNo = groupNo
    )
}

fun RoutePlanLocationDomainModel.toDomainDto(): RoutePlanLocation {
    return RoutePlanLocation(
        slug = slug,
        locations = locations
    )
}

fun RoutePlanDomainModel.toDomainDto(): RoutePlan {
    return RoutePlan(
        id = id,
        name = name,
        typeSlug = typeSlug,
        parent = parent,
        type = type,
        locations = locations?.map { it.toDomainDto() }
    )
}

fun ThemeConfigDomainModel.toDomainDto(): ThemeConfig {
    return ThemeConfig(
        themeColor = themeColor?.toDomainDto(),
        themeIcon = themeIcon?.toDomainDto()
    )
}

fun ThemeColorDomainModel.toDomainDto(): ThemeColor {
    return ThemeColor(
        primaryColor = primaryColor,
        primaryColorLight = primaryColorLight,
        questionColor = questionColor,
        titleFontColor = titleFontColor,
        statusBarColor = statusBarColor,
        retakeReloadColor = retakeReloadColor
    )
}

fun ThemeIconDomainModel.toDomainDto(): ThemeIcon {
    return ThemeIcon(
        titlebarImage = titlebarImage
    )
}




