package com.project.pavel_petrov_employees.presentation.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pavel_petrov_employees.R
import com.example.pavel_petrov_employees.databinding.ElementEmployeeProjectBinding
import com.project.pavel_petrov_employees.data.model.CommonEmployeeProjectData

class EmployeeProjectAdapter(
    private val context: Context
) : RecyclerView.Adapter<EmployeeProjectAdapter.ProjectDataItemViewHolder>() {

    private var listOfEmployeeProject = listOf<CommonEmployeeProjectData>()

    fun setListOfEmployeeAndProject(listOfProjects: List<CommonEmployeeProjectData>) {
        listOfEmployeeProject = listOfProjects

        notifyItemRangeChanged(0, listOfProjects.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectDataItemViewHolder {
        val binding = ElementEmployeeProjectBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProjectDataItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listOfEmployeeProject.count()
    }

    override fun onBindViewHolder(holder: ProjectDataItemViewHolder, position: Int) {
        holder.bind(listOfEmployeeProject[position])
    }

    inner class ProjectDataItemViewHolder(private val binding: ElementEmployeeProjectBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CommonEmployeeProjectData) {
            setTextViewText(item)
        }

        private fun setTextViewText(commonProjectData: CommonEmployeeProjectData) {
            binding.textViewEmployeeId.text = context.getString(
                R.string.employee_project_adapter_textview_employeeId,
                commonProjectData.employeeId
            )
            binding.textViewEmployeeIdMatching.text = context.getString(
                R.string.employee_project_adapter_textview_matching_employee,
                commonProjectData.matchingEmployeeId
            )
            binding.textViewProjectId.text = context.getString(
                R.string.employee_project_adapter_textview_projectId,
                commonProjectData.projectId
            )
            binding.textViewProjectDuration.text = context.getString(
                R.string.employee_project_adapter_textview_workingDays,
                commonProjectData.daysWorked
            )
        }
    }
}
