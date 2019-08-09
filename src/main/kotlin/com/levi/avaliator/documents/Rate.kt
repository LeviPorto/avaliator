package com.levi.avaliator.documents

import com.levi.avaliator.enuns.Improve
import com.levi.avaliator.enuns.RangeTime
import java.io.Serializable
import java.time.Instant
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Rate  (
        @Id val id : UUID,
        @Column val value : Double,
        @Column val restaurantId : Int,
        @Column val orderId : Int,
        @Column val userId : Int,
        @Column val comment : String,
        @Column val date : Instant,
        @Column val arrivedOnTime : Boolean,
        @Column val arrivalRangeTime : RangeTime,
        @Column val canImprove : Improve
) : Serializable