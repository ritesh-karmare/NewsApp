package rk.information.news.utils.dateUtils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    @JvmStatic
    @Throws(ParseException::class)
    fun timeAgo(dateToCompareStr: String?): String {
        if (dateToCompareStr.isNullOrEmpty()) return ""
        val currentDate = Date()
        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        val dateToCompare = input.parse(dateToCompareStr)
        val milliSecPerMinute = (60 * 1000).toLong() //Milliseconds Per Minute
        val milliSecPerHour = milliSecPerMinute * 60 //Milliseconds Per Hour
        val milliSecPerDay = milliSecPerHour * 24 //Milliseconds Per Day
        val milliSecPerMonth = milliSecPerDay * 30 //Milliseconds Per Month
        val milliSecPerYear = milliSecPerDay * 365 //Milliseconds Per Year
        //Difference in Milliseconds between two dates
        val msExpired = currentDate.time - dateToCompare.time
        //Second or Seconds ago calculation
        return if (msExpired < milliSecPerMinute) {
            if (Math.round((msExpired / 1000).toFloat()) == 1) {
                Math.round((msExpired / 1000).toFloat()).toString() + " second ago... "
            } else {
                Math.round((msExpired / 1000).toFloat()).toString() + " seconds ago..."
            }
        } else if (msExpired < milliSecPerHour) {
            if (Math.round((msExpired / milliSecPerMinute).toFloat()) == 1) {
                Math.round((msExpired / milliSecPerMinute).toFloat()).toString() + " minute ago... "
            } else {
                Math.round((msExpired / milliSecPerMinute).toFloat()).toString() + " minutes ago... "
            }
        } else if (msExpired < milliSecPerDay) {
            if (Math.round((msExpired / milliSecPerHour).toFloat()) == 1) {
                Math.round((msExpired / milliSecPerHour).toFloat()).toString() + " hour ago... "
            } else {
                Math.round((msExpired / milliSecPerHour).toFloat()).toString() + " hours ago... "
            }
        } else if (msExpired < milliSecPerMonth) {
            if (Math.round((msExpired / milliSecPerDay).toFloat()) == 1) {
                Math.round((msExpired / milliSecPerDay).toFloat()).toString() + " day ago... "
            } else {
                Math.round((msExpired / milliSecPerDay).toFloat()).toString() + " days ago... "
            }
        } else if (msExpired < milliSecPerYear) {
            if (Math.round((msExpired / milliSecPerMonth).toFloat()) == 1) {
                Math.round((msExpired / milliSecPerMonth).toFloat()).toString() + "  month ago... "
            } else {
                Math.round((msExpired / milliSecPerMonth).toFloat()).toString() + "  months ago... "
            }
        } else {
            if (Math.round((msExpired / milliSecPerYear).toFloat()) == 1) {
                Math.round((msExpired / milliSecPerYear).toFloat()).toString() + " year ago..."
            } else {
                Math.round((msExpired / milliSecPerYear).toFloat()).toString() + " years ago..."
            }
        }
    }

}