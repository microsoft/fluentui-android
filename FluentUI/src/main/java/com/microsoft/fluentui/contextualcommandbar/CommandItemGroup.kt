package com.microsoft.fluentui.contextualcommandbar

class CommandItemGroup {

    var items = arrayListOf<CommandItem>()

    fun addItem(item: CommandItem?): CommandItemGroup {
        item?.let {
            items.add(it)
        }
        return this
    }
}