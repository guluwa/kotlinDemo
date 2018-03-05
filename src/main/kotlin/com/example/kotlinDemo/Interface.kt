package com.example.kotlinDemo

import org.springframework.data.jpa.repository.JpaRepository

interface StoreRepository : JpaRepository<UserBean, Long> {

    fun findByUsername(username: String):List<UserBean>
}