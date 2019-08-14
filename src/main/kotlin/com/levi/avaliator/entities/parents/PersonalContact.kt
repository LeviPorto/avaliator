package com.levi.avaliator.entities.parents

abstract class PersonalContact(open val CPF : String, override val phone: String, override val email: String, override val name: String, override val simpleName: String, override val address: String, override val latitude: Double, override val longitude: Double)
    : Contact(phone, email, name, simpleName, address, latitude, longitude)