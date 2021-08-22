package com.example.mapwithquerty.di

import javax.inject.Scope

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class MainScreenScope

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class MapScreenScope

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class PersonScreenScope