package ru.ambulance.appeal.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import ru.ambulance.enums.AppealStatus

@Table("appeal")
data class Appeal(@Id @Column("id") val appealId: String, //  UUID identifier appeal
                  val authorId: String, //  UUID reference to userId
                  val description: String,
                  val primaryPatientStatus: String, //PatientState enum
                  val patientId: String?, //  UUID identifier of patient
                  var currentDoctorId: String? = null, //  UUID identifier of doctor
                  val primaryRequiredDoctor: String, //DoctorSpecialization enum
                  val hospitalId: String, //  UUID identifier of hospital

                  // optional and rewritable fields
                  var appealStatus: String = AppealStatus.NEW.name, //AppealStatus enum
                  var currentCabinetNumber: Int? = null,  // номер текущего кабинета доктора, куда пройти клиенту на осмотр
                  @org.springframework.data.annotation.Transient var isNewObject: Boolean = true
) : Persistable<String> {

    @PersistenceConstructor
    constructor(
            appealId: String,
            authorId: String,
            description: String,
            primaryPatientStatus: String,
            patientId: String?,
            currentDoctorId: String?,
            primaryRequiredDoctor: String,
            hospitalId: String,
            appealStatus: String,
            currentCabinetNumber: Int?
    ) : this(appealId = appealId,
            authorId = authorId,
            description = description,
            primaryPatientStatus = primaryPatientStatus,
            patientId = patientId,
            currentDoctorId = currentDoctorId,
            primaryRequiredDoctor = primaryRequiredDoctor,
            hospitalId = hospitalId,
            appealStatus = appealStatus,
            currentCabinetNumber = currentCabinetNumber,
            isNewObject = true)

    override fun isNew(): Boolean = isNewObject || appealId == null

    override fun getId(): String? = appealId
}
