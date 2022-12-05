package ru.ambulance.appeal.broker.saga

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import ru.ambulance.appeal.broker.AbstractAppealServiceListener
import ru.ambulance.appeal.model.entity.Appeal
import ru.ambulance.appeal.service.AppealService
import ru.ambulance.broker.events.appeal.DoctorResponseOnCreatingAppealEvent
import ru.ambulance.enums.AppealStatus

@Configuration
class CreatingAppealSagaDoctorResponseListener : AbstractAppealServiceListener<DoctorResponseOnCreatingAppealEvent, Appeal>() {

    @Value("\${kafka.topics.appealResponseTopic}")
    private val appealResponseTopic: String = "appealResponseTopic"

    @Autowired
    private lateinit var appealService: AppealService;

    override fun getTopic(): String = appealResponseTopic

    @Transactional
    override fun getSuccessHandler(responseEvent: DoctorResponseOnCreatingAppealEvent): Mono<Appeal> {
        return responseEvent.appealId.let { appealService.findById(it) }.flatMap {
            it.isNewObject = false
            if (responseEvent.isSuccess) {
                it.appealStatus = AppealStatus.ASSIGNED.name
                it.currentDoctorId = responseEvent.doctorId
            } else {
                it.appealStatus = responseEvent.appealStatus.name
            }
            appealService.save(it)
        }
    }

    override fun getEventClass(): Class<DoctorResponseOnCreatingAppealEvent> = DoctorResponseOnCreatingAppealEvent::class.java

    override fun getErrorObject(): Appeal = Appeal(appealId = "", authorId = "",
            description = "", primaryPatientStatus = "",
            patientId = "", primaryRequiredDoctor = "",
            hospitalId = "")
}