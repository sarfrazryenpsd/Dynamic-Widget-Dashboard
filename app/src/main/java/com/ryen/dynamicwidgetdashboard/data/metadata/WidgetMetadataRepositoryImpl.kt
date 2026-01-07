package com.ryen.dynamicwidgetdashboard.data.metadata

import com.ryen.dynamicwidgetdashboard.domain.metadata.WidgetMetadataRepository
import com.ryen.dynamicwidgetdashboard.domain.model.WidgetMeta
import javax.inject.Inject

class WidgetMetadataRepositoryImpl @Inject constructor(): WidgetMetadataRepository {
    override suspend fun getWidgetMetadata(): List<WidgetMeta> =
        DashboardMetaProvider.sampleDashboardMetadata
}