package com.dalisyron.data.model

import javax.print.attribute.standard.Destination

data class TripItemEntity(
    val helpjooId : String,
    val source : LatLngEntity,
    val destination: LatLngEntity
)