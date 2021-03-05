/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.bottomsheet

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment

/**
 * [BottomSheet] is used to display a list of menu items in a modal dialog inside of a Fragment that retains state.
 */
class BottomSheet : AppCompatDialogFragment(), BottomSheetItem.OnClickListener {
    companion object {
        private const val ITEMS = "items"
        private const val HEADER_ITEM = "headerItem"

        /**
         * @param items is an ArrayList of [BottomSheetItem]s.
         */
        @JvmStatic
        @JvmOverloads
        fun newInstance(items: ArrayList<BottomSheetItem>, headerItem: BottomSheetItem? = null): BottomSheet {
            val bundle = Bundle()
            bundle.putParcelableArrayList(ITEMS, items)
            bundle.putParcelable(HEADER_ITEM, headerItem)

            val bottomSheet = BottomSheet()
            bottomSheet.arguments = bundle
            return bottomSheet
        }
    }

    interface OnDismissListener {
        fun onBottomSheetDismiss()
    }

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var items: ArrayList<BottomSheetItem>
    private var headerItem: BottomSheetItem? = null
    private var clickedItem: BottomSheetItem? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bundle = savedInstanceState ?: arguments
        items = bundle?.getParcelableArrayList(ITEMS) ?: arrayListOf()
        headerItem = bundle?.getParcelable(HEADER_ITEM)

        bottomSheetDialog = BottomSheetDialog(context!!, items, headerItem, theme)
        bottomSheetDialog.onItemClickListener = this

        return bottomSheetDialog
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(ITEMS, items)
        outState.putParcelable(HEADER_ITEM, headerItem)
    }

    override fun onBottomSheetItemClick(item: BottomSheetItem) {
        clickedItem = item
    }

    // According to Android documentation, DialogFragment owns the Dialog setOnDismissListener callback so this
    // can't be set on the Dialog. Instead onDismiss(android.content.DialogInterface) must be overridden.
    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        clickedItem?.let {
            (parentFragment as? BottomSheetItem.OnClickListener)?.onBottomSheetItemClick(it)
            (activity as? BottomSheetItem.OnClickListener)?.onBottomSheetItemClick(it)
            clickedItem = null
        }

        (parentFragment as? OnDismissListener)?.onBottomSheetDismiss()
        (activity as? OnDismissListener)?.onBottomSheetDismiss()
    }
}