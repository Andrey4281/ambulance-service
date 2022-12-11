package ru.ambulance.doctorservice.service.impl

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.ambulance.doctorservice.dao.DoctorRepository
import ru.ambulance.doctorservice.service.DoctorService

@Service
class DoctorServiceImpl(private val doctorRepository: DoctorRepository) : DoctorService {
    override fun findRequiredDoctorWithMinActiveAppeal(hospitalId: String, specialization: String): Mono<String> =
            doctorRepository.findRequiredDoctorWithMinActiveAppeal(hospitalId = hospitalId, specialization = specialization)
}