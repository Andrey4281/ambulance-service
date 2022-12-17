package ru.ambulance.authservice.service.impl

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.ambulance.authservice.dao.AuthUserRepository
import ru.ambulance.authservice.dao.UserRoleRepository
import ru.ambulance.authservice.model.dto.UserLoginResponse
import ru.ambulance.authservice.model.exceptions.UserAlreadyExistException
import ru.ambulance.authservice.model.exceptions.UserDoesNotExistException
import ru.ambulance.authservice.model.mapper.initPassword
import ru.ambulance.authservice.model.mapper.toUser
import ru.ambulance.authservice.model.rdto.CreateUserRdto
import ru.ambulance.authservice.model.rdto.UserLoginRdto
import ru.ambulance.authservice.service.AuthUserService
import ru.ambulance.authservice.util.JwtTokenService
import ru.ambulance.authservice.util.PasswordUtil

@Service
class AuthUserServiceImpl(private val userRepository: AuthUserRepository,
                          private val userRoleRepository: UserRoleRepository,
                          private val passwrodUtil: PasswordUtil,
                          private val jwtTokenService: JwtTokenService) : AuthUserService {

    override fun signUp(userRdto: CreateUserRdto): Mono<String> {
        return userRepository.findFirstByLogin(userRdto.login).flatMap<String?> {
            Mono.error(UserAlreadyExistException(String.format("User with login %s already exists exception", userRdto.login)))
        }.switchIfEmpty(userRepository.save(userRdto.toUser().initPassword(passwrodUtil.getSecuredPassword(userRdto.password)!!)).log()
                .map { it.login })
    }

    override fun signIn(userLoginRdto: UserLoginRdto): Mono<UserLoginResponse> {
        return userRepository.findFirstByLogin(userLoginRdto.login).flatMap {
            if (it != null) {
               if (it.password.equals(passwrodUtil.getSecuredPassword(userLoginRdto.password))) {
                   userRoleRepository.findById(it.roleId).flatMap { Mono.just(UserLoginResponse(isSuccess = true,
                           token = jwtTokenService.generate(userLoginRdto.login, it.name))) }
               } else {
                   Mono.just(UserLoginResponse(isSuccess = false, token = null))
               }
            } else {
                Mono.error(UserDoesNotExistException(String.format("User with login %s already exists exception", userLoginRdto.login)))
            }
        }
    }
}