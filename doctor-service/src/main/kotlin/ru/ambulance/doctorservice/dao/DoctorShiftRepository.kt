package ru.ambulance.doctorservice.dao

import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import ru.ambulance.doctorservice.model.entity.DoctorShift

@Repository
interface DoctorShiftRepository : ReactiveCrudRepository<DoctorShift, String> {

    @Modifying
    @Query("UPDATE doctor_shift SET is_active=false WHERE doctor_id = :doctorId")
    fun endShift(@Param("doctorId") doctorId: String): Mono<Void>

    @Query("SELECT EXISTS (SELECT 1 FROM doctor_shift WHERE is_active=true AND doctor_id = :doctorId)")
    fun isExistActiveDoctorShift(@Param("doctorId") doctorId: String): Mono<Boolean>

    fun findFirstByDoctorIdAndIsActiveTrue(doctorId: String): Mono<DoctorShift>
}