package com.ryen.dynamicwidgetdashboard.data.banner

import com.ryen.dynamicwidgetdashboard.domain.model.BannerConfig

object BannerDataGenerator {

    fun getBanner(instanceId: String): List<BannerConfig> {
        return when (instanceId) {
            "pokemon" -> listOf(
                BannerConfig("Pikachu", "Electric type"),
                BannerConfig("Charizard", "Fire & Flying type")
            )

            "cars" -> listOf(
                BannerConfig("Tesla", "Electric performance"),
                BannerConfig("BMW", "Ultimate driving machine")
            )

            "bikes" -> listOf(
                BannerConfig("Ducati", "Born to race")
            )

            else -> emptyList()
        }
    }
}