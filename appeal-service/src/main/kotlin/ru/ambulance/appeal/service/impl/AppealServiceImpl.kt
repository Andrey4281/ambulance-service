package ru.ambulance.appeal.service.impl

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.ambulance.appeal.dao.AppealRepository
import ru.ambulance.appeal.model.dto.AppealDto
import ru.ambulance.appeal.model.mapper.toDto
import ru.ambulance.appeal.service.AppealService
import java.util.*

@Service
class AppealServiceImpl(val appealRepository: AppealRepository) : AppealService {
    override fun findById(appealId: UUID): Mono<AppealDto> = appealRepository
        .findById(appealId.toString())
        .map { it.toDto() }

    override fun findAll(): Flux<AppealDto> {
        return appealRepository
            .findAll()
            .map { it.toDto() }
    }

}