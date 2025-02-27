package com.example.data.local.entity.mapper

interface EntityMapper<Domain,Entity> {

    fun asEntity(domain: Domain) :Entity

    fun asDomain(entity: Entity) : Domain
}