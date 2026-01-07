package com.ryen.dynamicwidgetdashboard.presentation.widget.banner

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

data class BannerWidget(
    val title: String,
    val subtitle: String
)

data class BannerState(
    val instanceId: String = "",
    val banners: List<BannerWidget> = emptyList(),
    val isLoading: Boolean = false,
    val isSingleBanner: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class BannerViewModel @Inject constructor(
    private val bannerRepository: BannerRepository
): ViewModel(){

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
                    BannerWidget(
                        title = it.title,
                        subtitle = it.subtitle
                    )
                }
                val isSingleBanner = bannersDto.size == 1

                _uiState.update { state ->
                    state.copy(
                        instanceId = instanceId,
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