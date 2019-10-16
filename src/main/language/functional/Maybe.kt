package language.functional

sealed class Maybe<out T>
class Just<T>(val t: T) : Maybe<T>()
object Noting : Maybe<Nothing>()

