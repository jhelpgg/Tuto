package fr.jhelp.data

import fr.jhelp.data.implementation.DataAccessImplementation
import fr.jhelp.data.shared.DataAccessModel
import fr.jhelp.injector.inject

fun data() {
    inject<DataAccessModel>(DataAccessImplementation())
}