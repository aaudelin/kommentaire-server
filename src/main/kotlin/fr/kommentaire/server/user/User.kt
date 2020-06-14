package fr.kommentaire.server.user

import fr.kommentaire.server.question.Question

data class User(val id: Int, val pseudo: String, val pwd: String, val token: String, val dateToken: String, val role: UserRole) {
    fun question() = listOf(Question(1,"My question user", 10))
}

enum class UserRole {
    USER,
    ADMIN
}
