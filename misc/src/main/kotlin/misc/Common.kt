package misc

import java.io.File

/**
 * @return the [File] identified by this [String] assumed to point at a
 * file in resources directory.
 *
 * Will throw an NPE if file is not found
 */
fun String.asResourceFile(): File =
    File(object {}.javaClass.getResource(this).toURI()!!)
