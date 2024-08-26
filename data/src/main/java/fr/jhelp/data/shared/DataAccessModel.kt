package fr.jhelp.data.shared

import fr.jhelp.data.entities.Person

interface DataAccessModel {
    fun setPerson(name:String, road:String) : Person
    fun persons() : List<Person>
}