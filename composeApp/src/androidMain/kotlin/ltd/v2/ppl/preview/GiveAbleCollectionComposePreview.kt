package ltd.v2.ppl.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ltd.v2.ppl.attendance.presentation.giveable.GiveAbleCollectionCompose


@Preview(showBackground = true)
@Composable
fun GiveAbleCollectionComposePreview(){
    GiveAbleCollectionCompose(
        title = "Giveable Collection",
        /*viewModel = listOf(
            GiveableDomain(
                campaignId = 1,
                campaignName = "Robi Elite",
                materials = listOf(
                    MaterialItemDomain(
                        id = 1,
                        name = "Test Material",
                        qty = 100,
                        type = 40,
                        typeName = 40,
                        campaignId = 5,
                        campaignName = "Robi Elite",
                        accepted = false
                    )
                )

        )
    )*/
    )
}