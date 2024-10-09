package fr.jhelp.data.shared

import fr.jhelp.data.entities.Person
import fr.jhelp.tools.tasks.future.Future

interface DataAccessModel {
    fun setPerson(name:String, road:String) : Future<Person>
    fun persons() : Future<List<Person>>
}