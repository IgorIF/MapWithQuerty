package com.example.mapwithquerty.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mapwithquerty.data.models.UsersResult
import com.example.mapwithquerty.data.UsersRepository
import com.example.mapwithquerty.data.models.Results
import com.example.mapwithquerty.di.MainScreenScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@MainScreenScope
class MainViewModel @Inject constructor(
    private val usersRepository: UsersRepository
): ViewModel() {

    val users = mutableStateOf<List<Results>>(emptyList())

    init {
        getPersons(10)
    }

    private fun getPersons(size: Int) {
        usersRepository.getPersons(size).enqueue(object : Callback<UsersResult> {
            override fun onResponse(call: Call<UsersResult>, response: Response<UsersResult>) {
                if (response.isSuccessful) {
                    response.body()?.let { users.value = it.userList }
                }
            }

            override fun onFailure(call: Call<UsersResult>, t: Throwable) = Timber.i(t)
        })
    }

}