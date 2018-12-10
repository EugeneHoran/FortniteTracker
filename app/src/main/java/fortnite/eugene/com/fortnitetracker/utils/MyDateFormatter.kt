package fortnite.eugene.com.fortnitetracker.utils

import java.text.SimpleDateFormat
import java.util.*

const val SECOND_MILLIS = 1000
const val MINUTE_MILLIS = 60 * SECOND_MILLIS
const val HOUR_MILLIS = 60 * MINUTE_MILLIS
const val DAY_MILLIS = 24 * HOUR_MILLIS

fun String.toDate(
        dateFormat: String = "yyyy-MM-dd'T'HH:mm:ss.SSS",
        timeZone: TimeZone = TimeZone.getTimeZone("UTC")
): Date {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    parser.timeZone = timeZone
    return parser.parse(this)
}


fun Date.formatTo(
        dateFormat: String,
        timeZone: TimeZone = TimeZone.getDefault()
): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = timeZone
    return formatter.format(this)
}

fun Date.formatToTimeAgo(): String {
    var time = this.time
    if (time < 1000000000000L) {
        time *= 1000
    }
    val now = Date().time
    if (time > now || time <= 0) {
        return "in the future"
    }
    val diff = now - time
    return if (diff < MINUTE_MILLIS) {
        "moments ago"
    } else if (diff < 2 * MINUTE_MILLIS) {
        "a minute ago"
    } else if (diff < 60 * MINUTE_MILLIS) {
        (diff / MINUTE_MILLIS).toString() + " minutes ago"
    } else if (diff < 2 * HOUR_MILLIS) {
        "an hour ago"
    } else if (diff < 24 * HOUR_MILLIS) {
        (diff / HOUR_MILLIS).toString() + " hours ago"
    } else if (diff < 48 * HOUR_MILLIS) {
        "yesterday"
    } else {
        (diff / DAY_MILLIS).toString() + " days ago"
    }
}