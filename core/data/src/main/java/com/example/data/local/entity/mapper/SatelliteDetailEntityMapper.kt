package com.example.data.local.entity.mapper

import com.example.data.local.entity.SatelliteDetailEntity
import com.example.model.SatelliteDetailModel

object SatelliteDetailEntityMapper :EntityMapper<SatelliteDetailModel,SatelliteDetailEntity> {

    override fun asEntity(domain: SatelliteDetailModel): SatelliteDetailEntity {
        return SatelliteDetailEntity(
            id = domain.id,
            costPerLaunch = domain.cost_per_launch,
            firstFlight = domain.first_flight,
            height = domain.height,
            mass = domain.mass
        )
    }

    override fun asDomain(entity: SatelliteDetailEntity) :SatelliteDetailModel {
        return SatelliteDetailModel(
            id = entity.id,
            cost_per_launch = entity.costPerLaunch,
            first_flight = entity.firstFlight,
            height = entity.height,
            mass = entity.mass
        )
    }
}