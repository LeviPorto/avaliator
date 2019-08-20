package com.levi.avaliator.documents

import com.levi.avaliator.enuns.Improve
import com.levi.avaliator.enuns.RangeTime
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.time.Instant
import java.util.*

@Table
class Rate  (
        @PrimaryKey val id : UUID,
        @Column val value : Double,
        @Column val restaurantId : Int,
        @Column val orderId : Int,
        @Column val userId : Int,
        @Column val comment : String,
        @Column val date : Instant,
        @Column val arrivedOnTime : Boolean,
        @Column val arrivalRangeTime : RangeTime,
        @Column val canImprove : Improve
)