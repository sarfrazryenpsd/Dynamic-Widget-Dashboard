package com.ryen.dynamicwidgetdashboard.data.list

import com.ryen.dynamicwidgetdashboard.domain.model.ListConfig
import com.ryen.dynamicwidgetdashboard.domain.list.ListRepository
import javax.inject.Inject

class ListRepositoryImpl @Inject constructor(): ListRepository {
    override suspend fun getLists(instanceId: String): List<ListConfig> =
        ListDataGenerator.getListData(instanceId)
}