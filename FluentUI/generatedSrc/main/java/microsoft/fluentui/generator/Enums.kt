/**
 * Auto-generated code, all changes will be lost
 */

package microsoft.fluentui.generator

enum class ButtonSize (val value: Int) {
	SMALL(0),
	MEDIUM(1),
	LARGE(2),

	;companion object {
		val map by lazy { ButtonSize.values().associateBy(ButtonSize::value) }
		fun fromValue(type: Int) = map[type]
		val list by lazy { ButtonSize.values().associateBy(ButtonSize::name) }
		fun fromValue(type: String) = list[type.toUpperCase()]
	}
}

enum class ButtonStyle (val value: Int) {
	PRIMARY(0),
	SECONDARY(1),
	GHOST(2),

	;companion object {
		val map by lazy { ButtonStyle.values().associateBy(ButtonStyle::value) }
		fun fromValue(type: Int) = map[type]
		val list by lazy { ButtonStyle.values().associateBy(ButtonStyle::name) }
		fun fromValue(type: String) = list[type.toUpperCase()]
	}
}