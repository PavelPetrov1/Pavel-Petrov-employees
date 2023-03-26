package com.project.pavel_petrov_employees.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.pavel_petrov_employees.data.model.CommonEmployeeProjectData
import com.project.pavel_petrov_employees.data.model.EmployeeProjectData
import com.project.pavel_petrov_employees.presentation.ui.utils.DateFormatManager
import com.project.pavel_petrov_employees.presentation.ui.utils.DateFormatManager.asDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream

class HomePageViewModel : ViewModel() {

    private val _projectDataDataList = MutableLiveData<List<CommonEmployeeProjectData>>()
    val projectDataDataList: LiveData<List<CommonEmployeeProjectData>> = _projectDataDataList

    fun readCsvFile(inputStream: InputStream) {
        viewModelScope.launch(Dispatchers.Default) {
            val listOfEmployeeProjectData = readCsv(inputStream)

            val commonProjectData =
                CommonEmployeeProjectData.calculateCommonProjects(listOfEmployeeProjectData)

            _projectDataDataList.postValue(commonProjectData)
        }
    }

    private fun readCsv(inputStream: InputStream): List<EmployeeProjectData> {
        val reader = inputStream.bufferedReader()

        return reader
            .lineSequence()
            .filter { it.isNotBlank() }
            .map {
                val (employeeIdStr, projectIdStr, startDateStr, endDateStr) = it.split(
                    ',',
                    ignoreCase = false,
                    limit = 4
                )

                val employeeId = employeeIdStr.trim().toInt()
                val projectId = projectIdStr.trim().toInt()
                val startingDate = startDateStr.asDate(DateFormatManager.DEFAULT_DATE_FORMAT)
                val endDate = endDateStr.asDate(DateFormatManager.DEFAULT_DATE_FORMAT)

                EmployeeProjectData(employeeId, projectId, startingDate, endDate)
            }.toList()
    }
}
