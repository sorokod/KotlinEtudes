package kstate

import kstate.Turnstile3.CoinBox
import kstate.Turnstile3.CoinEvent
import kstate.Turnstile3.Locked
import kstate.Turnstile3.PushEvent
import kstate.Turnstile3.Unlocked
import ru.nsk.kstatemachine.DataEvent
import ru.nsk.kstatemachine.DefaultState
import ru.nsk.kstatemachine.Event
import ru.nsk.kstatemachine.StateMachine
import ru.nsk.kstatemachine.activeStates
import ru.nsk.kstatemachine.addInitialState
import ru.nsk.kstatemachine.createStdLibStateMachine
import ru.nsk.kstatemachine.dataTransition
import ru.nsk.kstatemachine.onEntry
import ru.nsk.kstatemachine.onExit
import ru.nsk.kstatemachine.onTriggered
import ru.nsk.kstatemachine.processEventBlocking
import ru.nsk.kstatemachine.transition
import ru.nsk.kstatemachine.transitionConditionally
import ru.nsk.kstatemachine.visitors.exportToPlantUmlBlocking


/**
 * Like prev version, but we accumulate the coins. Unlock only with at least 10 coins
 * See guard
 */

private object Turnstile3 {
    data class CoinBox(var count: Int = 0)

    object PushEvent : Event

    data class CoinEvent(val amount: Int) : Event


    object Locked : DefaultState("Locked")
    object Unlocked : DefaultState("Unlocked")
}

fun main() {

    val coinBox = CoinBox()

    val machine = createStdLibStateMachine(name = "Turnstile") {

        logger = StateMachine.Logger { println("### ${it()}") }

        addInitialState(Locked) {

            onEntry { println("+onEnter Locked") }
            onExit { println("+onExit Locked") }


            transition<PushEvent> {
                targetState = Locked
            }

            transition<CoinEvent> {
                guard = { event.amount >= 10 }
                targetState = Unlocked
                onTriggered {
                    println("onTriggered " + it.event.amount)
                    coinBox.count += 1
                }
            }
        }


        addState(Unlocked) {

            onEntry { println("+onEnter Unlocked") }
            onExit { println("+onExit Unlocked") }

            transition<PushEvent> {
                targetState = Locked
            }
            transition<CoinEvent> {
                targetState = Unlocked
                onTriggered {
                    coinBox.count += 1
                }
            }
        }
    }

    println(machine.states)

    machine.processEventBlocking(CoinEvent(11))
    check(Unlocked in machine.activeStates()) {"unexpected state: ${machine.activeStates()}"}
    check(coinBox.count == 1) { "coinBox.count was ${coinBox.count}" }
//
//    machine.processEventBlocking((PushEvent))
//    check(Locked in machine.activeStates())
//    check(coinBox.count == 1) { "coinBox.count was ${coinBox.count}" }
//
//    machine.processEventBlocking(CoinEvent(1))
//    check(Unlocked in machine.activeStates()) {"unexpected state: ${machine.activeStates()}"}
//    check(coinBox.count == 2) { "coinBox.count was ${coinBox.count}" }
//
//    machine.processEventBlocking((PushEvent))
//    check(Locked in machine.activeStates())
//    check(coinBox.count == 2) { "coinBox.count was ${coinBox.count}" }
//
//    machine.processEventBlocking(CoinEvent(1))
//    check(Unlocked in machine.activeStates())
//    check(coinBox.count == 3) { "coinBox.count was ${coinBox.count}" }
//
//    machine.processEventBlocking(CoinEvent(1))
//    check(Unlocked in machine.activeStates())
//    check(coinBox.count == 4) { "coinBox.count was ${coinBox.count}" }
//
//
//    println("======\n" + machine.exportToPlantUmlBlocking())
}