package com.ryen.dynamicwidgetdashboard.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ryen.dynamicwidgetdashboard.presentation.widget.banner.BannerWidgetHost
import com.ryen.dynamicwidgetdashboard.presentation.widget.common.EmptyView
import com.ryen.dynamicwidgetdashboard.presentation.widget.common.ErrorView
import com.ryen.dynamicwidgetdashboard.presentation.widget.common.LoadingView
import com.ryen.dynamicwidgetdashboard.presentation.widget.list.ListWidgetHost

@Composable
fun DashboardScreen(
    dashBoardVewModel: DashboardViewModel = hiltViewModel<DashboardViewModel>(),
    modifier: Modifier = Modifier
) {

    val dashboardState by dashBoardVewModel.uiState.collectAsState()

    when (val state = dashboardState) {
        DashboardState.Loading -> {
            LoadingView()
        }

        is DashboardState.Error -> {
            ErrorView((dashboardState as DashboardState.Error).message ?: "Unknown error")
        }

        DashboardState.Empty -> {
            EmptyView()
        }

        is DashboardState.Loaded -> {

            LazyColumn(
                verticalArrangement = Arrangement.Top,
                modifier = modifier.then(
                Modifier.padding(4.dp))
            ) {
                state.widgets.forEach { widgetMetadata ->

                    item{
                        Text(
                            text = widgetMetadata.instanceId,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }

                    when (widgetMetadata.type) {
                        WidgetType.BANNER -> {
                            item{ BannerWidgetHost(instanceId = widgetMetadata.instanceId) }
                        }

                        WidgetType.LIST -> {
                            item{ ListWidgetHost(instanceId = widgetMetadata.instanceId) }
                        }

                        else -> {
                            item {
                                Text(
                                    "Unsupported widget type",
                                    color = Color.Red,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }

                    item{ Spacer(modifier = Modifier.height(12.dp)) }
                }
            }
        }
    }

}