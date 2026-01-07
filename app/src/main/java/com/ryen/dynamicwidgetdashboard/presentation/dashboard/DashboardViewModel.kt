package com.ryen.dynamicwidgetdashboard.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryen.dynamicwidgetdashboard.domain.metadata.WidgetMetadataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class WidgetType {
    BANNER, LIST, UNSUPPORTED
}

data class WidgetMetadata(
    val type: WidgetType,
    val instanceId: String
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: WidgetMetadataRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardState>(DashboardState.Loading)
    val uiState: StateFlow<DashboardState> = _uiState.asStateFlow()

    init {
        loadWidgets()
    }

    private fun loadWidgets() {
        viewModelScope.launch(Dispatchers.IO) {

            _uiState.value = DashboardState.Loading

            try {
                val widgetMeta = repository.getWidgetMetadata()
                val widgetMetaDto = widgetMeta.map {
                    WidgetMetadata(
                        type = when (it.type) {
                            "banner" -> WidgetType.BANNER
                            "list" -> WidgetType.LIST
                            else -> WidgetType.UNSUPPORTED
                        },
                        instanceId = it.instanceId
                    )
                }
                if (widgetMetaDto.isEmpty()) {
                    _uiState.value = DashboardState.Empty
                } else {
                    _uiState.value = DashboardState.Loaded(widgetMetaDto)
                }
            } catch (e: Exception) {
                _uiState.value = DashboardState.Error(e.message ?: "Unable to load widgets")
            }
        }
    }

}



sealed class DashboardState {
    data object Loading : DashboardState()
    data class Loaded(val widgets: List<WidgetMetadata>) : DashboardState()
    data class Error(val message: String? = null) : DashboardState()
    data object Empty : DashboardState()
}

