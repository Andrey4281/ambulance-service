package ru.ambulance.appeal.service.impl

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.ambulance.appeal.dao.HospitalRoomRepository
import ru.ambulance.appeal.service.HospitalRoomService

@Service
class HospitalRoomServiceImpl(private val hospitalRoomRepository: HospitalRoomRepository) : HospitalRoomService {

    override fun findHospitalRoomNumberByType(type: String): Mono<Int> = hospitalRoomRepository.findHospitalRoomNumberByType(type)
}