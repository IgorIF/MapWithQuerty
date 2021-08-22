package com.example.mapwithquerty.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepository @Inject constructor(
    private val usersRemoteDataSource: UsersRemoteDataSource
) {

    fun getPersons(size: Int) =  usersRemoteDataSource.getUsers(size)

}