package fr.jhelp.tuto

import android.app.Application
import android.content.Context
import fr.jhelp.injector.inject
import fr.jhelp.model.models

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        inject<Context>(this.applicationContext)
        models()
    }
}