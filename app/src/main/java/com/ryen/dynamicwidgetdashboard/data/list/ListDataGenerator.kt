package com.ryen.dynamicwidgetdashboard.data.list

import com.ryen.dynamicwidgetdashboard.domain.model.ListConfig

object ListDataGenerator {

    fun getListData(instanceId: String): List<ListConfig> {
        return when (instanceId) {
            "movies" -> listOf(
                ListConfig("Harry", "Potter"),
                ListConfig("Tony", "Stark"),
                ListConfig("Bruce", "Wayne")
            )

            "shows" -> listOf(
                ListConfig("Walter", "White"),
                ListConfig("Jesse", "Pinkman"),
                ListConfig("Saul", "Goodman")
            )

            else -> emptyList()
        }
    }
}