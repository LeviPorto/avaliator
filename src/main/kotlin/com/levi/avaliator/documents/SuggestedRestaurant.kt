package com.levi.avaliator.documents

import java.util.*

class SuggestedRestaurant (val id : UUID,
                           val name : String,
                           val address : String,
                           val count : Int,
                           val phone : String) {

    constructor(id: UUID, name: String, address : String, count : Int, phone : String, increment : Int)
            : this (id, name, address, count + increment, phone)

}