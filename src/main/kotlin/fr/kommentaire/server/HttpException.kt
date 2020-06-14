package fr.kommentaire.server

import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpStatusCodeException

class HttpException(httpStatus: HttpStatus, reason: String) : HttpStatusCodeException(httpStatus, reason)