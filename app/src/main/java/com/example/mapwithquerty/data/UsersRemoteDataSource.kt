package com.example.mapwithquerty.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRemoteDataSource @Inject constructor(
    private val personService: UsersRemoteService
) {
    fun getUsers(size: Int) = personService.getUsers(size)
}