/**
 * Auto-generated code, all changes will be lost
 */

package com.microsoft.fluentui.generator

enum class DrawerType (val value: Int) {
	LEFT_NAV(0),
	RIGHT_NAV(1),

	;companion object {
		val map by lazy { DrawerType.values().associateBy(DrawerType::value) }
		fun fromValue(type: Int) = map[type]
		val list by lazy { DrawerType.values().associateBy(DrawerType::name) }
		fun fromValue(type: String) = list[type.toUpperCase()]
	}
}

enum class AvatarSize (val value: Int) {
	X_SMALL(0),
	SMALL(1),
	MEDIUM(2),
	LARGE(3),
	XLARGE(4),
	XXLARGE(5),

	;companion object {
		val map by lazy { AvatarSize.values().associateBy(AvatarSize::value) }
		fun fromValue(type: Int) = map[type]
		val list by lazy { AvatarSize.values().associateBy(AvatarSize::name) }
		fun fromValue(type: String) = list[type.toUpperCase()]
	}
}

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

enum class AvatarStyle (val value: Int) {
	BASE(0),
	ACCENT(1),
	GROUP(2),
	OUTLINED(3),
	OUTLINED_PRIMARY(4),
	OVERFLOW(5),

	;companion object {
		val map by lazy { AvatarStyle.values().associateBy(AvatarStyle::value) }
		fun fromValue(type: Int) = map[type]
		val list by lazy { AvatarStyle.values().associateBy(AvatarStyle::name) }
		fun fromValue(type: String) = list[type.toUpperCase()]
	}
}

enum class ListCellType (val value: Int) {
	ONE_LINE(0),
	TWO_LINES(1),
	THREE_LINES(2),

	;companion object {
		val map by lazy { ListCellType.values().associateBy(ListCellType::value) }
		fun fromValue(type: Int) = map[type]
		val list by lazy { ListCellType.values().associateBy(ListCellType::name) }
		fun fromValue(type: String) = list[type.toUpperCase()]
	}
}

enum class ListLeadingViewSize (val value: Int) {
	SMALL(0),
	MEDIUM(1),
	LARGE(2),

	;companion object {
		val map by lazy { ListLeadingViewSize.values().associateBy(ListLeadingViewSize::value) }
		fun fromValue(type: Int) = map[type]
		val list by lazy { ListLeadingViewSize.values().associateBy(ListLeadingViewSize::name) }
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

enum class ListHeaderStyle (val value: Int) {
	BASE(0),
	ACCENT(1),

	;companion object {
		val map by lazy { ListHeaderStyle.values().associateBy(ListHeaderStyle::value) }
		fun fromValue(type: Int) = map[type]
		val list by lazy { ListHeaderStyle.values().associateBy(ListHeaderStyle::name) }
		fun fromValue(type: String) = list[type.toUpperCase()]
	}
}

enum class DrawerBehavior (val value: Int) {
	FULL(0),
	BELOW_STATUS(1),
	BELOW_TOOLBAR(2),
	ANCHORED(3),

	;companion object {
		val map by lazy { DrawerBehavior.values().associateBy(DrawerBehavior::value) }
		fun fromValue(type: Int) = map[type]
		val list by lazy { DrawerBehavior.values().associateBy(DrawerBehavior::name) }
		fun fromValue(type: String) = list[type.toUpperCase()]
	}
}