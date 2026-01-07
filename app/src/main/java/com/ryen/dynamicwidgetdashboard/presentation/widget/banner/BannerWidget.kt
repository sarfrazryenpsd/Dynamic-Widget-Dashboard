package com.ryen.dynamicwidgetdashboard.presentation.widget.banner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ryen.dynamicwidgetdashboard.presentation.widget.common.ErrorView
import com.ryen.dynamicwidgetdashboard.presentation.widget.common.LoadingView
import com.ryen.dynamicwidgetdashboard.ui.theme.DynamicWidgetDashboardTheme


@Composable
fun BannerWidget(
    banners: List<Banner>
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(banners) {
            BannerItem(
                title = it.title,
                subtitle = it.subtitle,
                isSingleBanner = banners.size == 1
            )
        }
    }
}

@Composable
fun BannerWidgetHost(instanceId: String) {

    val viewModel: BannerWidgetViewModel = hiltViewModel(
        key = instanceId
    )

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(instanceId) {
        viewModel.loadBanners(instanceId) // 🔥 runs only once per id
    }

    when {
        state.isLoading -> LoadingView()
        state.error != null -> ErrorView(state.error!!)
        else -> BannerWidget(state.banners)
    }
}

@Composable
fun BannerItem(
    title: String,
    subtitle: String,
    isSingleBanner: Boolean
) {

    val cardWidth = if (isSingleBanner) 1f else 0.8f

    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        ),
        modifier = Modifier.fillMaxWidth(cardWidth)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally

            ) {
            Text(
                text = title,
                style = MaterialTheme.typography.displayLarge,
                color = Color.Black
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black
            )
        }
    }

}

@Preview
@Composable
fun BannerItemPreview() {
    DynamicWidgetDashboardTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ){ BannerItem(title = "Title", subtitle = "Subtitle", isSingleBanner = true) }
    }
}