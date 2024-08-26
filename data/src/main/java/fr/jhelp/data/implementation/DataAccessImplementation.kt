package fr.jhelp.data.implementation

import fr.jhelp.data.database.database
import fr.jhelp.data.entities.Person
import fr.jhelp.data.shared.DataAccessModel

internal class DataAccessImplementation: DataAccessModel {
    override fun setPerson(name: String, road: String): Person {
        TODO("Not yet implemented")
    }

    override fun persons(): List<Person>  =
        database.personDao().persons()
}