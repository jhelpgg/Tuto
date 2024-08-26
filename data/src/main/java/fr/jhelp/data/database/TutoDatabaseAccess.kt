package fr.jhelp.data.database

import android.content.Context
import androidx.room.Room
import fr.jhelp.data.entities.Converters
import fr.jhelp.injector.injected

private object TutoDatabaseAccess {
    private val context: Context by injected<Context>()

    val database by lazy {
        Room.databaseBuilder(this.context, TutoDatabase::class.java, "tuto").addTypeConverter(Converters()).build()
    }
}

internal val database by lazy { TutoDatabaseAccess.database }
