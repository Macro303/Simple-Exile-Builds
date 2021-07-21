package github.buried_in_code

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Buried-In-Code on 2020-Jul-17
 */
@Serializable
data class Config(@SerialName("Dark Mode") var darkMode: Boolean = false)