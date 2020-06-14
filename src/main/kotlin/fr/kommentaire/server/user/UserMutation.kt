package fr.kommentaire.server.user

import com.expediagroup.graphql.spring.operations.Mutation
import fr.kommentaire.server.HttpException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpStatusCodeException

@Component
class UserMutation(
        val userRepository: UserRepository,
        val userService: UserService
) : Mutation {

    fun createUser(pseudo: String, pwd: String) : User {
        if (userRepository.findUserFromPseudo(pseudo) != null) throw HttpException(HttpStatus.BAD_REQUEST, "Pseudo $pseudo already exists")
        val user = User(0, pseudo, userService.encodePassword(pwd), userService.generateUserToken(), userService.refreshTokenDate(), UserRole.USER)
        val userId = userRepository.insertUser(user)
        return user.copy(id = userId ?: 0)
    }

    fun authenticateUser(pseudo: String, pwd: String): User {
        val userDB = userRepository.findUserFromPseudo(pseudo) ?: throw HttpException(HttpStatus.BAD_REQUEST, "Pseudo $pseudo does not exist")
        if (!userService.matchPassword(userDB.pwd, pwd)) throw HttpException(HttpStatus.BAD_REQUEST, "Wrong password")

        return userDB.copy(
                token = userService.generateUserToken(),
                dateToken = userService.refreshTokenDate()
        )
    }
}