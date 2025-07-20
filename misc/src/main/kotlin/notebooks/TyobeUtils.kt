package notebooks

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

/**
 * Converts a date string from "MMM DD, YYYY" format to "YYYY-MM" format.
 *
 * This function parses date strings commonly found in TIOBE index data and transforms
 * them to a standardized year-month format suitable for time series analysis.
 * Uses Kotlin's built-in date/time parsing to avoid static month mappings.
 *
 * @param dateStr The input date string in "MMM DD, YYYY" format (e.g., "Oct 2, 2024")
 * @return The transformed date string in "YYYY-MM" format (e.g., "2024-10")
 *
 * @sample
 * ```kotlin
 * datesAsYYYYMM("Oct 2, 2024")   // returns "2024-10"
 * datesAsYYYYMM("Jan 15, 2023")  // returns "2023-01"
 * datesAsYYYYMM("Dec 31, 2022")  // returns "2022-12"
 * ```
 *
 * If the input string doesn't match the expected format, the original string is returned unchanged.
 */
fun datesAsYYYYMM(dateStr: String): String {
    return try {
        val inFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH)
        val outFormatter = DateTimeFormatter.ofPattern("yyyy-MM")

        // Parse the input date and format to desired output
        val date = LocalDate.parse(dateStr, inFormatter)
        date.format(outFormatter)
    } catch (e: DateTimeParseException) {
        // Return original string if parsing fails
        dateStr
    }
}


/**
 * Safely converts any value to a Double, handling invalid inputs as 0.0.
 *
 * This utility function converts any type to Double by first converting to string,
 * then parsing as a number. Designed for parsing numeric data from DataFrames where
 * values might be of various types or contain invalid/missing data.
 *
 * @param value The value to convert to Double (may be of any type or null)
 * @return A Double value: the parsed number if valid, or 0.0 if null/invalid
 *
 * @sample
 * ```kotlin
 * anyToDouble("15.5")    // returns 15.5
 * anyToDouble(42)        // returns 42.0
 * anyToDouble("0")       // returns 0.0
 * anyToDouble("")        // returns 0.0
 * anyToDouble(null)      // returns 0.0
 * anyToDouble("abc")     // returns 0.0
 * anyToDouble(3.14159)   // returns 3.14159
 * ```
 *
 * This function is particularly useful when processing DataFrame columns where
 * cell values might be strings, numbers, or other types that need standardization.
 */
fun anyToDouble(value: Any?): Double {
    val strValue = value?.toString()

    if (strValue == null) {
        return 0.0
    }
    return strValue.toDoubleOrNull() ?: 0.0
}
