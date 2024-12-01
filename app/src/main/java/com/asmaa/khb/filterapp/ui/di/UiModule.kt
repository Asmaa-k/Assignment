package com.asmaa.khb.filterapp.ui.di

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent


@Module
@InstallIn(FragmentComponent::class)
object NavControllerModule {

    @Provides
    fun provideNavController(fragment: Fragment): NavController {
        return NavHostFragment.findNavController(fragment)
    }
}
