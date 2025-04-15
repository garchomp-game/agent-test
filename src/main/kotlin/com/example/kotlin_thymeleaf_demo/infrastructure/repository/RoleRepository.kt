\
package com.example.kotlin_thymeleaf_demo.infrastructure.repository

import com.example.kotlin_thymeleaf_demo.domain.user.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<Role, Int> {
    fun findByName(name: String): Role?
}
