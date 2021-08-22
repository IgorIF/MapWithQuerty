package com.example.mapwithquerty.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonsRepository @Inject constructor(
    val personsRemoteDataSource: PersonsRemoteDataSource
) {

}