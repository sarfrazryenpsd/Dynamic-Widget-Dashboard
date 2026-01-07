package com.ryen.dynamicwidgetdashboard.presentation.widget.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryen.dynamicwidgetdashboard.domain.list.ListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class ListWidget(
    val name: String,
    val surname: String
)

@HiltViewModel
class ListWidgetViewModel @Inject constructor(
    private val repository: ListRepository
) : ViewModel(){

    private val _listState: MutableStateFlow<ListState> = MutableStateFlow(ListState.Loading)
    val listState = _listState.asStateFlow()


    fun loadListWidgets(instanceId: String) {
        viewModelScope.launch {
            _listState.value = ListState.Loading

            try {
                val listData = repository.getLists(instanceId)

                val listDto = listData.map {
                    ListWidget(
                        name = it.name,
                        surname = it.surname
                    )
                }

                if (listDto.isEmpty()) {
                    _listState.value = ListState.Empty
                } else {
                    _listState.value = ListState.Success(listDto)
                }

            } catch (e: Exception) {
                _listState.value = ListState.Error(message = e.message ?: "Unable to load data")
            }
        }
    }
}

sealed class ListState{
    object Loading : ListState()
    data class Success(val data: List<ListWidget>) : ListState()
    data object Empty : ListState()
    data class Error(val message: String?) : ListState()
}