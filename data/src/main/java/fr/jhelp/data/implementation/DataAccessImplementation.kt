package fr.jhelp.data.implementation

import fr.jhelp.data.database.TutoDatabase
import fr.jhelp.data.entities.Address
import fr.jhelp.data.entities.Person
import fr.jhelp.data.shared.DataAccessModel
import fr.jhelp.tools.tasks.future.Future
import fr.jhelp.tools.tasks.parallel
import kotlinx.coroutines.Dispatchers

internal class DataAccessImplementation : DataAccessModel
{
    override fun setPerson(name: String, road: String): Future<Person> =
        {
            val address = Address(road)
            val person = Person(name, address)
            TutoDatabase.addOrUpdate(address)
            TutoDatabase.addOrUpdate(person)
            person
        }.parallel(Dispatchers.IO)

    override fun persons(): Future<List<Person>> =
        TutoDatabase::personsList.parallel(Dispatchers.IO)
}