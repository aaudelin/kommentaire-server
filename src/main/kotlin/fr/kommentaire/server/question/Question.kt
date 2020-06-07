package fr.kommentaire.server.question

import fr.kommentaire.server.user.User
import fr.kommentaire.server.user.UserRole

data class Question(val id: Int, val content: String, val votes: Int) {
    fun user() = User("PSEUDO", "PWD", "TOKEN", "2020-06-07", UserRole.ADMIN)
}