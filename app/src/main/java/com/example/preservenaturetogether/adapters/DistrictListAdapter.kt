package com.example.preservenaturetogether.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.preservenaturetogether.data.District
import com.example.preservenaturetogether.databinding.ListItemDistrictBinding
import com.example.preservenaturetogether.viewmodels.DistrictListItemViewModel

class DistrictListAdapter(
    private val districtList: List<District>,
) : RecyclerView.Adapter<DistrictListAdapter.ViewHolder>() {
    class ViewHolder(
        val binding: ListItemDistrictBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        binding = ListItemDistrictBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.viewModel = DistrictListItemViewModel(district = districtList[position])
    }

    override fun getItemCount(): Int = districtList.size
}