package com.microsoft.fluentui.vNext.listitem
import android.text.TextUtils

interface IBaseListItem {
    var title: String
    var titleTruncateAt: TextUtils.TruncateAt
    var background: Int
}