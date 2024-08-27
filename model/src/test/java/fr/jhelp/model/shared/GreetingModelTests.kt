package fr.jhelp.model.shared

import fr.jhelp.injector.inject
import fr.jhelp.injector.injected
import fr.jhelp.model.dummy.GreetingDummy
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GreetingModelTests {
    @Before
    fun initialize() {
        val messageMutable = MutableStateFlow<String>("Test")
        inject<GreetingModel>(GreetingDummy(messageMutable) { "Other" })
    }

    private val greetingModel : GreetingModel by injected<GreetingModel>()

    @Test
    fun greetingTest() {
        Assert.assertEquals("Test", this.greetingModel.message.value)
        this.greetingModel.otherMessage()
        Thread.sleep(1024)
        Assert.assertEquals("Other", this.greetingModel.message.value)
    }
}