package com.ryen.dynamicwidgetdashboard.domain.list

import com.ryen.dynamicwidgetdashboard.domain.model.ListConfig

interface ListRepository {

    suspend fun getLists(instanceId: String): List<ListConfig>

}