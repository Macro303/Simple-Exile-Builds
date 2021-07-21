package github.buried_in_code.ui.main

import com.charleskorn.kaml.Yaml
import github.buried_in_code.core.BuildInfo
import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import kotlinx.serialization.decodeFromString
import tornadofx.ViewModel
import tornadofx.getValue
import tornadofx.setValue
import java.io.File
import java.nio.file.Files

/**
 * Created by Buried-In-Code on 2021-Apr-14.
 */
class MainModel : ViewModel() {
    val buildsProperty = SimpleListProperty<BuildInfo>()
    var builds: ObservableList<BuildInfo> by buildsProperty

    init {
        builds = FXCollections.observableArrayList()
        loadBuilds()
    }

    fun loadBuilds() {
        builds.clear()
        val folder = File("Builds")
        if (!folder.exists())
            folder.mkdirs()
        folder.listFiles()!!.filterNot { it.isDirectory }.mapNotNullTo(builds) {
            Files.newBufferedReader(it.toPath()).use { br ->
                Yaml.default.decodeFromString<BuildInfo>(br.readText())
            }
        }
        builds.sortWith(compareBy(BuildInfo::version, BuildInfo::title))
    }
}