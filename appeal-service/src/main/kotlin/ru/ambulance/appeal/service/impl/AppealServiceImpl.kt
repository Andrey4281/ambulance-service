package ru.ambulance.appeal.service.impl

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.ambulance.appeal.broker.outbox.AppealMessageServiceImpl
import ru.ambulance.appeal.dao.AppealRepository
import ru.ambulance.appeal.model.entity.Appeal
import ru.ambulance.appeal.model.exceptions.AppealDoesNotExistException
import ru.ambulance.appeal.model.mapper.toCreatingAppealEvent
import ru.ambulance.appeal.model.rdto.CreateAppealRdto
import ru.ambulance.appeal.service.AppealService
import ru.ambulance.appeal.service.HospitalRoomService
import ru.ambulance.appeal.service.PatientService
import ru.ambulance.enums.AppealStatus
import ru.ambulance.enums.HospitalRoomType
import ru.ambulance.enums.PatientGender
import ru.ambulance.enums.PatientState
import java.time.LocalDate
import java.util.*

@Service
class AppealServiceImpl(private val appealRepository: AppealRepository,
                        private val hospitalRoomService: HospitalRoomService,
                        private val patientService: PatientService,
                        private val appealMessageService: AppealMessageServiceImpl) : AppealService {

    @Value("\${kafka.topics.appealRequestTopic}")
    private val appealRequestTopic: String = "appealRequestTopic"

    override fun findById(appealId: String): Mono<Appeal> = appealRepository.findById(appealId.toString())

    @Transactional
    override fun createNewAppeal(createAppealRdto: CreateAppealRdto): Mono<String> {
        val appealId: String = UUID.randomUUID().toString()
        val appeal = Appeal(appealId = appealId, authorId = createAppealRdto.authorId,
                description = createAppealRdto.description, primaryPatientStatus = createAppealRdto.primaryPatientStatus.name,
                patientId = createAppealRdto.patientId, primaryRequiredDoctor = createAppealRdto.primaryRequiredDoctor.name,
                hospitalId = createAppealRdto.hospitalId)

        val hospitalRoomType: Mono<String> = if (createAppealRdto.hospitalRoomType != null) {
            Mono.just(createAppealRdto.hospitalRoomType.name)
        } else {
            if (createAppealRdto.primaryPatientStatus == PatientState.EXTREMELY_SERIOUS
                    || createAppealRdto.primaryPatientStatus == PatientState.AGONY
                    || createAppealRdto.primaryPatientStatus == PatientState.CLINICAL_DEATH) {
                Mono.just(HospitalRoomType.URGENT_ROOM.name)
            } else if (createAppealRdto.patientId != null) {
                patientService.findPatientById(createAppealRdto.patientId).map {
                    if (LocalDate.now().year - it.birthDate.year < 18) {
                        HospitalRoomType.CHILD_ROOM.name
                    } else if (it.gender == PatientGender.MAN.name) {
                        HospitalRoomType.MAN_ROOM.name
                    } else {
                        HospitalRoomType.FEMALE_ROOM.name
                    }
                }
            } else {
                Mono.just(HospitalRoomType.MAN_ROOM.name)
            }
        }

        return hospitalRoomType.flatMap {
            hospitalRoomService.findHospitalRoomNumberByType(it)
        }.flatMap {
            appeal.currentCabinetNumber = it
            appealRepository.save(appeal)
        }.map { it.toCreatingAppealEvent() }.flatMap { appealMessageService.sendMessage(null, appealRequestTopic, it) }
                .map { appealId }
    }

    override fun save(appeal: Appeal): Mono<Appeal> = appealRepository.save(appeal)

    override fun showAppealList(appealStatues: List<String>?, appealIds: List<String>?, doctorId: String?): Flux<Appeal> = appealRepository.showAppealList(appealStatues, appealIds, doctorId)

    override fun updateAppealStatus(doctorId: String,
                                    appealId: String,
                                    appealStatus: AppealStatus): Mono<String> {

        return appealRepository.findFirstByAppealIdAndCurrentDoctorId(appealId = appealId, currentDoctorId = doctorId).flatMap {
            if (it == null) {
                Mono.error(AppealDoesNotExistException("Appeal with does not exist"))
            } else {
                it.isNewObject = false
                it.appealStatus = appealStatus.name
                appealRepository.save(it)
            }
        }.map { it.appealId }
    }
}