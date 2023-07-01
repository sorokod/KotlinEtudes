package kstate

import kstate.Turnstile.CoinEvent
import kstate.Turnstile.Locked
import kstate.Turnstile.PushEvent
import kstate.Turnstile.Unlocked
import ru.nsk.kstatemachine.DefaultState
import ru.nsk.kstatemachine.Event
import ru.nsk.kstatemachine.StateMachine
import ru.nsk.kstatemachine.activeStates
import ru.nsk.kstatemachine.addInitialState
import ru.nsk.kstatemachine.createStdLibStateMachine
import ru.nsk.kstatemachine.processEventBlocking
import ru.nsk.kstatemachine.transition
import ru.nsk.kstatemachine.visitors.exportToPlantUmlBlocking

private object Turnstile {
    object PushEvent : Event
    object CoinEvent : Event

    object Locked : DefaultState("Locked")
    object Unlocked : DefaultState("Unlocked")
}

fun main() {
    val machine = createStdLibStateMachine(name = "Turnstile") {

        logger = StateMachine.Logger { println("### ${it()}") }

        addInitialState(Locked) {
            transition<CoinEvent> {
                targetState = Unlocked
            }
        }
        addState(Unlocked) {
            transition<PushEvent> {
                targetState = Locked
            }
        }
    }

    machine.processEventBlocking(CoinEvent)
    check(Unlocked in machine.activeStates())

    machine.processEventBlocking((PushEvent))
    check(Locked in machine.activeStates())


    println("======\n" + machine.exportToPlantUmlBlocking())
}