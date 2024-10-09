package fr.jhelp.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import fr.jhelp.data.entities.Address
import fr.jhelp.data.entities.Entity
import fr.jhelp.data.entities.Person
import fr.jhelp.injector.injected
import fr.jhelp.tools.optional.Optional
import fr.jhelp.tools.optional.emptyOptional
import fr.jhelp.tools.optional.valueOptional

internal object TutoDatabase
{
    private const val TABLE_VERSION = "Version"

    const val COLUMN_UID = "uid"
    const val COLUMN_VERSION = "version"
    const val VERSION = 1

    private val context by injected<Context>()
    private val database: SQLiteDatabase by lazy {
        val database = this.context.openOrCreateDatabase("TutoDatabase", Context.MODE_PRIVATE, null)
        this.initialize(database)
        database
    }

    internal fun personsList(): List<Person>
    {
        val persons = ArrayList<Person>()

        val cursor = this.database.query(
            Person.TABLE_PERSON,
            arrayOf(COLUMN_UID, Person.COLUMN_NAME, Person.COLUMN_ADDRESS),
            null, null, null, null, null
                                        )

        while (cursor.moveToNext())
        {
            val uid = cursor.getLong(0)
            val name = cursor.getString(1)
            val addressUid = cursor.getLong(2)

            val addressOptional = this.address(addressUid)

            addressOptional.ifPresent { address ->
                persons.add(Person(uid, name, address))
            }
        }

        cursor.close()
        return persons
    }

    internal fun addOrUpdate(address: Address)
    {
        this.addOrUpdateEntity(address)
    }

    internal fun addOrUpdate(person: Person)
    {
        this.addOrUpdate(person.address)
        this.addOrUpdateEntity(person)
    }

    internal fun address(road: String): Optional<Address>
    {
        var response: Optional<Address> = emptyOptional()

        val cursor = this.database.query(
            Address.TABLE_ADDRESS,
            arrayOf(COLUMN_UID),
            "${Address.COLUMN_ROAD} = ?",
            arrayOf(road),
            null, null, null
                                        )

        if (cursor.moveToNext())
        {
            val uid = cursor.getLong(0)
            response = valueOptional(Address(uid, road))
        }

        cursor.close()
        return response
    }

    private fun address(uid: Long): Optional<Address>
    {
        var response: Optional<Address> = emptyOptional()

        val cursor = this.database.query(
            Address.TABLE_ADDRESS,
            arrayOf(Address.COLUMN_ROAD),
            "$COLUMN_UID = ?",
            arrayOf(uid.toString()),
            null, null, null
                                        )

        if (cursor.moveToNext())
        {
            val road = cursor.getString(0)
            response = valueOptional(Address(uid, road))
        }

        cursor.close()
        return response
    }

    internal fun person(name: String): Optional<Person>
    {
        var response: Optional<Person> = emptyOptional()

        val cursor = this.database.query(
            Person.TABLE_PERSON,
            arrayOf(COLUMN_UID, Person.COLUMN_ADDRESS),
            "${Person.COLUMN_NAME} = ?",
            arrayOf(name),
            null, null, null
                                        )

        if (cursor.moveToNext())
        {
            val uid = cursor.getLong(0)
            val addressUid = cursor.getLong(1)

            val addressOptional = this.address(addressUid)

            addressOptional.ifPresent { address ->
                response = valueOptional(Person(uid, name, address))
            }
        }

        cursor.close()
        return response
    }

    private fun addOrUpdateEntity(entity: Entity)
    {
        if (entity.uid < 0L)
        {
            entity.uid = this.entityUid(entity)
        }
        else
        {
            val contentValues = ContentValues()
            entity.collect(contentValues)
            this.database.update(entity.tableName, contentValues, "$COLUMN_UID = ?", arrayOf(entity.uid.toString()))
        }
    }

    private fun createTable(tableName: String, vararg columns: Pair<String, DataType>)
    {
        val builder = StringBuilder()
        builder.append("CREATE TABLE IF NOT EXISTS $tableName (")
        builder.append("$COLUMN_UID INTEGER PRIMARY KEY AUTOINCREMENT")

        for (column in columns)
        {
            builder.append(", ${column.first} ${column.second}")
        }

        builder.append(")")

        this.database.execSQL(builder.toString())
    }

    private fun initialize(database: SQLiteDatabase)
    {
        val currentVersion = this.currentVersion(database)

        if (currentVersion < VERSION)
        {
            this.upgrade(database, currentVersion, VERSION)
            database.delete(TABLE_VERSION, null, null)
            val contentValues = ContentValues()
            contentValues.put(COLUMN_VERSION, VERSION)
            database.insert(TABLE_VERSION, null, contentValues)
        }
    }

    private fun upgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int)
    {
        if (oldVersion == 0)
        {
            this.lastVersion(database)
            return
        }

        var version = oldVersion

        while (version < newVersion)
        {
            when (version)
            {
                1    -> this.upgrade_1_2(database)
                else -> throw IllegalStateException("Unknown version $version")
            }

            version++
        }
    }

    private fun upgrade_1_2(database: SQLiteDatabase)
    {
        //TODO("Not yet implemented")
    }

    private fun lastVersion(database: SQLiteDatabase)
    {
        this.createTable(Address.TABLE_ADDRESS, Address.COLUMN_ROAD to DataType.TEXT)
        this.createTable(Person.TABLE_PERSON, Person.COLUMN_NAME to DataType.TEXT, Person.COLUMN_ADDRESS to DataType.INTEGER)
    }

    private fun entityUid(entity: Entity): Long
    {
        var uid = entity.uid

        if (uid >= 0L)
        {
            return uid
        }

        val where = StringBuilder()
        val arguments = ArrayList<String>()

        for (column in entity.columnsIdentifier)
        {
            if (where.isNotEmpty())
            {
                where.append(" AND ")
            }

            where.append("$column = ?")
            arguments.add(entity.value(column))
        }

        val cursor = this.database.query(
            entity.tableName,
            arrayOf(COLUMN_UID),
            where.toString(),
            arguments.toTypedArray(),
            null, null, null
                                        )

        if (cursor.moveToNext())
        {
            uid = cursor.getLong(0)
        }

        cursor.close()

        if (uid < 0L)
        {
            val contentValues = ContentValues()
            entity.collect(contentValues)
            uid = this.database.insert(entity.tableName, null, contentValues)
        }

        entity.uid = uid
        return uid
    }

    private fun currentVersion(database: SQLiteDatabase): Int
    {
        var version = 0
        database.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_VERSION ($COLUMN_VERSION INTEGER)")
        val cursor = database.query(
            TABLE_VERSION,
            arrayOf(COLUMN_VERSION),
            null, null,
            null, null, null
                                   )

        if (cursor.moveToNext())
        {
            version = cursor.getInt(0)
        }

        cursor.close()
        return version
    }
}
