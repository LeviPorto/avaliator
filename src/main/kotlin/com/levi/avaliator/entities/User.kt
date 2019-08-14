package com.levi.avaliator.entities

import com.levi.avaliator.entities.parents.PersonalContact

class User(val id : Int, override val CPF: String, override val phone: String, override val email: String, override val name: String, override val simpleName: String, override val address: String,
           override val latitude: Double, override val longitude: Double) : PersonalContact(CPF, phone, email, name, simpleName, address, latitude, longitude)