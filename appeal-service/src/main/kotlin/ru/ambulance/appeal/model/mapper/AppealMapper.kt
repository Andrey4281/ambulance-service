package ru.ambulance.appeal.model.mapper

import ru.ambulance.appeal.model.entity.Appeal
import ru.ambulance.appeal.model.dto.AppealDto
import ru.ambulance.broker.events.appeal.CreatingAppealEvent
import ru.ambulance.enums.AppealStatus
import ru.ambulance.enums.DoctorSpecialization
import ru.ambulance.enums.PatientState

fun Appeal.toDto(): AppealDto = AppealDto(appealId, authorId, description, PatientState.valueOf(primaryPatientStatus), patientId, currentDoctorId, DoctorSpecialization.valueOf(primaryRequiredDoctor), hospitalId, AppealStatus.valueOf(appealStatus), currentCabinetNumber)
fun Appeal.toCreatingAppealEvent(): CreatingAppealEvent = CreatingAppealEvent(eventId = appealId, primaryRequiredDoctor = DoctorSpecialization.valueOf(primaryRequiredDoctor))
fun AppealDto.toEntity(): Appeal = Appeal(id, authorId, description, primaryPatientStatus.name, patientId, currentDoctorId, primaryRequiredDoctor.name, hospitald, appealStatus.name, currentCabinetNumber)
