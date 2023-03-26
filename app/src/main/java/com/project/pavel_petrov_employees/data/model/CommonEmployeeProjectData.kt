package com.project.pavel_petrov_employees.data.model

import com.project.pavel_petrov_employees.presentation.ui.utils.DateFormatManager
import java.io.Serializable

data class CommonEmployeeProjectData(
    val employeeId: Int,
    val matchingEmployeeId: Int,
    val projectId: Int,
    val daysWorked: String
) : Serializable {

    companion object {

        private const val EMPLOYEE_NON_MATCHING_DATES = "Worked on different dates"

        fun calculateCommonProjects(listOfEmployeeProjectData: List<EmployeeProjectData>): List<CommonEmployeeProjectData> {
            return listOfEmployeeProjectData.flatMap { employeeProjectData ->
                val commonEmployeesForProject =
                    listOfEmployeeProjectData.filter { matchingEmployeeProjectData ->
                        doProjectAndEmployeeIdsMatch(
                            employeeProjectData,
                            matchingEmployeeProjectData
                        )
                    }

                commonEmployeesForProject.map { matchingEmployeeProjectData ->
                    val daysWorkedTogether =
                        getDaysWorkedTogether(employeeProjectData, matchingEmployeeProjectData)

                    CommonEmployeeProjectData(
                        employeeProjectData.employeeId,
                        matchingEmployeeProjectData.employeeId,
                        employeeProjectData.projectId,
                        daysWorkedTogether
                    )
                }
            }
        }

        private fun doProjectAndEmployeeIdsMatch(
            employeeProjectData: EmployeeProjectData,
            matchingEmployeeProjectData: EmployeeProjectData
        ): Boolean {
            val projectIdMatches =
                employeeProjectData.projectId == matchingEmployeeProjectData.projectId
            val employeeIdsNotEqual =
                employeeProjectData.employeeId != matchingEmployeeProjectData.employeeId

            return projectIdMatches && employeeIdsNotEqual
        }

        private fun getDaysWorkedTogether(
            firstEmployee: EmployeeProjectData,
            secondEmployee: EmployeeProjectData
        ): String {
            val employeeDaysWorkedTogether =
                DateFormatManager.getDaysWorkedTogether(firstEmployee, secondEmployee)
                    ?: EMPLOYEE_NON_MATCHING_DATES

            return employeeDaysWorkedTogether.toString()
        }
    }
}
