package github.macro.build_info

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import github.macro.build_info.gems.BuildGem
import github.macro.build_info.gems.GemInfo
import github.macro.build_info.gems.UpdateGem
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
@JsonDeserialize(using = BuildDeserializer::class)
class BuildInfo() : JsonModelAuto {
	val nameProperty = SimpleStringProperty()
	var name by nameProperty

	val classProperty = SimpleObjectProperty<ClassTag>()
	var classTag by classProperty

	val ascendencyProperty = SimpleObjectProperty<Ascendency>()
	var ascendency by ascendencyProperty

	val linksProperty = SimpleListProperty<List<BuildGem>>()
	var links by linksProperty

	val updatesProperty = SimpleListProperty<UpdateGem>()
	var updates by updatesProperty

	constructor(
		name: String,
		classTag: ClassTag,
		ascendency: Ascendency?,
		links: List<List<BuildGem>>,
		updates: List<UpdateGem>
	) : this() {
		this.name = name
		this.classTag = classTag
		this.ascendency = ascendency
		this.links = FXCollections.observableList(links)
		this.updates = FXCollections.observableList(updates)
	}

	override fun toString(): String {
		return "BuildInfo(nameProperty=$nameProperty, classProperty=$classProperty, ascendencyProperty=$ascendencyProperty, linksProperty=$linksProperty, updatesProperty=$updatesProperty)"
	}

	fun display(): String = "$name [$classTag${if (ascendency == null) "" else "/" + ascendency.name}]"
}