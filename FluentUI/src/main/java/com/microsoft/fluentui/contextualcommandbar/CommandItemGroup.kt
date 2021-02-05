/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.contextualcommandbar

class CommandItemGroup {

    /**
     * The identifier of this item group.
     */
    var id: String? = null

    /**
     * [CommandItem]s contained in this group
     */
    var items = arrayListOf<CommandItem>()

    fun addItem(item: CommandItem?): CommandItemGroup {
        item?.let {
            items.add(it)
        }
        return this
    }
}
