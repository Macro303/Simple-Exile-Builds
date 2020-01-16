package github.macro

/**
 * Created by Macro303 on 2020-Jan-16.
 */
data class Range<T>(val min: T, val max: T) {
	fun display(): String {
		return if (min == max) {
			if (min is Range<*>)
				min.display()
			else
				"$min"
		} else {
			if (min is Range<*>)
				"(${min.display()} - ${(max as Range<*>).display()})"
			else
				"$min - $max"
		}
	}

	constructor(value: T) : this(value, value)
}