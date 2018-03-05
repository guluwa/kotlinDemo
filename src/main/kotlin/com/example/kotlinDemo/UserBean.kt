package com.example.kotlinDemo

import javax.persistence.*

@Entity
@Table(name = "user")
data class UserBean(
        var username: String = "",
        var password: String = "",

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0
)