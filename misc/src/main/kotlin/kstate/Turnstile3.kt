package kstate

import kstate.Turnstile2.CoinBox
import kstate.Turnstile2.CoinEvent
import kstate.Turnstile2.Locked
import kstate.Turnstile2.PushEvent
import kstate.Turnstile2.Unlocked
import ru.nsk.kstatemachine.DefaultState
import ru.nsk.kstatemachine.Event
import ru.nsk.kstatemachine.StateMachine
import ru.nsk.kstatemachine.activeStates
import ru.nsk.kstatemachine.addInitialState
import ru.nsk.kstatemachine.createStdLibStateMachine
import ru.nsk.kstatemachine.onTriggered
import ru.nsk.kstatemachine.processEventBlocking
import ru.nsk.kstatemachine.state
import ru.nsk.kstatemachine.transition
import ru.nsk.kstatemachine.visitors.exportToPlantUmlBlocking



/**
 * Like prev version, but we accumulate the coins (tokens really).
 * Note that the machine consumes coins when Unlocked !
 */

private object Turnstile2 {
    data class CoinBox(var count: Int = 0)

    object PushEvent : Event

    //    data class CoinEvent(override val data: Int) : DataEvent<Int>
    object CoinEvent : Event


    object Locked : DefaultState("Locked")
    object Unlocked : DefaultState("Unlocked")
}

fun main() {

    val coinBox = CoinBox()

    val machine = createStdLibStateMachine(name = "Turnstile") {

        logger = StateMachine.Logger { println("### ${it()}") }

        addInitialState(Locked) {
            transition<PushEvent> {
                targetState = Locked
            }

            transition<CoinEvent> {
                targetState = Unlocked
//                onComplete { params, _ -> coinBox.count += params.event.data }
                onTriggered { coinBox.count += 1 }
            }
        }


        addState(Unlocked) {
            transition<PushEvent> {
                targetState = Locked
            }
            transition<CoinEvent> {
                targetState = Unlocked
//                onComplete { params, _ -> coinBox.count += params.event.data }
                onTriggered { coinBox.count += 1 }
            }
        }
    }

    machine.processEventBlocking(CoinEvent)
    check(Unlocked in machine.activeStates())
    check(coinBox.count == 1) { "coinBox.count was ${coinBox.count}" }

    machine.processEventBlocking((PushEvent))
    check(Locked in machine.activeStates())
    check(coinBox.count == 1) { "coinBox.count was ${coinBox.count}" }

    machine.processEventBlocking(CoinEvent)
    check(Unlocked in machine.activeStates())
    check(coinBox.count == 2) { "coinBox.count was ${coinBox.count}" }

    machine.processEventBlocking((PushEvent))
    check(Locked in machine.activeStates())
    check(coinBox.count == 2) { "coinBox.count was ${coinBox.count}" }

    machine.processEventBlocking(CoinEvent)
    check(Unlocked in machine.activeStates())
    check(coinBox.count == 3) { "coinBox.count was ${coinBox.count}" }

    machine.processEventBlocking(CoinEvent)
    check(Unlocked in machine.activeStates())
    check(coinBox.count == 4) { "coinBox.count was ${coinBox.count}" }


    println("======\n" + machine.exportToPlantUmlBlocking())
}