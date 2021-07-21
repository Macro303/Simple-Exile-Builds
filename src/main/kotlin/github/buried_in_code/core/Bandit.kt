package github.buried_in_code.core

/**
 * Created by Buried-In-Code on 2021-Apr-12.
 */
enum class Bandit {
    KRAITYN,
    ALIRA,
    OAK;

    companion object {
        fun fromName(name: String?): Bandit? = values().firstOrNull {
            it.name.replace("_", " ").equals(name, ignoreCase = true)
        }
    }
}