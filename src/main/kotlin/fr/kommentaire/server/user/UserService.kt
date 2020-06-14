package fr.kommentaire.server.user

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import java.util.Date

@Component
class UserService(val userRepository: UserRepository, val passwordEncoder: PasswordEncoder) {

    fun generateUserToken() = (1..18).map { ('0'..'z').toList().toTypedArray().random() }.joinToString("") + SimpleDateFormat("yyyyMMddHHmmss").format(Date())

    fun encodePassword(pwd: String) = passwordEncoder.encode(pwd)

    fun refreshTokenDate() = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Date())

    fun matchPassword(databasePassword: String, inputPassword: String) = passwordEncoder.matches(inputPassword, databasePassword)


}