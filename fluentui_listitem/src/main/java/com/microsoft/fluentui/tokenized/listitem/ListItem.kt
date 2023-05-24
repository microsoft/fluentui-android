package com.microsoft.fluentui.tokenized.listitem

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import com.microsoft.fluentui.icons.ListItemIcons
import com.microsoft.fluentui.icons.listitemicons.Chevron
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens.ControlType
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.controlTokens.*
import com.microsoft.fluentui.theme.token.controlTokens.BorderInset.None
import com.microsoft.fluentui.theme.token.controlTokens.BorderType.NoBorder
import com.microsoft.fluentui.theme.token.controlTokens.ListItemType.*
import com.microsoft.fluentui.theme.token.controlTokens.TextPlacement.Bottom
import com.microsoft.fluentui.theme.token.controlTokens.TextPlacement.Top
import com.microsoft.fluentui.util.dpToPx

object ListItem {

    private fun clearSemantics(properties: (SemanticsPropertyReceiver.() -> Unit)?): Modifier {
        return if (properties != null) {
            Modifier.clearAndSetSemantics(properties)
        } else {
            Modifier
        }
    }

    private fun Modifier.clickAndSemanticsModifier(
        interactionSource: MutableInteractionSource,
        onClick: () -> Unit,
        enabled: Boolean,
        rippleColor: Color
    ): Modifier = composed {
        Modifier.clickable(
            interactionSource = interactionSource,
            indication = rememberRipple(color = rippleColor),
            onClickLabel = null,
            enabled = enabled,
            onClick = onClick
        )
    }

    private fun Modifier.borderModifier(
        border: BorderType, borderColor: Color, borderSize: Float, borderInset: Float
    ): Modifier = drawBehind {
        when (border) {
            BorderType.Top -> drawLine(
                borderColor, Offset(0f, 0f), Offset(size.width, 0f), borderSize * density
            )
            BorderType.Bottom -> drawLine(
                borderColor,
                Offset(borderInset, size.height),
                Offset(size.width, size.height),
                borderSize * density
            )
            BorderType.TopBottom -> {
                drawLine(
                    borderColor, Offset(0f, 0f), Offset(size.width, 0f), borderSize * density
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
            val calculatedWidth = subcompose(
                "textToCalculate",
                actionTextComposable
            )[0].measure(Constraints()).width.toDp()

            val contentPlaceable = subcompose("inlineText") {
                content(calculatedWidth)
            }[0].measure(constraints)
            layout(contentPlaceable.width, contentPlaceable.height) {
                contentPlaceable.place(0, 0)
            }
        }
    }

    @Composable
    private fun InlineText(
        description: String,
        actionText: String,
        onClick: () -> Unit,
        descriptionTextColor: Color,
        actionTextColor: Color,
        descriptionTextTypography: TextStyle,
        actionTextTypography: TextStyle,
        backgroundColor: Brush
    ) {
        PlaceholderForActionText(actionTextComposable = {
            BasicText(
                text = actionText,
                style = actionTextTypography,
            )
        }) { measuredWidth ->
            val text = buildAnnotatedString {
                if (description.isNotEmpty()) {
                    withStyle(
                        style = SpanStyle(
                            color = descriptionTextColor,
                            fontSize = descriptionTextTypography.fontSize,
                            fontWeight = descriptionTextTypography.fontWeight
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
                Pair("key", InlineTextContent(
                    Placeholder(
                        width = widthInDp,
                        height = 16.sp,
                        placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                    )
                ) {
                    Box(
                        Modifier
                            .background(backgroundColor)
                            .clickable(
                                onClick = onClick
                            )
                    ) {
                        BasicText(
                            text = actionText,
                            style = actionTextTypography.merge(TextStyle(color = actionTextColor))
                        )
                    }
                })
            )
            BasicText(
                text = text, inlineContent = inlineContent
            )
        }

    }

    /**
     * Create a Single line or a multi line List item. A multi line list can be formed by providing either a secondary text or a tertiary text
     *
     * @param text Primary text.
     * @param modifier Optional modifier for List item.
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
     * @param bottomView Optional bottom view under Text field. If used, trailing view will not be displayed
     * @param leadingAccessoryView Optional composable leading accessory view.
     * @param trailingAccessoryView Optional composable trailing accessory view.
     * @param leadingAccessoryViewAlignment Alignment for leading accessory view to align Top, Bottom or Center
     * @param trailingAccessoryViewAlignment Alignment for trailing accessory view to align Top, Bottom or Center
     * @param textAccessibilityProperties Accessibility properties for the text in list item.
     * @param listItemTokens Optional list item tokens for list item appearance.If not provided then list item tokens will be picked from [AppThemeController]
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
        border: BorderType = NoBorder,
        borderInset: BorderInset = None,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        bottomView: (@Composable () -> Unit)? = null,
        leadingAccessoryView: (@Composable () -> Unit)? = null,
        trailingAccessoryView: (@Composable () -> Unit)? = null,
        leadingAccessoryViewAlignment: Alignment.Vertical = Alignment.CenterVertically,
        trailingAccessoryViewAlignment: Alignment.Vertical = Alignment.CenterVertically,
        textAccessibilityProperties: (SemanticsPropertyReceiver.() -> Unit)? = null,
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
        val listItemInfo = ListItemInfo(
            listItemType = listItemType,
            borderInset = borderInset,
            horizontalSpacing = FluentGlobalTokens.SizeTokens.Size160,
            verticalSpacing = FluentGlobalTokens.SizeTokens.Size120,
            unreadDot = unreadDot
        )
        val backgroundColor =
            token.backgroundBrush(listItemInfo).getBrushByState(
                enabled = true, selected = false, interactionSource = interactionSource
            )
        val primaryTextTypography = token.primaryTextTypography(listItemInfo)
        val subTextTypography = token.subTextTypography(listItemInfo)
        val secondarySubTextTypography =
            token.secondarySubTextTypography(listItemInfo)
        val primaryTextColor = token.primaryTextColor(
            listItemInfo
        ).getColorByState(
            enabled = enabled, selected = false, interactionSource = interactionSource
        )
        val subTextColor = token.subTextColor(
            listItemInfo
        ).getColorByState(
            enabled = enabled, selected = false, interactionSource = interactionSource
        )
        val secondarySubTextColor = token.secondarySubTextColor(
            listItemInfo
        ).getColorByState(
            enabled = enabled, selected = false, interactionSource = interactionSource
        )
        val rippleColor = token.rippleColor(listItemInfo)
        val unreadDotColor = token.unreadDotColor(listItemInfo)
        val padding = token.padding(listItemInfo)
        val borderSize = token.borderSize(listItemInfo).value
        val borderInsetToPx = with(LocalDensity.current) {
            token.borderInset(listItemInfo).toPx()
        }
        val borderColor = token.borderColor(listItemInfo).getColorByState(
            enabled = enabled, selected = false, interactionSource = interactionSource
        )
        val leadingAccessoryAlignment = when(leadingAccessoryViewAlignment){
            Alignment.Top -> Alignment.TopCenter
            Alignment.Bottom -> Alignment.BottomCenter
            else -> Alignment.Center
        }
        val trailingAccessoryAlignment = when(trailingAccessoryViewAlignment){
            Alignment.Top -> Alignment.TopEnd
            Alignment.Bottom -> Alignment.BottomEnd
            else -> Alignment.CenterEnd
        }
        Row(
            modifier
                .background(backgroundColor)
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .borderModifier(border, borderColor, borderSize, borderInsetToPx)
                .clickAndSemanticsModifier(
                    interactionSource, onClick = onClick ?: {}, enabled, rippleColor
                ), verticalAlignment = Alignment.CenterVertically
        ) {
            if (leadingAccessoryView != null && textAlignment == ListItemTextAlignment.Regular) {
                Box(
                    modifier = Modifier
                        .padding(
                            start = if (unreadDot) 4.dp else padding.calculateStartPadding(
                                LocalLayoutDirection.current
                            ),
                            top = if(leadingAccessoryViewAlignment == Alignment.Top) padding.calculateTopPadding() else 0.dp,
                            bottom = if(leadingAccessoryViewAlignment == Alignment.Bottom) padding.calculateBottomPadding() else 0.dp
                        )
                        .fillMaxHeight(), contentAlignment = leadingAccessoryAlignment
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (unreadDot) {
                            Canvas(
                                modifier = Modifier
                                    .padding(start = 4.dp)
                                    .sizeIn(minWidth = 8.dp, minHeight = 8.dp)
                            ) {
                                drawCircle(
                                    color = unreadDotColor, style = Fill, radius = dpToPx(4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                        leadingAccessoryView()
                    }
                }
            }
            val contentAlignment =
                if (textAlignment == ListItemTextAlignment.Regular) Alignment.CenterStart else Alignment.Center
            Box(
                Modifier
                    .padding(horizontal = padding.calculateStartPadding(LocalLayoutDirection.current))
                    .weight(1f)
                    .then(clearSemantics(textAccessibilityProperties)),
                contentAlignment = contentAlignment
            ) {
                Column(Modifier.padding(vertical = padding.calculateTopPadding())) {
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

                        BasicText(
                            text = text,
                            style = primaryTextTypography.merge(TextStyle(color = primaryTextColor)),
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
                            BasicText(
                                text = subText,
                                style = subTextTypography.merge(TextStyle(color = subTextColor)),
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
                            if (bottomView != null) {
                                Row(modifier.padding(top = 7.dp, bottom = 7.dp)) {
                                    bottomView()
                                }
                            } else {
                                if (secondarySubTextLeadingIcons != null) {
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        secondarySubTextLeadingIcons.icon1()
                                        secondarySubTextLeadingIcons.icon2?.let { it() }
                                    }
                                }
                                if (secondarySubText != null) {
                                    BasicText(
                                        text = secondarySubText,
                                        style = secondarySubTextTypography.merge(TextStyle(color = secondarySubTextColor)),
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
            if (bottomView == null && trailingAccessoryView != null && textAlignment == ListItemTextAlignment.Regular) {
                Box(
                    Modifier.padding(top = if(trailingAccessoryViewAlignment == Alignment.Top) padding.calculateTopPadding() else 0.dp,
                        bottom = if(trailingAccessoryViewAlignment == Alignment.Bottom) padding.calculateBottomPadding() else 0.dp,
                        end = padding.calculateEndPadding(LocalLayoutDirection.current)).fillMaxHeight(),
                    contentAlignment = trailingAccessoryAlignment
                ) {
                    trailingAccessoryView()
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
        style: SectionHeaderStyle = SectionHeaderStyle.Bold,
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
        val listItemInfo = ListItemInfo(
            listItemType = SectionHeader,
            borderInset = borderInset,
            horizontalSpacing = FluentGlobalTokens.SizeTokens.Size160,
            verticalSpacing = FluentGlobalTokens.SizeTokens.Size120,
            style = style
        )
        val backgroundColor =
            token.backgroundBrush(listItemInfo).getBrushByState(
                enabled = true, selected = false, interactionSource = interactionSource
            )
        val cellHeight = token.cellHeight(listItemInfo)
        val primaryTextTypography =
            token.sectionHeaderPrimaryTextTypography(listItemInfo)
        val actionTextTypography =
            token.sectionHeaderActionTextTypography(listItemInfo)
        val primaryTextColor = token.primaryTextColor(
            listItemInfo
        ).getColorByState(
            enabled = enabled, selected = false, interactionSource = interactionSource
        )
        val actionTextColor = token.actionTextColor(
            listItemInfo
        ).getColorByState(
            enabled = enabled, selected = false, interactionSource = interactionSource
        )
        val rippleColor = token.rippleColor(listItemInfo)
        val padding = token.padding(listItemInfo)
        val borderSize = token.borderSize(listItemInfo).value
        val borderInsetToPx = with(LocalDensity.current) {
            token.borderInset(listItemInfo).toPx()
        }
        val borderColor = token.borderColor(listItemInfo).getColorByState(
            enabled = enabled, selected = false, interactionSource = interactionSource
        )
        val chevronTint = token.chevronTint(listItemInfo)
        var expandedState by rememberSaveable { mutableStateOf(false) }
        val rotationState by animateFloatAsState(
            targetValue = if (!enableContentOpenCloseTransition || expandedState) chevronOrientation.enterTransition else chevronOrientation.exitTransition
        )
        Box(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = cellHeight)
                .background(backgroundColor)
                .clickAndSemanticsModifier(
                    interactionSource,
                    onClick = { expandedState = !expandedState },
                    enabled,
                    rippleColor
                )
                .borderModifier(border, borderColor, borderSize, borderInsetToPx)
        ) {
            Column {
                Row(
                    Modifier
                        .background(backgroundColor)
                        .fillMaxWidth()
                        .heightIn(min = cellHeight)
                        .padding(bottom = padding.calculateBottomPadding()),
                    verticalAlignment = Alignment.Bottom
                ) {

                    Box(
                        Modifier
                            .padding(
                                horizontal = padding.calculateStartPadding(
                                    LocalLayoutDirection.current
                                )
                            )
                            .weight(1f), contentAlignment = Alignment.BottomStart
                    ) {

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (enableChevron) {
                                Icon(painter = rememberVectorPainter(image = ListItemIcons.Chevron),
                                    contentDescription = "Chevron",
                                    Modifier
                                        .clickable { expandedState = !expandedState }
                                        .rotate(rotationState),
                                    tint = chevronTint)
                            }
                            BasicText(
                                text = title,
                                style = primaryTextTypography.merge(TextStyle(color = primaryTextColor)),
                                maxLines = titleMaxLines,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                    }
                    Row(Modifier.padding(end = padding.calculateEndPadding(LocalLayoutDirection.current))) {
                        if (accessoryTextTitle != null) {
                            BasicText(
                                text = accessoryTextTitle,
                                Modifier.clickable(role = Role.Button,
                                    onClick = accessoryTextOnClick ?: {}),
                                style = actionTextTypography.merge(TextStyle(color = actionTextColor))
                            )
                        }
                    }
                    if (trailingAccessoryView != null) {
                        Box(
                            Modifier.padding(
                                end = padding.calculateEndPadding(
                                    LocalLayoutDirection.current
                                )
                            ), contentAlignment = Alignment.BottomStart
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
        val listItemInfo = ListItemInfo(
            listItemType = SectionDescription,
            horizontalSpacing = FluentGlobalTokens.SizeTokens.Size160,
            verticalSpacing = FluentGlobalTokens.SizeTokens.Size80,
            borderInset = borderInset,
            placement = descriptionPlacement
        )
        val backgroundColor =
            token.backgroundBrush(listItemInfo).getBrushByState(
                enabled = true, selected = false, interactionSource = interactionSource
            )
        val cellHeight = token.cellHeight(listItemInfo)
        val descriptionTextTypography =
            token.descriptionTextTypography(listItemInfo)
        val actionTextTypography = token.actionTextTypography(listItemInfo)
        val descriptionTextColor = token.descriptionTextColor(
            listItemInfo
        ).getColorByState(
            enabled = enabled, selected = false, interactionSource = interactionSource
        )
        val actionTextColor = token.actionTextColor(
            listItemInfo
        ).getColorByState(
            enabled = enabled, selected = false, interactionSource = interactionSource
        )
        val rippleColor = token.rippleColor(listItemInfo)
        val borderSize = token.borderSize(listItemInfo).value
        val borderInsetToPx = with(LocalDensity.current) {
            token.borderInset(listItemInfo).toPx()
        }
        val borderColor = token.borderColor(listItemInfo).getColorByState(
            enabled = enabled, selected = false, interactionSource = interactionSource
        )
        val descriptionAlignment = token.descriptionPlacement(listItemInfo)
        val padding = token.padding(listItemInfo)
        Row(
            modifier
                .fillMaxWidth()
                .heightIn(min = cellHeight)
                .background(backgroundColor)
                .borderModifier(border, borderColor, borderSize, borderInsetToPx)
                .clickAndSemanticsModifier(
                    interactionSource, onClick = onClick ?: {}, enabled, rippleColor
                ), verticalAlignment = descriptionAlignment
        ) {
            if (leadingAccessoryView != null && descriptionPlacement == Top) {
                Box(
                    Modifier.padding(padding.calculateStartPadding(LocalLayoutDirection.current)),
                    contentAlignment = Alignment.Center
                ) {
                    leadingAccessoryView()
                }
            }
            Box(
                Modifier
                    .padding(
                        start = if (leadingAccessoryView == null) padding.calculateStartPadding(
                            LocalLayoutDirection.current
                        ) else 0.dp,
                        end = padding.calculateEndPadding(LocalLayoutDirection.current),
                        top = if (descriptionPlacement == Top) padding.calculateTopPadding() else 0.dp,
                        bottom = if (descriptionPlacement == Bottom) padding.calculateTopPadding() else 0.dp
                    )
                    .weight(1f)
            ) {
                if (!actionText.isNullOrBlank()) {
                    InlineText(
                        description = description,
                        actionText = actionText,
                        onClick = onActionClick ?: {},
                        actionTextTypography = actionTextTypography,
                        actionTextColor = actionTextColor,
                        descriptionTextColor = descriptionTextColor,
                        descriptionTextTypography = descriptionTextTypography,
                        backgroundColor = backgroundColor
                    )
                } else {
                    BasicText(
                        text = description,
                        style = descriptionTextTypography.merge(TextStyle(color = descriptionTextColor))
                    )
                }
            }
            if (trailingAccessoryView != null) {
                Box(
                    Modifier.padding(end = padding.calculateEndPadding(LocalLayoutDirection.current)),
                    contentAlignment = Alignment.Center
                ) {
                    trailingAccessoryView()
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
        style: SectionHeaderStyle = SectionHeaderStyle.Bold,
        border: BorderType = NoBorder,
        borderInset: BorderInset = None,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        trailingAccessoryView: (@Composable () -> Unit)? = null,
        listItemTokens: ListItemTokens? = null
    ) {
        val token = listItemTokens
            ?: FluentTheme.controlTokens.tokens[ControlType.ListItem] as ListItemTokens
        val listItemInfo = ListItemInfo(
            listItemType = OneLine,
            style = style,
            horizontalSpacing = FluentGlobalTokens.SizeTokens.Size160,
            verticalSpacing = FluentGlobalTokens.SizeTokens.Size80,
            borderInset = borderInset
        )
        val backgroundColor =
            token.backgroundBrush(listItemInfo).getBrushByState(
                enabled = true, selected = false, interactionSource = interactionSource
            )
        val cellHeight = token.cellHeight(listItemInfo)
        val primaryTextTypography =
            token.sectionHeaderPrimaryTextTypography(listItemInfo)
        val actionTextTypography =
            token.sectionHeaderActionTextTypography(listItemInfo)
        val primaryTextColor = token.primaryTextColor(
            listItemInfo
        ).getColorByState(
            enabled = enabled, selected = false, interactionSource = interactionSource
        )
        val actionTextColor = token.actionTextColor(
            listItemInfo
        ).getColorByState(
            enabled = enabled, selected = false, interactionSource = interactionSource
        )
        val padding = token.padding(listItemInfo)
        val borderSize = token.borderSize(listItemInfo).value
        val borderInsetToPx = with(LocalDensity.current) {
            token.borderInset(listItemInfo).toPx()
        }
        val borderColor = token.borderColor(listItemInfo).getColorByState(
            enabled = enabled, selected = false, interactionSource = interactionSource
        )

        Box(
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
                    .focusable(true), verticalAlignment = Alignment.Bottom
            ) {
                BasicText(
                    text = title,
                    modifier = Modifier
                        .padding(
                            start = padding.calculateStartPadding(LocalLayoutDirection.current),
                            end = padding.calculateEndPadding(LocalLayoutDirection.current),
                            bottom = padding.calculateBottomPadding()
                        )
                        .weight(1f),
                    style = primaryTextTypography.merge(TextStyle(color = primaryTextColor)),
                    maxLines = titleMaxLines,
                    overflow = TextOverflow.Ellipsis
                )

                if (accessoryTextTitle != null) {
                    BasicText(
                        text = accessoryTextTitle,
                        Modifier
                            .padding(
                                end = padding.calculateEndPadding(LocalLayoutDirection.current),
                                bottom = padding.calculateBottomPadding()
                            )
                            .clickable(
                                role = Role.Button,
                                onClick = accessoryTextOnClick ?: {}),
                        style = actionTextTypography.merge(TextStyle(color = actionTextColor))
                    )
                }
                if (trailingAccessoryView != null) {
                    Box(
                        Modifier.padding(
                            end = padding.calculateEndPadding(LocalLayoutDirection.current),
                            bottom = padding.calculateBottomPadding()
                        ), contentAlignment = Alignment.BottomStart
                    ) {
                        trailingAccessoryView()
                    }
                }
            }
        }
    }
}