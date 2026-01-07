package com.ryen.dynamicwidgetdashboard.presentation.widget.banner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ryen.dynamicwidgetdashboard.ui.theme.DynamicWidgetDashboardTheme


@Composable
fun BannerItem(
    title: String,
    subtitle: String,
    isSingleBanner: Boolean
) {

    val cardWidth = if (isSingleBanner) 1f else .8f

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
                .padding(20.dp),

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
        BannerItem(title = "Title", subtitle = "Subtitle", isSingleBanner = true)
    }
}