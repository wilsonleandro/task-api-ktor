package br.com.task.utils

import at.favre.lib.crypto.bcrypt.BCrypt
import br.com.task.utils.Constants.PASSWORD_HASH_CONST

fun generatedHash(value: String): String {
    return BCrypt.withDefaults()
        .hashToString(PASSWORD_HASH_CONST, value.toCharArray())
        .toString()
}

fun verifyHash(value: String, password: String): BCrypt.Result {
    return BCrypt.verifyer().verify(password.toCharArray(), value)
}
