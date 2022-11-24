package ru.ambulance.appeal.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import ru.ambulance.enums.AppealStatus
import ru.ambulance.enums.DoctorSpecialization
import ru.ambulance.enums.PatientState

@Table("appeal")
data class Appeal(@Id val id: String, //  UUID identifier appeal
                  val authorId: String, //  UUID reference to userId
                  val description: String,
                  val primaryPatientStatus: PatientState,
                  val patientId: String, //  UUID identifier of patient
                  val currentDoctorId: String, //  UUID identifier of doctor
                  val primaryRequiredDoctor: DoctorSpecialization,
                  val hospitald: String, //  UUID identifier of hospital

                  // optional and rewritable fields
                  var appealStatus: AppealStatus = AppealStatus.NEW,
                  var currentCabinetNumber: Long  // номер текущего кабинета доктора, куда пройти клиенту на осмотр
)
