package com.asmaa.khb.filterapp.domain.di

import android.content.Context
import com.asmaa.khb.filterapp.data.local.CategoryRealm
import com.asmaa.khb.filterapp.data.local.FieldRealm
import com.asmaa.khb.filterapp.data.local.OptionsRealm
import com.asmaa.khb.filterapp.data.local.SearchFlowRealm
import com.asmaa.khb.filterapp.data.local.SubCategoryRealm
import com.asmaa.khb.filterapp.domain.SharedPreferencesManager
import com.asmaa.khb.filterapp.domain.helpers.LocalDataHelper
import com.asmaa.khb.filterapp.domain.repositoriesImplementations.FilterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideRealmConfiguration(): RealmConfiguration = RealmConfiguration.Builder(
        schema = setOf(
            CategoryRealm::class, SubCategoryRealm::class,
            SearchFlowRealm::class, FieldRealm::class, OptionsRealm::class
        )
    )
        .deleteRealmIfMigrationNeeded()
        .schemaVersion(0)
        .compactOnLaunch()
        .build()


    @Provides
    fun provideRealmInstance(realmConfiguration: RealmConfiguration): Realm =
        Realm.open(realmConfiguration)


    @Provides
    @Singleton
    fun provideFilterRepository(
        realm: Realm
    ): FilterRepository = FilterRepository(realm)

    @Provides
    @Singleton
    fun provideSharedPrefManager(@ApplicationContext context: Context) =
        SharedPreferencesManager(context)

    @Provides
    fun provideLocalDataHelper(
        @ApplicationContext context: Context,
        realm: Realm,
        sharedPrefManager: SharedPreferencesManager
    ): LocalDataHelper = LocalDataHelper(context, realm, sharedPrefManager)
}