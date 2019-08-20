package com.levi.avaliator.documents

import org.springframework.data.annotation.Id
import org.springframework.data.cassandra.core.mapping.Column
import java.io.Serializable
import java.util.*

class SuggestedRestaurant (@Id val id : UUID,
                           @Column val name : String,
                           @Column val address : String,
                           @Column val count : Int,
                           @Column val phone : String) : Serializable {

    constructor(id: UUID, name: String, address : String, count : Int, phone : String, increment : Int)
            : this (id, name, address, count + increment, phone)

}