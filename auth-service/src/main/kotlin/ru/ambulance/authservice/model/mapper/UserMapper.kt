package ru.ambulance.authservice.model.mapper

import ru.ambulance.authservice.model.entity.AuthUser
import ru.ambulance.authservice.model.rdto.CreateUserRdto
import java.util.UUID

fun CreateUserRdto.toUser(): AuthUser = AuthUser(userId = UUID.randomUUID().toString(), login = login,
        phone = phone, email = email, roleId = roleId, isNewObject = true, password = null)
fun AuthUser.initPassword(password: String) = AuthUser(userId = userId, login = login, phone = phone, email = email, roleId = roleId, isNewObject = true, password = password)