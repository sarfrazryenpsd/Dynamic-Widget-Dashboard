package com.ryen.dynamicwidgetdashboard.domain.metadata

import com.ryen.dynamicwidgetdashboard.domain.model.WidgetMeta

interface WidgetMetadataRepository {

    suspend fun getWidgetMetadata(): List<WidgetMeta>

}