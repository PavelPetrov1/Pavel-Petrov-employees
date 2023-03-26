package com.project.pavel_petrov_employees.presentation.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pavel_petrov_employees.R
import com.example.pavel_petrov_employees.databinding.FragmentHomePageBinding
import com.project.pavel_petrov_employees.presentation.ui.adapters.EmployeeProjectAdapter
import com.project.pavel_petrov_employees.presentation.viewmodel.HomePageViewModel

class HomePageFragment : Fragment(R.layout.fragment_home_page) {

    private lateinit var binding: FragmentHomePageBinding

    private lateinit var mEmployeeProjectDataAdapter: EmployeeProjectAdapter
    private val mHomePageViewModel: HomePageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomePageBinding.bind(view)

        mEmployeeProjectDataAdapter = EmployeeProjectAdapter(requireContext())

        initRecyclerView()

        initClicks()

        handleViewModelData()
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.employeeProjectsRecycler.layoutManager = linearLayoutManager
        binding.employeeProjectsRecycler.adapter = mEmployeeProjectDataAdapter
    }

    private fun initClicks() {
        binding.buttonReadCsv.setOnClickListener {
            getEmployeeCsvAndReadFile()
        }
    }

    private fun getEmployeeCsvAndReadFile() {
        val csvFile = resources.openRawResource(R.raw.employeeprojects)
        mHomePageViewModel.readCsvFile(csvFile)
    }

    private fun handleViewModelData() {
        mHomePageViewModel.projectDataDataList.observe(viewLifecycleOwner) { listOfEmployeeProjects ->
            mEmployeeProjectDataAdapter.setListOfEmployeeAndProject(listOfEmployeeProjects)
        }
    }
}
