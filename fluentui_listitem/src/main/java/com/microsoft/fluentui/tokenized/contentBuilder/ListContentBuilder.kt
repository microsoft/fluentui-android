package com.microsoft.fluentui.tokenized.contentBuilder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.*
import com.microsoft.fluentui.tokenized.divider.Divider
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.tabItem.TabItem
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min

data class ItemData(
    var title: String,
    var subTitle: String? = null,
    var enabled: Boolean = true,
    var onClick: () -> Unit,
    var accessory: @Composable (() -> Unit)? = null,
    var icon: ImageVector
)

// marker interface
internal interface ContentData

// data class for list of Items to be placed horizontally
internal data class HorizontalListContentData(
    val itemDataList: List<ItemData>,
    val header: String? = null,
    val fixedWidth: Boolean = false,
    val tabItemTokens: TabItemTokens? = null
) : ContentData

// data class for list of Items to be placed vertically
internal data class VerticalListContentData(
    val itemDataList: List<ItemData>,
    val header: String? = null,
    val listItemTokens: ListItemTokens? = null
) : ContentData

// data class for list of Items to be placed in vertical grid
internal data class VerticalGridContentData(
    val itemDataList: List<ItemData>,
    val header: String? = null,
    val maxItemInRow: Int,
    val equidistant: Boolean = false,
    val tabItemTokens: TabItemTokens? = null
) : ContentData

//data class for divider
internal data class DividerContentData(val heightDp: Dp, val dividerToken: DividerTokens?) :
    ContentData

/*
* Builder to create list of list (vertical, horizontal), grid.
* */
class ListContentBuilder {
    private val listOfContentData: ArrayList<ContentData> = ArrayList()

    private fun add(contentData: ContentData) {
        listOfContentData.add(contentData)
    }

    fun addHorizontalList(
        itemDataList: List<ItemData>,
        header: String? = null,
        fixedWidth: Boolean = false,
        tabItemTokens: TabItemTokens? = null
    ): ListContentBuilder {
        add(HorizontalListContentData(itemDataList, header, fixedWidth, tabItemTokens))
        return this
    }

    fun addVerticalList(
        itemDataList: List<ItemData>,
        header: String? = null,
        listItemTokens: ListItemTokens? = null
    ): ListContentBuilder {
        add(VerticalListContentData(itemDataList, header, listItemTokens))
        return this
    }

    fun addVerticalGrid(
        itemDataList: List<ItemData>,
        header: String? = null,
        maxItemInRow: Int = 4,
        equidistant: Boolean = false,
        tabItemTokens: TabItemTokens? = null
    ): ListContentBuilder {
        add(VerticalGridContentData(itemDataList, header, maxItemInRow, equidistant, tabItemTokens))
        return this
    }

    fun addDivider(
        heightDp: Dp = 1.dp,
        dividerToken: DividerTokens? = null
    ): ListContentBuilder {
        add(DividerContentData(heightDp, dividerToken))
        return this
    }

    fun getContent(): @Composable () -> Unit {
        //TODO As per the thread https://issuetracker.google.com/issues/184670295#comment34 focus
        // navigation issues should resolve with compose version 1.3.0.
        return {
            LazyColumn {
                for (contentData in listOfContentData) {
                    when (contentData) {
                        is HorizontalListContentData -> createHorizontalList(
                            contentData.itemDataList,
                            contentData.header,
                            contentData.fixedWidth,
                            contentData.tabItemTokens
                        )()

                        is VerticalListContentData -> createVerticalList(
                            contentData.itemDataList,
                            contentData.header,
                            contentData.listItemTokens
                        )()

                        is VerticalGridContentData -> createVerticalGrid(
                            contentData.itemDataList,
                            contentData.header,
                            contentData.maxItemInRow,
                            contentData.equidistant,
                            contentData.tabItemTokens
                        )()

                        is DividerContentData -> createDivider(
                            contentData.heightDp,
                            contentData.dividerToken
                        )()
                    }
                }
            }
        }
    }

    private fun createVerticalGrid(
        itemDataList: List<ItemData>,
        header: String?,
        maxItemsInRow: Int,
        equidistant: Boolean,
        tabItemTokens: TabItemTokens? = null
    ): LazyListScope.() -> Unit {
        return {
            if (header != null) {
                item {
                    ListItem.Header(title = header)
                }
            }
            val size = itemDataList.size
            val itemsInRow = min(size, maxItemsInRow)

            items(ceil(size * 1.0 / itemsInRow).toInt()) { row ->
                val token =
                    tabItemTokens
                        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.TabItemControlType] as TabItemTokens
                Layout(
                    modifier = Modifier
                        .background(
                            token.backgroundBrush(
                                tabItemInfo =TabItemInfo(
                                TabTextAlignment.VERTICAL,
                                FluentStyle.Brand
                            )).rest
                        )
                        .padding(start = 16.dp, end = 16.dp),
                    content = {
                        var col = 0
                        val widthRatio = if ((row + 1) * itemsInRow <= size || !equidistant)
                            1.0f / maxItemsInRow
                        else
                            1.0f / min(itemsInRow, (size - (row * itemsInRow)))
                        while (col < itemsInRow && (row * itemsInRow + col) < size) {
                            TabItem(
                                title = itemDataList[row * itemsInRow + col].title,
                                enabled = itemDataList[row * itemsInRow + col].enabled,
                                onClick = itemDataList[row * itemsInRow + col].onClick,
                                accessory = itemDataList[row * itemsInRow + col].accessory,
                                icon = itemDataList[row * itemsInRow + col].icon,
                                modifier = Modifier.fillMaxWidth(widthRatio),
                                tabItemTokens = tabItemTokens
                            )
                            col++
                        }
                    }) { measurables, constraints ->
                    val count = measurables.size
                    val placeables = measurables.map { measurable ->
                        measurable.measure(constraints)
                    }
                    var layoutHeight = 0

                    placeables.forEach {
                        layoutHeight = layoutHeight.coerceAtLeast(it.height)
                    }

                    layout(constraints.maxWidth, layoutHeight) {
                        var xPosition = 0
                        val width = if (equidistant)
                            constraints.maxWidth / count
                        else
                            constraints.maxWidth / maxItemsInRow

                        placeables.forEach { placeable ->
                            placeable.placeRelative(y = 0, x = xPosition)
                            if (placeable != placeables.last())
                                xPosition += width
                        }
                    }
                }
            }
        }
    }

    private fun createHorizontalList(
        itemDataList: List<ItemData>,
        header: String?,
        fixedWidth: Boolean = false,
        tabItemTokens: TabItemTokens? = null
    ): LazyListScope.() -> Unit {
        return {
            if (header != null) {
                item {
                    ListItem.Header(title = header)
                }
            }
            item {
                val rowScope = rememberCoroutineScope()
                val rowLazyListState = rememberLazyListState()
                val token =
                    tabItemTokens
                        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.TabItemControlType] as TabItemTokens
                LazyRow(
                    state = rowLazyListState, modifier = Modifier
                        .background(
                            token.backgroundBrush(
                                TabItemInfo(
                                    TabTextAlignment.VERTICAL,
                                    FluentStyle.Brand
                                )
                            ).rest
                        )
                        .padding(start = 16.dp)
                        .fillMaxWidth()
                ) {
                    itemsIndexed(itemDataList) { index, item ->
                        TabItem(
                            title = item.title,
                            enabled = item.enabled,
                            fixedWidth = fixedWidth,
                            onClick = item.onClick,
                            accessory = item.accessory,
                            icon = item.icon,
                            modifier = Modifier
                                .onFocusEvent { focusState ->
                                    if (focusState.isFocused) {
                                        rowScope.launch {
                                            rowLazyListState.animateScrollToItem(
                                                max(0, index - 2)
                                            )
                                        }
                                    }
                                },
                            tabItemTokens = tabItemTokens
                        )
                    }
                }
            }
        }
    }

    private fun createVerticalList(
        itemDataList: List<ItemData>,
        header: String?,
        listItemTokens: ListItemTokens? = null
    ): LazyListScope.() -> Unit {
        return {
            if (header != null) {
                item {
                    ListItem.Header(title = header)
                }
            }

            items(itemDataList) { item ->
                val themeID =
                    FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
                val token = listItemTokens
                    ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.ListItemControlType] as ListItemTokens
                ListItem.Item(
                    text = item.title,
                    subText = item.subTitle,
                    leadingAccessoryContent = {
                        if (item.icon != null) {
                            Icon(
                                item.icon, null,
                                tint = token.iconColor(ListItemInfo()).let {
                                    if (item.enabled) it.rest else it.disabled
                                }
                            )
                        }
                    },
                    trailingAccessoryContent = item.accessory,
                    enabled = item.enabled,
                    onClick = item.onClick,
                    listItemTokens = listItemTokens
                )
            }
        }
    }

    private fun createDivider(
        heightDp: Dp,
        dividerToken: DividerTokens?
    ): LazyListScope.() -> Unit {
        return {
            item {
                Divider(
                    heightDp, dividerToken
                )
            }
        }
    }
}