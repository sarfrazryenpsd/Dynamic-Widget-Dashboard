package com.ryen.dynamicwidgetdashboard.presentation.widget.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ryen.dynamicwidgetdashboard.presentation.widget.common.EmptyView
import com.ryen.dynamicwidgetdashboard.presentation.widget.common.ErrorView
import com.ryen.dynamicwidgetdashboard.presentation.widget.common.LoadingView
import com.ryen.dynamicwidgetdashboard.ui.theme.DynamicWidgetDashboardTheme

@Composable
fun ListWidget(
    listWidget: List<ListWidget>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray.copy(alpha = .6f))
            .height(360.dp)
            .padding(6.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(listWidget) {
                ListItem(
                    name = it.name,
                    surname = it.surname,
                )
            }
        }
    }
}

@Composable
fun ListWidgetHost(instanceId: String) {

    val viewModel: ListWidgetViewModel = hiltViewModel(
        key = instanceId
    )

    val state by viewModel.listState.collectAsState()

    LaunchedEffect(instanceId) {
        viewModel.loadListWidgets(instanceId)
    }

    when (state) {
        is ListState.Loading -> LoadingView()
        is ListState.Error -> ErrorView((state as ListState.Error).message ?: "Error")
        is ListState.Empty -> EmptyView()
        else -> ListWidget((state as ListState.Success).data)
    }
}

@Composable
fun ListItem(
    name: String,
    surname: String,
) {

    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 15.dp)

        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black
            )
            Text(
                text = surname,
                style = MaterialTheme.typography.labelLarge,
                color = Color.Black
            )
        }
    }

}

@Preview
@Composable
private fun ListItemPrev() {
    DynamicWidgetDashboardTheme {
        ListItem(
            name = "Sarfraz",
            surname = "Ryen",
        )
    }

}