package com.oyermolov.githubapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oyermolov.githubapp.data.GithubRepository
import com.oyermolov.githubapp.domain.RepositoryEntity
import com.oyermolov.githubapp.domain.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {

    val currentUser = MutableLiveData<UserEntity>() //TODO: Provide immutable livedata
    val repositories = MutableLiveData<List<RepositoryEntity>>()
    val errors = MutableLiveData<String>()

    fun loadUser(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUser(username)
                .onSuccess {
                    currentUser.postValue(it)
                }
                .onFailure {
                    errors.postValue(it.message ?: "Something went wrong")
                }
        }
    }

    fun loadRepositories(username: String) { //TODO: Implement pagination (default page size is 30)
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRepositories(username, forceUpdate = true)
                .onSuccess {
                    repositories.postValue(it)
                }
                .onFailure {
                    errors.postValue(it.message ?: "Something went wrong")
                }
        }
    }
}