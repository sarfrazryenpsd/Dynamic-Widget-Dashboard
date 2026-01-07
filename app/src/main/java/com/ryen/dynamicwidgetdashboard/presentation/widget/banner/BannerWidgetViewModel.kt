package com.ryen.dynamicwidgetdashboard.presentation.widget.banner

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryen.dynamicwidgetdashboard.domain.banner.BannerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class Banner(
    val title: String,
    val subtitle: String
)

data class BannerState(
    val banners: List<Banner> = emptyList(),
    val isLoading: Boolean = false,
    val isSingleBanner: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class BannerWidgetViewModel @Inject constructor(
    private val bannerRepository: BannerRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _uiState: MutableStateFlow<BannerState> = MutableStateFlow(BannerState())
    val uiState = _uiState.asStateFlow()


    fun loadBanners(instanceId: String) {
        viewModelScope.launch(Dispatchers.IO) {

            _uiState.update { state ->
                state.copy(
                    isLoading = true
                )
            }

            try {
                val bannerData = bannerRepository.getBanners(instanceId)
                val bannersDto = bannerData.map {
                    Banner(
                        title = it.title,
                        subtitle = it.subtitle
                    )
                }
                val isSingleBanner = bannersDto.size == 1

                _uiState.update { state ->
                    state.copy(
                        banners = bannersDto,
                        isSingleBanner = isSingleBanner,
                        isLoading = false
                    )
                }
            } catch (e: Exception){
                _uiState.update { state ->
                    state.copy(
                        error = e.message
                    )
                }
            }
        }
    }
}