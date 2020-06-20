package fr.kommentaire.server.user

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository


@Repository
class UserRepository(val jdbcTemplate: JdbcTemplate, val userJdbcInsert: SimpleJdbcInsert) {

    fun updateToken(user: User) : Int {
        return jdbcTemplate.update("UPDATE USER SET token='${user.token}', DATETOKEN='${user.dateToken}' where id=${user.id}")
    }

    fun insertUser(user: User): Int? {
        return userJdbcInsert
                .executeAndReturnKey(MapSqlParameterSource().apply {
                    addValue("pseudo", user.pseudo)
                    addValue("pwd", user.pwd)
                    addValue("token", user.token)
                    addValue("DATETOKEN", user.dateToken)
                    addValue("role", user.role.name)
                }).toInt()
    }

    fun findUserFromId(userId: Int): User? {
        return jdbcTemplate.query("SELECT * FROM USER WHERE id=$userId") { rs, rowNum ->
            val userRole = if (rs.getString(5) == "ADMIN") UserRole.ADMIN else UserRole.USER
            User(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getString(6), rs.getString(3), userRole)
        }.getOrNull(0)
    }

    fun findUserFromPseudo(pseudo: String): User? {
        return jdbcTemplate.query("SELECT * FROM USER WHERE pseudo='$pseudo'") { rs, rowNum ->
            val userRole = if (rs.getString(5) == "ADMIN") UserRole.ADMIN else UserRole.USER
            User(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getString(6), rs.getString(3), userRole)
        }.getOrNull(0)
    }

    fun findUserFromToken(token: String): User? {
        return jdbcTemplate.query("SELECT * FROM USER WHERE token='$token'") { rs, rowNum ->
            val userRole = if (rs.getString(5) == "ADMIN") UserRole.ADMIN else UserRole.USER
            User(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getString(6), rs.getString(3), userRole)
        }.getOrNull(0)
    }

    fun findAll(): List<User?>? {
        return jdbcTemplate.query("SELECT * FROM USER") { rs, rowNum ->
            val userRole = if (rs.getString(5) == "ADMIN") UserRole.ADMIN else UserRole.USER
            User(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getString(6), rs.getString(3), userRole)
        }
    }

}