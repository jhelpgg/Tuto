package fr.jhelp.data.implementation

import fr.jhelp.data.entities.Address
import fr.jhelp.data.entities.Person
import fr.jhelp.data.shared.DataAccessModel

internal class DataAccessImplementation : DataAccessModel {
    override fun setPerson(name: String, road: String): Person {
        val address = Address(road)
        return Person(name, address)
    }

    override fun persons(): List<Person> = emptyList()
}