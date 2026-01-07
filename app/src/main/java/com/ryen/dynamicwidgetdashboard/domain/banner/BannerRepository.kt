package com.ryen.dynamicwidgetdashboard.domain.banner

import com.ryen.dynamicwidgetdashboard.domain.model.BannerConfig

interface BannerRepository {

    suspend fun getBanners(instanceId: String): List<BannerConfig>

}