package com.lamti.thechase.data.models

data class User(
    val userId: Int,
    val email: String,
    val displayName: String,
    val passwordHash: String
) : java.io.Serializable
