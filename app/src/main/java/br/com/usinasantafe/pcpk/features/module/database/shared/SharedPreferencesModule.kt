package br.com.usinasantafe.pcpk.features.module.database.shared

import android.content.Context
import android.content.SharedPreferences
import br.com.usinasantafe.pcpk.common.utils.BASE_SHARE_PREFERENCES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences(BASE_SHARE_PREFERENCES, Context.MODE_PRIVATE)
    }

}