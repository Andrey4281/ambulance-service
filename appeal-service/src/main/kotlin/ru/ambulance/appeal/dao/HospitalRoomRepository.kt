package ru.ambulance.appeal.dao

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import ru.ambulance.appeal.model.entity.HospitalRoom

interface HospitalRoomRepository : ReactiveCrudRepository<HospitalRoom, String> {
    @Query("SELECT cabinet_number FROM hospital_room WHERE type = :type")
    fun findHospitalRoomNumberByType(@Param("type") type: String) : Mono<Int>
}