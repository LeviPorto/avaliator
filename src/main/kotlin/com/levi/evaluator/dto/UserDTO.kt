package com.levi.evaluator.dto

class UserDTO(val id: Int, val name: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserDTO

        if (id != other.id) return false

        return true
    }

}
