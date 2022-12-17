package ru.ambulance.authservice.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("auth_user")
data class AuthUser(
        @Id @Column("id") val userId: String,
        val login: String,
        val phone: String,
        var password: String?,
        val email: String,
        @Column("role_id") val roleId: String,
        @org.springframework.data.annotation.Transient var isNewObject: Boolean = true
) : Persistable<String> {

    override fun getId(): String = userId

    override fun isNew(): Boolean = isNewObject

    @PersistenceConstructor
    constructor(
            userId: String,
            login: String,
            phone: String,
            password: String,
            email: String,
            roleId: String
    ) : this(
            userId = userId,
            login = login,
            phone = phone,
            password = password,
            email = email,
            roleId = roleId,
            isNewObject = true
    )
}