package github.macro.ui.editor

import github.macro.Util
import github.macro.build_info.Update
import github.macro.build_info.gems.Gem
import github.macro.build_info.gems.reward.RewardType
import github.macro.ui.GemSelector
import github.macro.ui.UIModel
import github.macro.ui.viewer.GemViewerPane
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.layout.BorderPane
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.layout.Priority
import javafx.scene.paint.Paint
import javafx.scene.shape.StrokeLineCap
import javafx.scene.shape.StrokeLineJoin
import javafx.scene.shape.StrokeType
import javafx.scene.text.TextAlignment
import javafx.util.Duration
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.File

/**
 * Created by Macro303 on 2020-Jan-14.
 */
class GemEditorPane(val model: UIModel, gem: Gem, val index: Int? = null, val listName: String? = null) : BorderPane() {
	val gemProperty = SimpleObjectProperty<Gem>()
	var gem by gemProperty

	val imageUrlProperty = SimpleStringProperty()
	var imageUrl by imageUrlProperty

	val nameProperty = SimpleStringProperty()
	var name by nameProperty

	val rewardsProperty = SimpleStringProperty()
	var rewards by rewardsProperty

	val colourProperty = SimpleObjectProperty<Paint>()
	var colour by colourProperty

	val previousProperty = SimpleBooleanProperty()
	var previous by previousProperty

	val nextProperty = SimpleBooleanProperty()
	var next by nextProperty

	val reasonProperty = SimpleStringProperty()
	var reason by reasonProperty

	init {
		setNewGem(gem)
		initialize()
	}

	fun setNewGem(newGem: Gem) {
		gemProperty.value = newGem

		style {
			borderColor += box(c(Util.slotToColour(gem.colour)))
			borderStyle += BorderStrokeStyle(
				StrokeType.CENTERED,
				StrokeLineJoin.ROUND,
				StrokeLineCap.ROUND,
				0.0,
				0.0,
				listOf(5.0, 5.0)
			)
			borderWidth += box(2.px)
		}

		var imageFile = gem.getFile()
		if (!imageFile.exists())
			imageFile = File("resources/Gems", "Missing.png")
		imageUrlProperty.value = "file:${imageFile.path}"

		nameProperty.value = gem.getTagname()

		var temp = gem.acquisition.rewards.joinToString(separator = "\n") {
			var text = "Act ${it.act} - ${it.quest}"
			if(it.rewardType == RewardType.VENDOR)
				text += " (${it.vendor})"
			text
		}.trim()
		if (temp.isBlank())
			temp = "Not available as a reward"
		rewardsProperty.value = temp

		colourProperty.value = Paint.valueOf(Util.slotToColour(gem.colour))

		previousProperty.value = model.selectedBuild.gems.updates.any {
			(it.new == gem) && it.new != Util.MISSING_GEM
		}

		nextProperty.value = model.selectedBuild.gems.updates.any {
			it.old == gem && it.old != Util.MISSING_GEM
		}

		reasonProperty.value = model.selectedBuild.gems.updates.firstOrNull {
			it.old == gem && it.old != Util.MISSING_GEM
		}?.reason
	}

	companion object {
		private val LOGGER = LogManager.getLogger(GemEditorPane::class.java)
	}

	private fun initialize() {
		paddingAll = 5.0
		top {
			hbox(spacing = 5.0, alignment = Pos.CENTER) {
				imageview(imageUrlProperty, lazyload = true) {
					fitHeight = 80.0
					fitWidth = 80.0
				}
			}
		}
		center {
			label(nameProperty) {
				isWrapText = true
				prefWidth = 90.0
				alignment = Pos.CENTER
				textAlignment = TextAlignment.CENTER
				textFillProperty().bind(colourProperty)
				tooltip(rewards) {
					style {
						fontSize = 14.px
					}
					textProperty().bind(rewardsProperty)
				}
				Util.hackTooltipStartTiming(tooltip)
			}
		}
		bottom {
			vbox(spacing = 5.0, alignment = Pos.CENTER) {
				hbox(spacing = 5.0, alignment = Pos.CENTER) {
					button(text = "<<") {
						visibleWhen(previousProperty)
						isFocusTraversable = false
						action {
							setNewGem(
								model.selectedBuild.gems.updates.firstOrNull { it.new?.equals(gem) ?: false }?.old
									?: Util.MISSING_GEM
							)
						}
					}
					separator {
						isVisible = false
						hgrow = Priority.ALWAYS
					}
					button(text = ">>") {
						visibleWhen(nextProperty)
						isFocusTraversable = false
						action {
							setNewGem(
								model.selectedBuild.gems.updates.firstOrNull { it.old?.equals(gem) ?: false }?.new
									?: Util.MISSING_GEM
							)
						}
						tooltip(reason) {
							style {
								fontSize = 14.px
							}
							textProperty().bind(reasonProperty)
						}
						Util.hackTooltipStartTiming(tooltip)
					}
				}
				hbox(spacing = 5.0, alignment = Pos.CENTER) {
					button("Add") {
						visibleWhen(nextProperty.not().and(gemProperty.isEqualTo(Util.MISSING_GEM).not()))
						action {
							val scope = Scope()
							setInScope(model, scope)
							find<GemSelector>(scope).openModal(block = true, resizable = false)
							if (model.selectedGem != Util.MISSING_GEM) {
								model.selectedBuild.gems.updates.add(
									Update(gem, model.selectedGem, "Reason not Implemented")
								)
								setNewGem(model.selectedGem)
							}
						}
					}
					button("Edit") {
						action {
							val scope = Scope()
							setInScope(model, scope)
							find<GemSelector>(scope).openModal(block = true, resizable = false)
							if (model.selectedGem != Util.MISSING_GEM) {
								if (index != null) {
									when (listName) {
										"Weapons" -> model.selectedBuild.gems.weapons[index] = model.selectedGem
										"Armour" -> model.selectedBuild.gems.armour[index] = model.selectedGem
										"Helmet" -> model.selectedBuild.gems.helmet[index] = model.selectedGem
										"Gloves" -> model.selectedBuild.gems.gloves[index] = model.selectedGem
										"Boots" -> model.selectedBuild.gems.boots[index] = model.selectedGem
									}
								}
								model.selectedBuild.gems.updates.filter { it.old == gem }
									.forEach { it.old = model.selectedGem }
								model.selectedBuild.gems.updates.filter { it.new == gem }
									.forEach { it.new = model.selectedGem }
								setNewGem(model.selectedGem)
							}
						}
					}
					button("Del") {
						visibleWhen(previousProperty.or(nextProperty))
						action {
							var update = model.selectedBuild.gems.updates.firstOrNull { it.new == gem }
							if (update != null) {
								model.selectedBuild.gems.updates.remove(update)
								val refresh = model.selectedBuild.gems.updates.firstOrNull { it.old == gem }
								if (refresh != null)
									refresh.old = update.old
								setNewGem(update.old)
							} else {
								update = model.selectedBuild.gems.updates.firstOrNull { it.old == gem }
								if (update != null) {
									model.selectedBuild.gems.updates.remove(update)
									val refresh = model.selectedBuild.gems.updates.firstOrNull { it.new == gem }
									if (refresh != null)
										refresh.new = update.new
									if (index != null) {
										when (listName) {
											"Weapons" -> {
												val selected = model.selectedBuild.gems.weapons[index]
												if (selected == gem)
													model.selectedBuild.gems.weapons[index] = update.new
											}
											"Armour" -> {
												val selected = model.selectedBuild.gems.armour[index]
												if (selected == gem)
													model.selectedBuild.gems.armour[index] = update.new
											}
											"Helmet" -> {
												val selected = model.selectedBuild.gems.helmet[index]
												if (selected == gem)
													model.selectedBuild.gems.helmet[index] = update.new
											}
											"Gloves" -> {
												val selected = model.selectedBuild.gems.gloves[index]
												if (selected == gem)
													model.selectedBuild.gems.gloves[index] = update.new
											}
											"Boots" -> {
												val selected = model.selectedBuild.gems.boots[index]
												if (selected == gem)
													model.selectedBuild.gems.boots[index] = update.new
											}
										}
									}
									setNewGem(update.new)
								}
							}
						}
					}
				}
			}
		}
	}
}