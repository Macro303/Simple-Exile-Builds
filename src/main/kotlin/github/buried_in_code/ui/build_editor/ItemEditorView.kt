package github.buried_in_code.ui.build_editor

import github.buried_in_code.Utils
import github.buried_in_code.core.Gem
import github.buried_in_code.core.GemEntry
import github.buried_in_code.ui.gem_selector.GemSelectorModel
import github.buried_in_code.ui.gem_selector.GemSelectorView
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.layout.BorderPane
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.paint.Paint
import javafx.scene.shape.StrokeLineCap
import javafx.scene.shape.StrokeLineJoin
import javafx.scene.shape.StrokeType
import javafx.scene.text.TextAlignment
import tornadofx.*

/*
 * Created by Buried-In-Code on 2021-Jun-22.
 */
class ItemEditorView(private val original: GemEntry) : BorderPane() {
    val itemProperty = SimpleObjectProperty<GemEntry>()
    var item by itemProperty

    val imageUrlProperty = SimpleStringProperty()
    var imageUrl by imageUrlProperty

    val nameProperty = SimpleStringProperty()
    var name by nameProperty

    val colourProperty = SimpleObjectProperty<Paint>()
    var colour by colourProperty

    val previousProperty = SimpleObjectProperty<GemEntry?>()
    var previous by previousProperty

    val nextProperty = SimpleObjectProperty<GemEntry?>()
    var next by nextProperty

    init {
        maxWidth = (Utils.UI_PREF_WIDTH - (10 * 6)) / 6
        prefWidth = (Utils.UI_PREF_WIDTH - (20 * 6)) / 6
        minWidth = (Utils.UI_PREF_WIDTH - (30 * 6)) / 6
        paddingAll = 10.0
        setItemProperties(original)
        init()
    }

    private fun setItemProperties(item: GemEntry) {
        this.item = item
        this.imageUrl = "file:${item.gem.getImageFile().path}"
        this.name = item.gem.name
        this.colour = Paint.valueOf(item.gem.colour.hex)
        var temp = original
        while (temp.next != null) {
            if (temp.next == item) {
                this.previous = temp
                break
            }
            temp = temp.next!!
        }
        this.next = item.next

        style {
            borderColor += box(c(item.gem.colour.hex))
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
    }

    private fun init() {
        center {
            paddingAll = 5.0
            vbox(spacing = 5.0, alignment = Pos.CENTER) {
                paddingAll = 5.0
                imageview(imageUrlProperty, lazyload = true) {
                    if (!imageUrl.contains("placeholder")) {
                        if (Utils.getImageHeight(item.gem) >= Utils.IMG_SIZE) {
                            fitHeight = Utils.IMG_SIZE
                            isPreserveRatio = true
                        } else if (Utils.getImageWidth(item.gem) >= Utils.IMG_SIZE) {
                            fitWidth = Utils.IMG_SIZE
                            isPreserveRatio = true
                        }
                    }
                }
            }
        }
        left {
            vbox(spacing = 5.0, alignment = Pos.CENTER) {
                paddingAll = 5.0
                button("⬅") {
                    visibleWhen(previousProperty.isNotNull)
                    action {
                        setItemProperties(previous!!)
                    }
                }
            }
        }
        right {
            vbox(spacing = 5.0, alignment = Pos.CENTER) {
                paddingAll = 5.0
                button("➡") {
                    visibleWhen(previousProperty.isNotNull)
                    action {
                        setItemProperties(previous!!)
                    }
                }
            }
        }
        bottom {
            vbox(spacing = 5.0, alignment = Pos.CENTER) {
                paddingAll = 5.0
                label(nameProperty) {
                    isWrapText = true
                    alignment = Pos.CENTER
                    textAlignment = TextAlignment.CENTER
                    textFillProperty().bind(colourProperty)
                }
                hbox(spacing = 5.0, alignment = Pos.CENTER) {
                    paddingAll = 5.0
                    button("➕") {
                        useMaxWidth = true
                        action {
                            val selected = gemSelect(item.gem)
                            if (selected.id != "Gem/Missing") {
                                if (item.gem.id != "Gem/Missing") {
                                    item.next = GemEntry(selected.id, item.next)
                                    setItemProperties(item.next!!)
                                } else {
                                    item = GemEntry(selected.id, item.next)
                                    setItemProperties(item)
                                }
                            }
                        }
                    }
                    button("⚙") {
                        useMaxWidth = true
                    }
                    button("✖") {
                        useMaxWidth = true
                        action {
                            if (next != null) {
                                if (previous != null) {
                                    previous!!.next = item.next
                                    setItemProperties(item)
                                } else
                                    item = item.next
                            } else if (previous != null) {
                                previous!!.next = null
                                setItemProperties(previous!!)
                            } else
                                item = GemEntry(Utils.getMissingGem().id)
                        }
                        visibleWhen {
                            (previousProperty.isNotNull.and(nextProperty.isNotNull)).or((item.gem.id != "Gem/Missing").toProperty())
                        }
                    }
                }
            }
        }
    }

    private fun gemSelect(current: Gem): Gem {
        val temp = GemSelectorModel(current)
        val scope = Scope()
        setInScope(temp, scope)
        find<GemSelectorView>(scope).openModal(block = true, resizable = false)
        return temp.selected
    }
}