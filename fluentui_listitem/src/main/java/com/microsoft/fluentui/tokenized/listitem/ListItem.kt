package com.microsoft.fluentui.tokenized.listitem

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import com.microsoft.fluentui.icons.ListItemIcons
import com.microsoft.fluentui.icons.listitemicons.Chevron
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens.ControlType
import com.microsoft.fluentui.theme.token.FontInfo
import com.microsoft.fluentui.theme.token.GlobalTokens.SpacingTokens.*
import com.microsoft.fluentui.theme.token.controlTokens.*
import com.microsoft.fluentui.theme.token.controlTokens.BorderInset.None
import com.microsoft.fluentui.theme.token.controlTokens.BorderType.NoBorder
import com.microsoft.fluentui.theme.token.controlTokens.ListItemType.*
import com.microsoft.fluentui.theme.token.controlTokens.TextPlacement.Top
import com.microsoft.fluentui.util.dpToPx

val LocalListItemTokens = compositionLocalOf { ListItemTokens() }
val LocalListItemInfo = compositionLocalOf { ListItemInfo() }

@Composable
fun getListItemTokens(): ListItemTokens {
    return LocalListItemTokens.current
}

@Composable
fun getListItemInfo(): ListItemInfo {
    return LocalListItemInfo.current
}

object ListItem {

    private fun Modifier.clickAndSemanticsModifier(
        interactionSource: MutableInteractionSource,
        onClick: () -> Unit,
        enabled: Boolean
    ): Modifier = composed {
        Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(),
                onClickLabel = null,
                enabled = enabled,
                onClick = onClick
            )
    }

    private fun Modifier.borderModifier(
        border: BorderType,
        borderColor: Color,
        borderSize: Float,
        borderInset: Float
    ): Modifier = drawBehind {
        when (border) {
            BorderType.Top -> drawLine(
                borderColor,
                Offset(0f, 0f),
                Offset(size.width, 0f),
                borderSize * density
            )
            BorderType.Bottom -> drawLine(
                borderColor,
                Offset(borderInset, size.height),
                Offset(size.width, size.height),
                borderSize * density
            )
            BorderType.TopBottom -> {
                drawLine(
                    borderColor,
                    Offset(0f, 0f),
                    Offset(size.width, 0f),
                    borderSize * density
                )
                drawLine(
                    borderColor,
                    Offset(borderInset, size.height),
                    Offset(size.width, size.height),
                    borderSize * density
                )
            }
            NoBorder -> {

            }
        }

    }

    /*
    This function calculates the placeholder width for action text
     */
    @Composable
    fun PlaceholderForActionText(
        actionTextComposable: @Composable () -> Unit,
        content: @Composable (width: Dp) -> Unit,
    ) {
        SubcomposeLayout { constraints ->
            val calculatedWidth = subcompose("textToCalculate", actionTextComposable)[0]
                .measure(Constraints()).width.toDp()

            val contentPlaceable = subcompose("inlineText") {
                content(calculatedWidth)
            }[0].measure(constraints)
            layout(contentPlaceable.width, contentPlaceable.height) {
                contentPlaceable.place(0, 0)
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun InlineText(
        description: String,
        actionText: String,
        onClick: () -> Unit,
        descriptionTextColor: Color,
        actionTextColor: Color,
        descriptionTextSize: FontInfo,
        actionTextSize: FontInfo,
        backgroundColor: Color
    ) {
        PlaceholderForActionText(
            actionTextComposable = {
                Text(
                    text = actionText,
                    fontSize = actionTextSize.fontSize.size,
                    fontWeight = actionTextSize.weight
                )
            }
        ) { measuredWidth ->
            val text = buildAnnotatedString {
                if (description.isNotEmpty()) {
                    withStyle(
                        style = SpanStyle(
                            color = descriptionTextColor,
                            fontSize = descriptionTextSize.fontSize.size,
                            fontWeight = descriptionTextSize.weight
                        )
                    ) {
                        append("$description ")
                    }
                }
                //below alternate text will be replaced by composable
                appendInlineContent("key", actionText)
            }
            val widthInDp: TextUnit = with(LocalDensity.current) {
                measuredWidth.toSp()
            }
            val inlineContent = mapOf(
                Pair(
                    "key",
                    InlineTextContent(
                        Placeholder(
                            width = widthInDp,
                            height = 16.sp,
                            placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                        )
                    ) {
                        Surface(
                            onClick = onClick,
                            color = backgroundColor
                        ) {
                            Text(
                                text = actionText,
                                fontSize = actionTextSize.fontSize.size,
                                fontWeight = actionTextSize.weight,
                                color = actionTextColor
                            )
                        }
                    }
                )
            )
            Text(
                text = text,
                inlineContent = inlineContent
            )
        }

    }

    /**
     * Create a Single line or a multi line List item. A multi line list can be formed by providing either a secondary text or a tertiary text
     *
     * @param modifier Optional modifier for List item.
     * @param text Primary text.
     * @param subText Optional secondaryText or a subtitle.
     * @param secondarySubText Optional tertiary text or a footer.
     * @param textAlignment Optional [ListItemTextAlignment] to align text in the center or start at the lead.
     * @param unreadDot Option boolean value that display a dot on leading edge of the accessory view and makes the primary text bold on true
     * @param enabled Optional enable/disable List item
     * @param textMaxLines Optional max visible lines for primary text.
     * @param subTextMaxLines Optional max visible lines for secondary text.
     * @param secondarySubTextMaxLines Optional max visible lines for tertiary text.
     * @param onClick Optional onClick action for list item.
     * @param primaryTextLeadingIcons Optional primary text leading icons(20X20). Supply text icons using [TextIcons]
     * @param primaryTextTrailingIcons Optional primary text trailing icons(20X20). Supply text icons using [TextIcons]
     * @param secondarySubTextLeadingIcons Optional secondary text leading icons(16X16). Supply text icons using [TextIcons]
     * @param secondarySubTextTailingIcons Optional secondary text trailing icons(16X16). Supply text icons using [TextIcons]
     * @param border [BorderType] Optional border for the list item.
     * @param borderInset [BorderInset]Optional borderInset for list item.
     * @param listItemTokens Optional list item tokens for list item appearance.If not provided then list item tokens will be picked from [AppThemeController]
     * @param leadingAccessoryView Optional composable leading accessory view.
     * @param trailingAccessoryView Optional composable trailing accessory view.
     *
     */
    @Composable
    fun Item(
        text: String,
        modifier: Modifier = Modifier,
        subText: String? = null,
        secondarySubText: String? = null,
        textAlignment: ListItemTextAlignment = ListItemTextAlignment.Regular,
        unreadDot: Boolean = false,
        enabled: Boolean = true,
        textMaxLines: Int = 1,
        subTextMaxLines: Int = 1,
        secondarySubTextMaxLines: Int = 1,
        onClick: (() -> Unit)? = null,
        primaryTextLeadingIcons: TextIcons? = null,
        primaryTextTrailingIcons: TextIcons? = null,
        secondarySubTextLeadingIcons: TextIcons? = null,
        secondarySubTextTailingIcons: TextIcons? = null,
        progressIndicator: (@Composable () -> Unit)? = null,
        border: BorderType = NoBorder,
        borderInset: BorderInset = None,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        leadingAccessoryView: (@Composable () -> Unit)? = null,
        trailingAccessoryView: (@Composable () -> Unit)? = null,
        listItemTokens: ListItemTokens? = null
    ) {
        val listItemType = if (subText == null && secondarySubText == null) {
            OneLine
        } else if ((secondarySubText == null && subText != null) || (secondarySubText != null && subText == null)) {
            TwoLine
        } else {
            ThreeLine
        }
        val token = listItemTokens
            ?: FluentTheme.controlTokens.tokens[ControlType.ListItem] as ListItemTokens
        CompositionLocalProvider(
            LocalListItemTokens provides token,
            LocalListItemInfo provides ListItemInfo(
                listItemType = listItemType,
                borderInset = borderInset,
                horizontalSpacing = Medium,
                verticalSpacing = Medium,
                unreadDot = unreadDot
            )
        ) {

            val backgroundColor =
                getListItemTokens().backgroundColor(getListItemInfo()).getColorByState(
                    enabled = true,
                    selected = false,
                    interactionSource = interactionSource
                )
            val cellHeight = getListItemTokens().cellHeight(getListItemInfo())
            val primaryTextSize = getListItemTokens().primaryTextTypography(getListItemInfo())
            val subTextSize =
                getListItemTokens().subTextTypography(getListItemInfo())
            val secondarySubTextSize =
                getListItemTokens().secondarySubTextTypography(getListItemInfo())
            val primaryTextColor = getListItemTokens().primaryTextColor(
                getListItemInfo()
            ).getColorByState(
                enabled = enabled,
                selected = false,
                interactionSource = interactionSource
            )
            val subTextColor = getListItemTokens().subTextColor(
                getListItemInfo()
            ).getColorByState(
                enabled = enabled,
                selected = false,
                interactionSource = interactionSource
            )
            val secondarySubTextColor = getListItemTokens().secondarySubTextColor(
                getListItemInfo()
            ).getColorByState(
                enabled = enabled,
                selected = false,
                interactionSource = interactionSource
            )
            val unreadDotColor = getListItemTokens().unreadDotColor(getListItemInfo())
            val horizontalPadding = getListItemTokens().horizontalPadding(getListItemInfo())
            val verticalPadding = getListItemTokens().verticalPadding(getListItemInfo())
            val borderSize = getListItemTokens().borderSize(getListItemInfo()).value
            val borderInsetToPx =
                with(LocalDensity.current) {
                    getListItemTokens().borderInset(getListItemInfo()).toPx()
                }
            val borderColor = getListItemTokens().borderColor(getListItemInfo()).getColorByState(
                enabled = enabled,
                selected = false,
                interactionSource = interactionSource
            )

            Row(
                modifier
                    .background(backgroundColor)
                    .fillMaxWidth()
                    .heightIn(min = cellHeight)
                    .borderModifier(border, borderColor, borderSize, borderInsetToPx)
                    .clickAndSemanticsModifier(
                        interactionSource,
                        onClick = onClick ?: {},
                        enabled
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (unreadDot) {
                    Canvas(
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .sizeIn(minWidth = 8.dp, minHeight = 8.dp)
                    ) {
                        drawCircle(
                            color = unreadDotColor,
                            style = Fill,
                            radius = dpToPx(4.dp)
                        )
                    }
                }
                if (leadingAccessoryView != null && textAlignment == ListItemTextAlignment.Regular) {
                    Box(
                        Modifier.padding(start = if (unreadDot) 4.dp else horizontalPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        leadingAccessoryView()
                    }
                }
                val contentAlignment =
                    if (textAlignment == ListItemTextAlignment.Regular) Alignment.CenterStart else Alignment.Center
                Box(
                    Modifier
                        .padding(start = horizontalPadding, end = horizontalPadding)
                        .weight(1f), contentAlignment = contentAlignment
                ) {
                    Column(Modifier.padding(top = verticalPadding, bottom = verticalPadding)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (primaryTextLeadingIcons != null) {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    primaryTextLeadingIcons.icon1()
                                    primaryTextLeadingIcons.icon2?.let { it() }
                                }
                            }

                            Text(
                                text = text,
                                fontSize = primaryTextSize.fontSize.size,
                                fontWeight = primaryTextSize.weight,
                                color = primaryTextColor,
                                maxLines = textMaxLines,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (primaryTextTrailingIcons != null) {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    primaryTextTrailingIcons.icon1()
                                    primaryTextTrailingIcons.icon2?.let { it() }
                                }
                            }
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (subText != null && textAlignment == ListItemTextAlignment.Regular) {
                                Text(
                                    text = subText,
                                    fontSize = subTextSize.fontSize.size,
                                    fontWeight = subTextSize.weight,
                                    color = subTextColor,
                                    maxLines = subTextMaxLines,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        if (textAlignment == ListItemTextAlignment.Regular) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                if (progressIndicator != null) {
                                    Row(modifier.padding(top = 7.dp, bottom = 7.dp)) {
                                        progressIndicator()
                                    }
                                } else {
                                    if (secondarySubTextLeadingIcons != null) {
                                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                            secondarySubTextLeadingIcons.icon1()
                                            secondarySubTextLeadingIcons.icon2?.let { it() }
                                        }
                                    }
                                    if (secondarySubText != null) {
                                        Text(
                                            text = secondarySubText,
                                            fontSize = secondarySubTextSize.fontSize.size,
                                            fontWeight = secondarySubTextSize.weight,
                                            color = secondarySubTextColor,
                                            maxLines = secondarySubTextMaxLines,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    if (secondarySubTextTailingIcons != null) {
                                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                            secondarySubTextTailingIcons.icon1()
                                            secondarySubTextTailingIcons.icon2?.let { it() }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (progressIndicator == null && trailingAccessoryView != null && textAlignment == ListItemTextAlignment.Regular) {
                    Box(
                        Modifier.padding(end = horizontalPadding),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        trailingAccessoryView()
                    }
                }

            }
        }
    }

    /**
     * Create a Section header. Section headers are list tiles that delineates sections of a list or grid list
     *
     * @param modifier Optional modifier for List item.
     * @param title Section header title.
     * @param titleMaxLines Optional max visible lines for title.
     * @param accessoryTextTitle Optional accessory text.
     * @param accessoryTextOnClick Optional onClick action for accessory text.
     * @param enabled Optional enable/disable List item
     * @param style [SectionHeaderStyle] Section header style.
     * @param enableChevron Adds a chevron icon before text
     * @param chevronOrientation Pass [ChevronOrientation] to apply chevron icon transition when clicked on the list item. Defaults to static (enter and exit transition are same).
     * @param border [BorderType] Optional border for the list item.
     * @param borderInset [BorderInset] Optional borderInset for list item.
     * @param listItemTokens Optional list item tokens for list item appearance.If not provided then list tokens will be picked from [AppThemeController]
     * @param enter [EnterTransition] used for content appearing transition
     * @param exit [ExitTransition] used for content disappearing transition
     * @param trailingAccessoryView Optional composable trailing accessory view.
     * @param content Composable content to appear or disappear on clicking the list item
     *
     */

    @Composable
    fun SectionHeader(
        title: String,
        modifier: Modifier = Modifier,
        titleMaxLines: Int = 1,
        accessoryTextTitle: String? = null,
        accessoryTextOnClick: (() -> Unit)? = null,
        enabled: Boolean = true,
        style: SectionHeaderStyle = SectionHeaderStyle.Standard,
        enableChevron: Boolean = true,
        chevronOrientation: ChevronOrientation = ChevronOrientation(0f, 0f),
        border: BorderType = NoBorder,
        borderInset: BorderInset = None,
        enableContentOpenCloseTransition: Boolean = false,
        enter: EnterTransition = expandVertically(),
        exit: ExitTransition = shrinkVertically(),
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        trailingAccessoryView: (@Composable () -> Unit)? = null,
        listItemTokens: ListItemTokens? = null,
        content: (@Composable () -> Unit)? = null
    ) {

        val token = listItemTokens
            ?: FluentTheme.controlTokens.tokens[ControlType.ListItem] as ListItemTokens
        CompositionLocalProvider(
            LocalListItemTokens provides token,
            LocalListItemInfo provides ListItemInfo(
                listItemType = SectionHeader,
                borderInset = borderInset,
                horizontalSpacing = Medium,
                verticalSpacing = Small,
                style = style
            )
        ) {

            val backgroundColor =
                getListItemTokens().backgroundColor(getListItemInfo()).getColorByState(
                    enabled = true,
                    selected = false,
                    interactionSource = interactionSource
                )
            val cellHeight = getListItemTokens().cellHeight(getListItemInfo())
            val primaryTextSize =
                getListItemTokens().sectionHeaderPrimaryTextTypography(getListItemInfo())
            val actionTextSize =
                getListItemTokens().sectionHeaderActionTextTypography(getListItemInfo())
            val primaryTextColor = getListItemTokens().primaryTextColor(
                getListItemInfo()
            ).getColorByState(
                enabled = enabled,
                selected = false,
                interactionSource = interactionSource
            )
            val actionTextColor =
                getListItemTokens().actionTextColor(
                    getListItemInfo()
                ).getColorByState(
                    enabled = enabled,
                    selected = false,
                    interactionSource = interactionSource
                )
            val horizontalPadding = getListItemTokens().horizontalPadding(getListItemInfo())
            val verticalPadding = getListItemTokens().verticalPadding(getListItemInfo())
            val borderSize = getListItemTokens().borderSize(getListItemInfo()).value
            val borderInsetToPx =
                with(LocalDensity.current) {
                    getListItemTokens().borderInset(getListItemInfo()).toPx()
                }
            val borderColor = getListItemTokens().borderColor(getListItemInfo()).getColorByState(
                enabled = enabled,
                selected = false,
                interactionSource = interactionSource
            )
            val chevronTint = getListItemTokens().chevronTint(getListItemInfo())
            var expandedState by rememberSaveable { mutableStateOf(false) }
            val rotationState by animateFloatAsState(
                targetValue = if (!enableContentOpenCloseTransition || expandedState)
                    chevronOrientation.enterTransition else chevronOrientation.exitTransition
            )
            Surface(
                modifier = modifier
                    .fillMaxWidth()
                    .heightIn(min = cellHeight)
                    .background(backgroundColor)
                    .clickAndSemanticsModifier(
                        interactionSource,
                        onClick = { expandedState = !expandedState }, enabled
                    )
                    .borderModifier(border, borderColor, borderSize, borderInsetToPx)
            ) {
                Column {
                    Row(
                        modifier
                            .background(backgroundColor)
                            .fillMaxWidth()
                            .heightIn(min = cellHeight)
                            .padding(bottom = verticalPadding),
                        verticalAlignment = Alignment.Bottom
                    ) {

                        Box(
                            Modifier
                                .padding(start = horizontalPadding, end = horizontalPadding)
                                .weight(1f), contentAlignment = Alignment.BottomStart
                        ) {

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (enableChevron) {
                                    Icon(painter = rememberVectorPainter(image = ListItemIcons.Chevron),
                                        contentDescription = "Chevron",
                                        modifier
                                            .clickable { expandedState = !expandedState }
                                            .rotate(rotationState),
                                        tint = chevronTint)
                                }
                                Text(
                                    text = title,
                                    fontSize = primaryTextSize.fontSize.size,
                                    fontWeight = primaryTextSize.weight,
                                    color = primaryTextColor,
                                    maxLines = titleMaxLines,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                        }
                        Row(Modifier.padding(end = horizontalPadding)) {
                            if (accessoryTextTitle != null) {
                                Text(text = accessoryTextTitle,
                                    modifier.clickable(
                                        role = Role.Button,
                                        onClick = accessoryTextOnClick ?: {}
                                    ),
                                    color = actionTextColor,
                                    fontSize = actionTextSize.fontSize.size,
                                    fontWeight = actionTextSize.weight)
                            }
                        }
                        if (trailingAccessoryView != null) {
                            Box(
                                Modifier.padding(end = horizontalPadding),
                                contentAlignment = Alignment.BottomStart
                            ) {
                                trailingAccessoryView()
                            }
                        }
                    }
                    Row {
                        if (content != null) {
                            AnimatedVisibility(
                                visible = !enableContentOpenCloseTransition || expandedState,
                                enter = enter,
                                exit = exit
                            ) {
                                content()
                            }
                        }

                    }
                }
            }
        }
    }

    /**
     * Create a Section description. Section description are lists that provide added context to a list.
     *
     * @param modifier Optional modifier for List item.
     * @param description description text.
     * @param enabled Optional enable/disable List item
     * @param listItemTokens Optional list item tokens for list item appearance.If not provided then list tokens will be picked from [AppThemeController]
     * @param actionText Option boolean to append "Action" text button to the description text.
     * @param descriptionPlacement [TextPlacement] Enum value for placing the description text in the list item.
     * @param onClick Optional onClick action for list item.
     * @param onActionClick Optional onClick action for actionText.
     * @param border [BorderType] Optional border for the list item.
     * @param borderInset [BorderInset] Optional borderInset for list item.
     * @param leadingAccessoryView Optional composable leading accessory view.
     * @param trailingAccessoryView Optional composable trailing accessory view.
     *
     */
    @Composable
    fun SectionDescription(
        description: String,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        actionText: String? = null,
        descriptionPlacement: TextPlacement = Top,
        border: BorderType = NoBorder,
        borderInset: BorderInset = None,
        onClick: (() -> Unit)? = null,
        onActionClick: (() -> Unit)? = null,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        leadingAccessoryView: (@Composable () -> Unit)? = null,
        trailingAccessoryView: (@Composable () -> Unit)? = null,
        listItemTokens: ListItemTokens? = null
    ) {
        val token = listItemTokens
            ?: FluentTheme.controlTokens.tokens[ControlType.ListItem] as ListItemTokens
        CompositionLocalProvider(
            LocalListItemTokens provides token,
            LocalListItemInfo provides ListItemInfo(
                listItemType = SectionDescription,
                horizontalSpacing = Medium,
                verticalSpacing = XSmall,
                borderInset = borderInset,
                placement = descriptionPlacement
            )
        ) {

            val backgroundColor =
                getListItemTokens().backgroundColor(getListItemInfo()).getColorByState(
                    enabled = true,
                    selected = false,
                    interactionSource = interactionSource
                )
            val cellHeight = getListItemTokens().cellHeight(getListItemInfo())
            val descriptionTextSize = getListItemTokens().descriptionTextTypography(getListItemInfo())
            val actionTextSize =
                getListItemTokens().actionTextTypography(getListItemInfo())
            val descriptionTextColor =
                getListItemTokens().descriptionTextColor(
                    getListItemInfo()
                ).getColorByState(
                    enabled = enabled,
                    selected = false,
                    interactionSource = interactionSource
                )
            val actionTextColor = getListItemTokens().actionTextColor(
                getListItemInfo()
            ).getColorByState(
                enabled = enabled,
                selected = false,
                interactionSource = interactionSource
            )
            val horizontalPadding = getListItemTokens().horizontalPadding(getListItemInfo())
            val leadPadding = if (leadingAccessoryView == null) {
                PaddingValues(start = horizontalPadding, end = horizontalPadding)
            } else {
                PaddingValues(
                    start = 0.dp,
                    end = horizontalPadding
                )
            }
            val borderSize = getListItemTokens().borderSize(getListItemInfo()).value
            val borderInsetToPx =
                with(LocalDensity.current) {
                    getListItemTokens().borderInset(getListItemInfo()).toPx()
                }
            val borderColor = getListItemTokens().borderColor(getListItemInfo()).getColorByState(
                enabled = enabled,
                selected = false,
                interactionSource = interactionSource
            )
            val descriptionAlignment =
                getListItemTokens().descriptionPlacement(getListItemInfo())
            val verticalPadding = if (descriptionPlacement == Top) {
                PaddingValues(top = getListItemTokens().verticalPadding(getListItemInfo()))
            } else {
                PaddingValues(bottom = getListItemTokens().verticalPadding(getListItemInfo()))
            }
            Row(
                modifier
                    .fillMaxWidth()
                    .heightIn(min = cellHeight)
                    .background(backgroundColor)
                    .borderModifier(border, borderColor, borderSize, borderInsetToPx)
                    .clickAndSemanticsModifier(
                        interactionSource,
                        onClick = onClick ?: {}, enabled
                    ), verticalAlignment = descriptionAlignment
            ) {
                if (leadingAccessoryView != null && descriptionPlacement == Top) {
                    Box(
                        Modifier
                            .padding(horizontalPadding), contentAlignment = Alignment.Center
                    ) {
                        leadingAccessoryView()
                    }
                }
                Box(
                    Modifier
                        .padding(leadPadding)
                        .padding(verticalPadding)
                        .weight(1f)
                ) {
                    if (!actionText.isNullOrBlank()) {
                        InlineText(
                            description = description,
                            actionText = actionText,
                            onClick = onActionClick ?: {},
                            actionTextSize = actionTextSize,
                            actionTextColor = actionTextColor,
                            descriptionTextColor = descriptionTextColor,
                            descriptionTextSize = descriptionTextSize,
                            backgroundColor = backgroundColor
                        )
                    } else {
                        Text(
                            text = description,
                            color = descriptionTextColor,
                            fontSize = descriptionTextSize.fontSize.size,
                            fontWeight = descriptionTextSize.weight
                        )
                    }
                }
                if (trailingAccessoryView != null) {
                    Box(
                        Modifier.padding(end = horizontalPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        trailingAccessoryView()
                    }
                }
            }
        }
    }

    /**
     * Create a Header. Headers are list tiles that delineates heading of a list or grid list
     *
     * @param modifier Optional modifier for List item.
     * @param title Section header title.
     * @param titleMaxLines Optional max visible lines for title.
     * @param accessoryTextTitle Optional accessory text.
     * @param accessoryTextOnClick Optional onClick action for accessory text.
     * @param enabled Optional enable/disable List item
     * @param style [SectionHeaderStyle] Section header style.
     * @param border [BorderType] Optional border for the list item.
     * @param borderInset [BorderInset] Optional borderInset for list item.
     * @param listItemTokens Optional list item tokens for list item appearance.If not provided then list tokens will be picked from [AppThemeController]
     * @param trailingAccessoryView Optional composable trailing accessory view.
     *
     */

    @Composable
    fun Header(
        title: String,
        modifier: Modifier = Modifier,
        titleMaxLines: Int = 1,
        accessoryTextTitle: String? = null,
        accessoryTextOnClick: (() -> Unit)? = null,
        enabled: Boolean = true,
        style: SectionHeaderStyle = SectionHeaderStyle.Standard,
        border: BorderType = NoBorder,
        borderInset: BorderInset = None,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        trailingAccessoryView: (@Composable () -> Unit)? = null,
        listItemTokens: ListItemTokens? = null
    ) {
        val token = listItemTokens
            ?: FluentTheme.controlTokens.tokens[ControlType.ListItem] as ListItemTokens
        CompositionLocalProvider(
            LocalListItemTokens provides token,
            LocalListItemInfo provides ListItemInfo(
                listItemType = OneLine,
                style = style,
                horizontalSpacing = Medium,
                verticalSpacing = XSmall,
                borderInset = borderInset
            )
        ) {

            val backgroundColor =
                getListItemTokens().backgroundColor(getListItemInfo()).getColorByState(
                    enabled = true,
                    selected = false,
                    interactionSource = interactionSource
                )
            val cellHeight = getListItemTokens().cellHeight(getListItemInfo())
            val primaryTextSize =
                getListItemTokens().sectionHeaderPrimaryTextTypography(getListItemInfo())
            val actionTextSize =
                getListItemTokens().sectionHeaderActionTextTypography(getListItemInfo())
            val primaryTextColor = getListItemTokens().primaryTextColor(
                getListItemInfo()
            ).getColorByState(
                enabled = enabled,
                selected = false,
                interactionSource = interactionSource
            )
            val actionTextColor =
                getListItemTokens().actionTextColor(
                    getListItemInfo()
                ).getColorByState(
                    enabled = enabled,
                    selected = false,
                    interactionSource = interactionSource
                )
            val horizontalPadding = getListItemTokens().horizontalPadding(getListItemInfo())
            val verticalPadding = getListItemTokens().verticalPadding(getListItemInfo())
            val borderSize = getListItemTokens().borderSize(getListItemInfo()).value
            val borderInsetToPx =
                with(LocalDensity.current) {
                    getListItemTokens().borderInset(getListItemInfo()).toPx()
                }
            val borderColor = getListItemTokens().borderColor(getListItemInfo()).getColorByState(
                enabled = enabled,
                selected = false,
                interactionSource = interactionSource
            )

            Surface(
                modifier = modifier
                    .fillMaxWidth()
                    .heightIn(min = cellHeight)
                    .background(backgroundColor)
                    .borderModifier(border, borderColor, borderSize, borderInsetToPx)
                    .focusable(false)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .heightIn(min = cellHeight)
                        .background(backgroundColor)
                        .focusable(true),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = title,
                        modifier = Modifier
                            .padding(
                                start = horizontalPadding,
                                end = horizontalPadding,
                                bottom = verticalPadding
                            )
                            .weight(1f),
                        fontSize = primaryTextSize.fontSize.size,
                        fontWeight = primaryTextSize.weight,
                        color = primaryTextColor,
                        maxLines = titleMaxLines,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (accessoryTextTitle != null) {
                        Text(text = accessoryTextTitle,
                            Modifier
                                .padding(end = horizontalPadding, bottom = verticalPadding)
                                .clickable(
                                    role = Role.Button,
                                    onClick = accessoryTextOnClick ?: {}
                                ),
                            color = actionTextColor,
                            fontSize = actionTextSize.fontSize.size,
                            fontWeight = actionTextSize.weight)
                    }
                    if (trailingAccessoryView != null) {
                        Box(
                            Modifier.padding(end = horizontalPadding, bottom = verticalPadding),
                            contentAlignment = Alignment.BottomStart
                        ) {
                            trailingAccessoryView()
                        }
                    }
                }
            }
        }
    }
}