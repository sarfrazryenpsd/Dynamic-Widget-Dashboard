package com.ryen.dynamicwidgetdashboard.data.metadata

import com.ryen.dynamicwidgetdashboard.domain.model.WidgetMeta

object DashboardMetaProvider {
    val sampleDashboardMetadata = listOf(
        WidgetMeta(type = "banner", instanceId = "pokemon"),
        WidgetMeta(type = "banner", instanceId = "cars"),
        WidgetMeta(type = "list", instanceId = "movies"),
        WidgetMeta(type = "banner", instanceId = "bikes"),
        WidgetMeta(type = "list", instanceId = "shows"),
        WidgetMeta(type = "unknown_widget_type", instanceId = "foo")
    )
}