package ltd.v2.ppl.attendance.presentation.asset

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ltd.v2.ppl.attendance.domain.model.NonGiveableDomain
import ltd.v2.ppl.attendance.presentation.giveable.GiveableIntent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AssetCollectionCompose(
    title: String,
    assetsList: List<NonGiveableDomain>,
    onNext: () -> Unit
) {
    val viewModel: AssetsViewModel = koinViewModel()

    // Load initial data
    LaunchedEffect(Unit) {
        if (assetsList.isNotEmpty()) {
            viewModel.loadAssetsListData(assetsList)
        }
    }

    // Listen for one-time state changes
    LaunchedEffect(Unit) {
        viewModel.oneTimeState.collect { state ->
            if (state.isNextEnabled) {
                onNext()
            }
        }
    }

    val state by viewModel.state.collectAsState()

    if (state.assetsList.isNotEmpty()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
            )
        ) {
            Column(modifier = Modifier.background(Color.Transparent)) {
                Text(
                    text = title,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 1000.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    itemsIndexed(state.assetsList) { index,  assetItem ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Text(text = assetItem.name.orEmpty())

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clickable {
                                            viewModel.onIntent(
                                                AssetsIntent.DecrementQty(
                                                  assetItem.id ?: 0
                                                )
                                            )
                                        }
                                        .background(
                                            MaterialTheme.colorScheme.primary,
                                            RoundedCornerShape(50)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Remove,
                                        contentDescription = "Remove",
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(text = "${assetItem.acceptedQty}")

                                Spacer(modifier = Modifier.width(8.dp))

                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clickable {
                                            viewModel.onIntent(
                                                AssetsIntent.IncrementQty(
                                                    assetId = assetItem.id ?: 0
                                                )
                                            )
                                        }
                                        .background(
                                            MaterialTheme.colorScheme.primary,
                                            RoundedCornerShape(50)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Add,
                                        contentDescription = "Add",
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(text = "/ ${assetItem.qty ?: 0}")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No assets available")
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { viewModel.onIntent(AssetsIntent.OnNextClicked) }) {
            if(state.isLoading){
                CircularProgressIndicator(modifier = Modifier.size(16.dp))
            }else{
                Text("Next")
            }

        }
    }
}
