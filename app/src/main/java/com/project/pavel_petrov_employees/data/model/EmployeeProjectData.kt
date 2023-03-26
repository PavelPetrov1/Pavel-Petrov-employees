package com.project.pavel_petrov_employees.data.model

import com.project.pavel_petrov_employees.presentation.ui.utils.DateFormatManager
import java.io.Serializable
import java.util.Date

data class EmployeeProjectData(
    val employeeId: Int,
    val projectId: Int,
    val startingDate: Date,
    val nullableEndDate: Date?
) : Serializable {
    val endDate = nullableEndDate ?: Date()

    fun getWorkDays(): Long {
        return DateFormatManager.getDaysBetweenDates(startingDate, endDate)
    }
}
