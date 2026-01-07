package com.ryen.dynamicwidgetdashboard.data.banner

import com.ryen.dynamicwidgetdashboard.domain.model.BannerConfig
import com.ryen.dynamicwidgetdashboard.domain.banner.BannerRepository
import javax.inject.Inject

class BannerRepositoryImpl @Inject constructor(): BannerRepository {
    override suspend fun getBanners(instanceId: String): List<BannerConfig> =
        BannerDataGenerator.getBanner(instanceId)
}