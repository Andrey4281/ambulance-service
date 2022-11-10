package ru.ambulance.appeal.model.mapper

import ru.ambulance.appeal.model.Appeal
import ru.ambulance.appeal.model.dto.AppealDto

fun Appeal.toDto(): AppealDto = AppealDto(id, authorId, description, primaryPatientStatus, patientId, currentDoctorId, primaryRequiredDoctor, hospitald, appealStatus, currentCabinetNumber)
fun AppealDto.toEntity(): Appeal = Appeal(id, authorId, description, primaryPatientStatus, patientId, currentDoctorId, primaryRequiredDoctor, hospitald, appealStatus, currentCabinetNumber)
