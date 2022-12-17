package ru.ambulance.authservice.dao

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import ru.ambulance.authservice.model.entity.Role

@Repository
interface UserRoleRepository : ReactiveCrudRepository<Role, String>