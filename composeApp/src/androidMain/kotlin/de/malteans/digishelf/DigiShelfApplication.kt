package de.malteans.digishelf

import android.app.Application
import de.malteans.digishelf.di.initKoin
import org.koin.android.ext.koin.androidContext

class DigiShelfApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@DigiShelfApplication)
        }
    }
}