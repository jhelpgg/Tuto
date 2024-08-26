package fr.jhelp.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import fr.jhelp.injector.injected

internal object TutoDatabase {
    private const val TABLE_VERSION = "Version"

    const val COLUMN_UID = "uid"
    const val COLUMN_VERSION = "version"

    private val context by injected<Context>()
    private val database: SQLiteDatabase by lazy {
        val database = this.context.openOrCreateDatabase("TutoDatabase", Context.MODE_PRIVATE, null)
        this.initialize(database)
        database
    }

    internal fun createTable(tableName: String, vararg columns: Pair<String, DataType>) {

    }

    private fun initialize(database: SQLiteDatabase) {

    }
}
