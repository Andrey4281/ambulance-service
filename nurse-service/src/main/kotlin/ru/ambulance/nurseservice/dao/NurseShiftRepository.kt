package ru.ambulance.nurseservice.dao

import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import ru.ambulance.nurseservice.model.entity.NurseShift

@Repository
interface NurseShiftRepository : ReactiveCrudRepository<NurseShift, String> {
    @Query("SELECT EXISTS (SELECT 1 FROM nurse_shift WHERE is_active=true AND nurse_id = :nurseId)")
    fun isExistActiveNurseShift(@Param("nurseId") nurseId: String): Mono<Boolean>

    @Modifying
    @Query("UPDATE nurse_shift SET is_active=false WHERE nurse_id = :nurseId")
    fun endShift(@Param("nurseId") nurseId: String): Mono<Void>

    fun findFirstByNurseIdAndIsActiveTrue(nurseId: String): Mono<NurseShift>
}