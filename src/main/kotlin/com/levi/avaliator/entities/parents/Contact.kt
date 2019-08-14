package com.levi.avaliator.entities.parents

abstract class Contact(open val phone : String, open val email :  String, open val name : String, open val simpleName : String, open val address : String,
                       latitude: Double, longitude: Double) : LocalizedEntity(latitude, longitude)