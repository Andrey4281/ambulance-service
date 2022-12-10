package ru.ambulance.appeal.broker.command

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Mono
import ru.ambulance.appeal.broker.AbstractAppealServiceListener
import ru.ambulance.appeal.model.entity.Appeal
import ru.ambulance.appeal.service.AppealService
import ru.ambulance.broker.events.appeal.UpdateAppealEvent

@Configuration
class AppealCommandListener(private val appealService: AppealService) : AbstractAppealServiceListener<UpdateAppealEvent, Appeal>() {

    @Value("\${kafka.topics.appealCommandTopic}")
    private val appealCommandTopic: String = "appealCommandTopic"

    override fun getErrorObject(): Appeal = Appeal(appealId = "", authorId = "",
            description = "", primaryPatientStatus = "",
            patientId = "", primaryRequiredDoctor = "",
            hospitalId = "")

    override fun getTopic(): String = appealCommandTopic

    override fun getSuccessHandler(value: UpdateAppealEvent): Mono<Appeal> {
        return appealService.findById(value.appealId).flatMap {
            it.isNewObject = false
            it.appealStatus = value.appealStatus.name
            appealService.save(it)
        }
    }

    override fun getEventClass(): Class<UpdateAppealEvent> = UpdateAppealEvent::class.java
}