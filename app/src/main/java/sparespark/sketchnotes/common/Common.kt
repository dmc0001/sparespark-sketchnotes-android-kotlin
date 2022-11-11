package sparespark.sketchnotes.common

import android.annotation.SuppressLint
import sparespark.sketchnotes.data.model.Color
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun getCalendarTime(): String {
    //d MMM yyyy HH:mm:ss Z full format sorted by..
    val cal = Calendar.getInstance(TimeZone.getDefault())
    val format = SimpleDateFormat("d MMM yyyy HH:mm:ss Z")
    format.timeZone = cal.timeZone
    return format.format(cal.time)
}

