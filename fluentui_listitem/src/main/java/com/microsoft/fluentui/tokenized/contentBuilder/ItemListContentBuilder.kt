package com.microsoft.fluentui.tokenized.contentBuilder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.*
import com.microsoft.fluentui.tokenized.divider.Divider
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.tabItem.TabItem
import kotlinx.coroutines.launch
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.ceil

data class Item(
    var title: String,
    var subTitle: String? = null,
    var enabled: Boolean = true,
    var onClick: () -> Unit,
    var accessory: @Composable (() -> Unit)? = null,
    var icon: ImageVector? = null
)

// marker interface
internal interface ItemList

// data class for list of horizontal Items
internal data class HorizontalItemList(
    val horizontalItems: List<Item>,
    val header: String? = null,
    val fixedWidth: Boolean = false,
    val tabItemTokens: TabItemTokens? = null
) : ItemList

// data class for list of vertical Items
internal data class VerticalItemList(
    val verticalItems: List<Item>,
    val header: String? = null,
    val listItemTokens: ListItemTokens? = null
) : ItemList

// data class for list of horizontal  grid Items
internal data class VerticalGridItemList(
    val verticalGridItems: List<Item>,
    val header: String? = null,
    val maxItemInRow: Int,
    val equidistant: Boolean = false,
    val tabItemTokens: TabItemTokens? = null
) : ItemList

//data class for divider
internal data class DividerItem(val heightDp: Dp, val dividerToken: DividerTokens?) : ItemList

class ItemListContentBuilder {
    private val listOfItemList: ArrayList<ItemList> = ArrayList()

    private fun add(itemListType: ItemList) {
        listOfItemList.add(itemListType)
    }

    fun addHorizontalList(
        items: List<Item>,
        header: String? = null,
        fixedWidth: Boolean = false,
        tabItemTokens: TabItemTokens? = null
    ): ItemListContentBuilder {
        add(HorizontalItemList(items, header, fixedWidth, tabItemTokens))
        return this
    }

    fun addVerticalList(
        items: List<Item>,
        header: String? = null,
        listItemTokens: ListItemTokens? = null
    ): ItemListContentBuilder {
        add(VerticalItemList(items, header, listItemTokens))
        return this
    }

    fun addVerticalGridList(
        items: List<Item>,
        header: String? = null,
        maxItemInRow: Int = 4,
        equidistant: Boolean = false
    ): ItemListContentBuilder {
        add(VerticalGridItemList(items, header, maxItemInRow, equidistant))
        return this
    }

    fun addDivider(
        heightDp: Dp = 1.dp,
        dividerToken: DividerTokens? = null
    ): ItemListContentBuilder {
        add(DividerItem(heightDp, dividerToken))
        return this
    }

    fun getContent(): @Composable () -> Unit {
        return {
            LazyColumn {
                for (itemList in listOfItemList) {
                    when (itemList) {
                        is HorizontalItemList -> createHorizontalList(
                            itemList.horizontalItems,
                            itemList.header,
                            itemList.fixedWidth,
                            itemList.tabItemTokens
                        )()

                        is VerticalItemList -> createVerticalList(
                            itemList.verticalItems,
                            itemList.header,
                            itemList.listItemTokens
                        )()

                        is VerticalGridItemList -> createVerticalGrid(
                            itemList.verticalGridItems,
                            itemList.header,
                            itemList.maxItemInRow,
                            itemList.equidistant,
                            itemList.tabItemTokens
                        )()

                        is DividerItem -> createDivider(itemList.heightDp, itemList.dividerToken)()
                    }
                }
            }
        }
    }

    private fun createVerticalGrid(
        itemsList: List<Item>,
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
            val size = itemsList.size
            val itemsInRow = min(size, maxItemsInRow)

            items(ceil(size * 1.0 / itemsInRow).toInt()) { row ->
                val token =
                    tabItemTokens
                        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.TabItem] as TabItemTokens
                Layout(
                    modifier = Modifier
                        .background(
                            token.background(
                                TabItemInfo(
                                    TabTextAlignment.VERTICAL,
                                    FluentStyle.Brand
                                )
                            )
                        )
                        .padding(start = 16.dp, end = 16.dp),
                    content = {
                        var col = 0
                        val widthRatio = if ((row + 1) * itemsInRow <= size || !equidistant)
                            1.0f / itemsInRow
                        else
                            1.0f / min(itemsInRow, (size - (row * itemsInRow)))
                        while (col < itemsInRow && (row * itemsInRow + col) < size) {
                            TabItem(
                                title = itemsList[row * itemsInRow + col].title,
                                enabled = itemsList[row * itemsInRow + col].enabled,
                                onClick = itemsList[row * itemsInRow + col].onClick,
                                accessory = itemsList[row * itemsInRow + col].accessory,
                                icon = itemsList[row * itemsInRow + col].icon,
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
        itemList: List<Item>,
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
                val scope = rememberCoroutineScope()
                val lazyListState = rememberLazyListState()
                val token =
                    tabItemTokens
                        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.TabItem] as TabItemTokens
                LazyRow(
                    state = lazyListState, modifier = Modifier
                        .background(
                            token.background(
                                TabItemInfo(
                                    TabTextAlignment.VERTICAL,
                                    FluentStyle.Brand
                                )
                            )
                        )
                        .padding(start = 16.dp)
                        .fillMaxWidth()
                ) {
                    itemsIndexed(itemList) { index, item ->
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
                                        scope.launch {
                                            lazyListState.animateScrollToItem(
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
        itemList: List<Item>,
        header: String?,
        listItemTokens: ListItemTokens? = null
    ): LazyListScope.() -> Unit {
        return {
            if (header != null) {
                item {
                    ListItem.Header(title = header)
                }
            }
            items(itemList) { item ->
                val token = listItemTokens
                    ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.ListItem] as ListItemTokens
                ListItem.Item(
                    text = item.title,
                    subText = item.subTitle,
                    leadingAccessoryView = {
                        if (item.icon != null) {
                            Icon(item.icon!!, null, tint = token.iconColor().rest)
                        }
                    },
                    trailingAccessoryView = item.accessory,
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