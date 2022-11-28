package com.oyermolov.githubapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oyermolov.githubapp.databinding.RepositoriesListItemBinding
import com.oyermolov.githubapp.domain.RepositoryEntity

class RepositoriesAdapter : RecyclerView.Adapter<RepositoriesAdapter.ViewHolder>() {
    private val items = mutableListOf<RepositoryEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RepositoriesListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun submitItems(repositories: List<RepositoryEntity>) {
        items.clear()
        items.addAll(repositories)
        notifyDataSetChanged() //TODO: Could be improved with paging
    }

    inner class ViewHolder(
        private val binding: RepositoriesListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(repository: RepositoryEntity) {
            binding.title.text = repository.name
            binding.description.text = repository.description
        }
    }
}