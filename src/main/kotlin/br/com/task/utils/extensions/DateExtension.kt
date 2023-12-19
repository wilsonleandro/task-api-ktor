package br.com.task.utils.extensions

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

private val zoneId = ZoneId.of("America/Sao_Paulo")
val dateFormat = DateTimeFormatter
    .ofPattern("EEE, dd MMM yyyy HH:mm:ss z")
    .withLocale(Locale("pt", "br"))
    .withZone(zoneId)

fun LocalDateTime.toDateString(): String =
    dateFormat.format(this)
