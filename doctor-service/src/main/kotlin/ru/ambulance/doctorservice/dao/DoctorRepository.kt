package ru.ambulance.doctorservice.dao

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import ru.ambulance.doctorservice.model.entity.Doctor

@Repository
interface DoctorRepository : ReactiveCrudRepository<Doctor, String> {

    @Query("SELECT d.id\n" +
            "FROM doctor d INNER JOIN doctor_shift ds on d.id = ds.doctor_id\n" +
            "WHERE ds.is_active=true AND d.hospital_id = :hospitalId AND d.specialization = :specialization\n" +
            "ORDER BY active_appeal_count LIMIT 1")
    fun findRequiredDoctorWithMinActiveAppeal(@Param("hospitalId") hospitalId: String,
                                              @Param("specialization") specialization: String): Mono<String>
}