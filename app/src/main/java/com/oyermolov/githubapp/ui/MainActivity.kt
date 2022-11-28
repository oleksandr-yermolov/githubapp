package com.oyermolov.githubapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.oyermolov.githubapp.GithubApplication
import com.oyermolov.githubapp.R
import com.oyermolov.githubapp.databinding.ActivityMainBinding
import com.oyermolov.githubapp.domain.UserEntity
import com.oyermolov.githubapp.ui.adapter.RepositoriesAdapter
import com.oyermolov.githubapp.ui.viewmodel.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModel: MainViewModel

    private val adapter = RepositoriesAdapter()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as? GithubApplication)?.appComponent?.inject(this)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchField.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) {
                binding.loadRepositoriesButton.isEnabled = s.isNullOrEmpty().not()
            }
        })
        binding.loadRepositoriesButton.setOnClickListener {
            val username = binding.searchField.text.toString()
            viewModel.loadUser(username)
            viewModel.loadRepositories(username)
        }
        binding.repositoriesRecyclerView.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        binding.repositoriesRecyclerView.adapter = adapter

        viewModel.currentUser.observe(this) {
            updateUserSection(it)
        }
        viewModel.repositories.observe(this) {
            binding.repositories.isVisible = it.isNotEmpty()
            adapter.submitItems(it)
        }
        viewModel.errors.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUserSection(user: UserEntity) {
        with(binding) {
            userGroup.visibility = View.VISIBLE

            Glide.with(this@MainActivity)
                .load(user.avatarUrl)
                .placeholder(R.drawable.ic_avatar_placeholder)
                .circleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(userAvatar)

            userName.text = user.name
            userLogin.text = user.login
            userPublicRepos.text = getString(R.string.public_repos_count, user.publicRepos)
            userFollowersAndFollowing.text = getString(R.string.followers_and_following_count, user.followers, user.following)

            userCompany.isVisible = !user.company.isNullOrEmpty()
            userLocation.isVisible = !user.location.isNullOrEmpty()
            userBio.isVisible = !user.bio.isNullOrEmpty()

            userCompany.text = user.company
            userLocation.text = user.location
            userBio.text = user.bio
        }
    }
}