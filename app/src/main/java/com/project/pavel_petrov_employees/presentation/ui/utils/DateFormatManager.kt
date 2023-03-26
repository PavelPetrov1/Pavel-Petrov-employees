package com.project.pavel_petrov_employees.presentation.ui.utils

import com.project.pavel_petrov_employees.data.model.EmployeeProjectData
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object DateFormatManager {
    const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd"

    fun String.asDate(format: String): Date {
        return try {
            val dateFormatDefaultLocale = SimpleDateFormat(format, Locale.getDefault())
            val parsedTimeInFormat = dateFormatDefaultLocale.parse(this)

            return parsedTimeInFormat
        } catch (e: ParseException) {
            val parseErrorMessage = e.cause.toString()
            println(parseErrorMessage)

            Date()
        }
    }

    fun getDaysWorkedTogether(
        firstEmployee: EmployeeProjectData,
        matchingEmployee: EmployeeProjectData
    ): Long? {
        val firstEmployeeDays = firstEmployee.getWorkDays()
        val matchingEmployeeDays = matchingEmployee.getWorkDays()
        val overlapDays = getOverlapDays(firstEmployee, matchingEmployee)

        if (overlapDays > 0) {
            return minOf(firstEmployeeDays, matchingEmployeeDays, overlapDays)
        }

        return null
    }

    private fun getOverlapDays(
        firstEmployee: EmployeeProjectData,
        matchingEmployee: EmployeeProjectData
    ): Long {
        val overlapStart = maxOf(firstEmployee.startingDate, matchingEmployee.startingDate)
        val overlapEnd = minOf(firstEmployee.endDate, matchingEmployee.endDate)

        return getDaysBetweenDates(overlapStart, overlapEnd)
    }

    fun getDaysBetweenDates(startDate: Date, endDate: Date): Long {
        val diffInMill = endDate.time - startDate.time
        return TimeUnit.DAYS.convert(diffInMill, TimeUnit.MILLISECONDS)
    }
}
