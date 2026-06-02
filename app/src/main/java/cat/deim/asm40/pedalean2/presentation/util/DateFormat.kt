package cat.deim.asm40.pedalean2.presentation.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val OUTPUT_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

/**
 * Convierte una fecha ISO-8601 del servidor (p.ej. "2026-06-01T14:10:41.382287Z")
 * en un texto legible: "01/06/2026 16:10".
 * Si el valor es nulo o no se puede parsear, devuelve un texto seguro sin romper la UI.
 */
fun String?.toReadableDateTime(): String {
    if (this.isNullOrBlank()) return "N/A"
    return try {
        val dateTime = try {
            // Formato con zona horaria (termina en Z u offset)
            Instant.parse(this).atZone(ZoneId.systemDefault()).toLocalDateTime()
        } catch (e: Exception) {
            // Formato sin zona
            LocalDateTime.parse(this)
        }
        dateTime.format(OUTPUT_FORMAT)
    } catch (e: Exception) {
        this // último recurso: mostramos el original en vez de petar
    }
}