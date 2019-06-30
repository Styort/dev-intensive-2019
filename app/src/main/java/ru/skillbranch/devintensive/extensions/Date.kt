package ru.skillbranch.devintensive.extensions

import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*
import javax.xml.datatype.DatatypeConstants.DAYS
import javax.xml.datatype.DatatypeConstants.TIME
import kotlin.math.abs


const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR
const val MONTH = 30 * DAY
const val YEAR = 12 * MONTH


fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
        TimeUnits.MONTH -> value * MONTH
        TimeUnits.YEAR -> value * YEAR
    }
    this.time = time
    return this
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY,
    MONTH,
    YEAR
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val diffInMillies = ((this.time - date.time) / SECOND).toInt()

    val minute = 60
    val hour = 60 * 60
    val day = 24 * hour

    return when (diffInMillies) {
        in -45..-1 -> "несколько секунд назад"
        in -75..-45 -> "минуту назад"
        in -45 * minute..-75 -> "${minutesAsPlular(abs(diffInMillies / 60))} назад"
        in -75 * minute..-45 * minute -> "час назад"
        in -22 * hour..-75 * minute -> "${hoursAsPlular(abs(diffInMillies / 60 / 60))} назад"
        in -26 * hour..-22 * hour -> "день назад"
        in -360 * day..-26 * hour -> "${daysAsPlular(abs(diffInMillies / 60 / 60 / 24))} назад"
        in -Int.MAX_VALUE..-360 * day -> "более года назад"

        in -1..1 -> "только что"

        in 1..45 -> "через несколько секунд"
        in 45..75 -> "через минуту"
        in 75..45 * minute -> "через ${minutesAsPlular(diffInMillies / 60)}"
        in 45 * minute..75 * minute -> "через час"
        in 75 * minute..22 * hour -> "через ${hoursAsPlular(diffInMillies / 60 / 60)}"
        in 22 * hour..26 * hour -> "через день"
        in 26 * hour..360 * day -> "через ${daysAsPlular(diffInMillies / 60 / 60 / 24)}"
        in 360 * day..Int.MAX_VALUE -> "более чем через год"

        else -> "более года назад"
    }
}

enum class Plular {
    ONE,
    FEW,
    MANY
}

private fun minutesAsPlular(value: Int) = when (value.asPlulars()) {
    Plular.ONE -> "$value минуту"
    Plular.FEW -> "$value минуты"
    Plular.MANY -> "$value минут"
}

private fun hoursAsPlular(value: Int) = when (value.asPlulars()) {
    Plular.ONE -> "$value час"
    Plular.FEW -> "$value часа"
    Plular.MANY -> "$value часов"
}

private fun daysAsPlular(value: Int) = when (value.asPlulars()) {
    Plular.ONE -> "$value день"
    Plular.FEW -> "$value дня"
    Plular.MANY -> "$value дней"
}

fun Int.asPlulars() : Plular{
    if (this > 10 && this % 100 / 10 == 1) return Plular.MANY
    if (this == 0 || this == 1) return Plular.ONE

    return when (this % 10) {
        1 -> Plular.ONE
        2, 3, 4 -> Plular.FEW
        else -> Plular.MANY
    }
}