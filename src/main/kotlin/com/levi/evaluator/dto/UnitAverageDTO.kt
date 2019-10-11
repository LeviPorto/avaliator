package com.levi.evaluator.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

class UnitAverageDTO @JsonCreator constructor(@JsonProperty("sum") val sum: Double, @JsonProperty("count") val count: Int) : Serializable
