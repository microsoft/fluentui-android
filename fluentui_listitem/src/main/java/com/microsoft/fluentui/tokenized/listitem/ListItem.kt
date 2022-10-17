package com.microsoft.fluentui.tokenized.listitem

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.microsoft.fluentui.theme.token.controlTokens.ListTextType.SecondarySubText
import com.microsoft.fluentui.theme.token.controlTokens.ListTextType.SubText
import com.microsoft.fluentui.theme.token.controlTokens.TextPlacement.Top

val LocalListItemTokens = compositionLocalOf { ListItemTokens() }

@Composable
fun getListItemTokens(): ListItemTokens {
    return LocalListItemTokens.current
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
                onClick = onClick,
                role = Role.Tab
            )
    }
    private fun Modifier.borderModifier(
        border: BorderType,
        borderColor: Color,
        borderSize: Float,
        borderInset: Float
    ): Modifier = composed {
        Modifier.drawBehind {
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
            }
        }
    }
    @Composable
    private fun inlineText(description: String,
                   actionText: String,
                   onClick: () -> Unit,
                   descriptionTextColor: Color,
                   actionTextColor: Color,
                   descriptionTextSize: FontInfo,
                   actionTextSize: FontInfo
        ){
        val text = buildAnnotatedString {
            if (description != null && description.isNotEmpty()) {
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
            appendInlineContent(actionText, "text")
        }

        val inlineContent = mapOf(
            Pair(
                actionText,
                InlineTextContent(
                    Placeholder(
                        width = 50.sp,
                        height = 20.sp,
                        placeholderVerticalAlign = PlaceholderVerticalAlign.TextTop
                    )
                ) {
                    Text(text = actionText,
                        modifier = Modifier.clickable(
                            enabled = true,
                            onClickLabel = actionText,
                            role = Role.Button,
                            onClick = onClick),
                        fontSize = actionTextSize.fontSize.size,
                        color = actionTextColor)
                }
            )
        )
        Text(text = text,
            inlineContent = inlineContent)
    }

    /**
     * Create a Single line or a multi line List item. A multi line list can be formed by providing either a secondary text or a tertiary text
     *
     * @param modifier Optional modifier for List item.
     * @param text Primary text.
     * @param subText Optional secondaryText or a subtitle.
     * @param secondarySubText Optional tertiary text or a footer.
     * @param textAlignment Optional [ListItemTextAlignment] to align text in the center or start at the lead.
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
     * @param listItemTokens Optional list item tokens for list item appearance.If not provided then drawer tokens will be picked from [AppThemeController]
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
        listItemTokens: ListItemTokens? = null,
        leadingAccessoryView: (@Composable () -> Unit)? = null,
        trailingAccessoryView: (@Composable () -> Unit)? = null,
    ) {

        val token = listItemTokens
            ?: FluentTheme.controlTokens.tokens[ControlType.ListItem] as ListItemTokens
        CompositionLocalProvider(LocalListItemTokens provides token) {
            var listItemType = if (subText == null && secondarySubText == null) {
                OneLine
            } else if ((secondarySubText == null && subText != null) || (secondarySubText != null && subText == null)) {
                TwoLine
            } else {
                ThreeLine
            }
            var isTertiaryTextIconsRequired = false
            if (listItemType == TwoLine && subText == null) {
                isTertiaryTextIconsRequired = true
            }
            val backgroundColor = getColorByState(
                stateData = getListItemTokens().backgroundColor(),
                enabled = true,
                interactionSource = interactionSource
            )
            val cellHeight = getListItemTokens().cellHeight(listItemType = listItemType)
            val textSize = getListItemTokens().textSize(textType = ListTextType.Text)
            val subTextSize =
                getListItemTokens().textSize(textType = SubText)
            var secondarySubTextSize =
                getListItemTokens().textSize(textType = SecondarySubText)
            val textColor = getColorByState(
                stateData = getListItemTokens().textColor(
                    textType = ListTextType.Text
                ),
                enabled = enabled,
                interactionSource = interactionSource
            )
            val subTextColor = getColorByState(
                stateData = getListItemTokens().textColor(textType = SubText),
                enabled = enabled,
                interactionSource = interactionSource
            )
            var secondarySubTextColor = getColorByState(
                stateData = getListItemTokens().textColor(
                    textType = SecondarySubText
                ),
                enabled = enabled,
                interactionSource = interactionSource
            )
            val horizontalPadding = getListItemTokens().padding(Medium)
            val verticalPadding = getListItemTokens().padding(Small)
            val borderSize = getListItemTokens().borderSize().value
            val borderInset =
                with(LocalDensity.current) {
                    getListItemTokens().borderInset(inset = borderInset).toPx()
                }
            val borderColor = getColorByState(
                stateData = getListItemTokens().borderColor(),
                enabled = enabled,
                interactionSource = interactionSource
            )

            Row(
                modifier
                    .background(backgroundColor)
                    .fillMaxWidth()
                    .heightIn(min = cellHeight)
                    .borderModifier(border, borderColor, borderSize, borderInset)
                    .clickAndSemanticsModifier(
                        interactionSource,
                        onClick = onClick ?: {},
                        enabled
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingAccessoryView != null && textAlignment == ListItemTextAlignment.Regular) {
                    Box(
                        Modifier.padding(start = horizontalPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        leadingAccessoryView()
                    }
                }
                var contentAlignment =
                    if (textAlignment == ListItemTextAlignment.Regular) Alignment.CenterStart else Alignment.Center
                Box(
                    Modifier
                        .padding(start = horizontalPadding, end = horizontalPadding)
                        .weight(1f), contentAlignment = contentAlignment
                ) {
                    Column(Modifier.padding(top = verticalPadding, bottom = verticalPadding)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (primaryTextLeadingIcons != null) {
                                primaryTextLeadingIcons.icon1()
                                primaryTextLeadingIcons.icon2?.let { it() }
                            }

                            Text(
                                text = text,
                                fontSize = textSize.fontSize.size,
                                fontWeight = textSize.weight,
                                color = textColor,
                                maxLines = textMaxLines,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (primaryTextTrailingIcons != null) {
                                primaryTextTrailingIcons.icon1()
                                primaryTextTrailingIcons.icon2?.let { it() }
                            }
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (subText != null && textAlignment == ListItemTextAlignment.Regular) {
                                Row {
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
                        }
                        if (textAlignment == ListItemTextAlignment.Regular) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if ((isTertiaryTextIconsRequired || listItemType == ThreeLine) && progressIndicator != null) {
                                    Row(modifier.padding(top = 7.dp, bottom = 7.dp)) {
                                        progressIndicator()
                                    }
                                } else {
                                    if ((isTertiaryTextIconsRequired || listItemType == ThreeLine) && secondarySubTextLeadingIcons != null) {
                                        secondarySubTextLeadingIcons.icon1()
                                        secondarySubTextLeadingIcons.icon2?.let { it() }
                                    }
                                    if (secondarySubText != null) {
                                        Row(modifier.padding(top = 1.dp)) {
                                            Text(
                                                text = secondarySubText,
                                                fontSize = secondarySubTextSize.fontSize.size,
                                                fontWeight = secondarySubTextSize.weight,
                                                color = secondarySubTextColor,
                                                maxLines = secondarySubTextMaxLines,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                    if ((isTertiaryTextIconsRequired || listItemType == ThreeLine) && secondarySubTextTailingIcons != null) {
                                        secondarySubTextTailingIcons.icon1()
                                        secondarySubTextTailingIcons.icon2?.let { it() }
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
     * @param listItemTokens Optional list item tokens for list item appearance.If not provided then drawer tokens will be picked from [AppThemeController]
     * @param enter [EnterTransition] used for content appearing transition
     * @param exit [ExitTransition] used for content disappearing transition
     * @param trailingAccessoryView Optional composable trailing accessory view.
     * @param content Composable content to appear or disappear on clicking the list item
     *
     */
    @OptIn(ExperimentalMaterialApi::class)
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
        enter: EnterTransition = slideInVertically(
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearOutSlowInEasing
            )
        ),
        exit: ExitTransition = slideOutVertically(
            animationSpec = tween(
                durationMillis = 250,
                easing = FastOutLinearInEasing
            )
        ),
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        listItemTokens: ListItemTokens? = null,
        trailingAccessoryView: (@Composable () -> Unit)? = null,
        content: (@Composable () -> Unit)? = null
    ) {

        val token = listItemTokens
            ?: FluentTheme.controlTokens.tokens[ControlType.ListItem] as ListItemTokens
        CompositionLocalProvider(LocalListItemTokens provides token) {

            val backgroundColor = getColorByState(
                stateData = getListItemTokens().backgroundColor(),
                enabled = true,
                interactionSource = interactionSource
            )
            val cellHeight = getListItemTokens().cellHeight(listItemType = OneLine)
            val textSize =
                getListItemTokens().textSize(textType = ListTextType.Text, style = style)
            val actionTextSize =
                getListItemTokens().textSize(textType = ListTextType.ActionText, style = style)
            val textColor = getColorByState(
                stateData = getListItemTokens().textColor(
                    textType = ListTextType.Text
                ),
                enabled = enabled,
                interactionSource = interactionSource
            )
            val actionTextColor =
                getColorByState(
                    stateData = getListItemTokens().textColor(
                        textType = ListTextType.ActionText
                    ),
                    enabled = enabled,
                    interactionSource = interactionSource
                )
            val horizontalPadding = getListItemTokens().padding(Medium)
            val verticalPadding = getListItemTokens().padding(size = XSmall)
            val borderSize = getListItemTokens().borderSize().value
            val borderInset =
                with(LocalDensity.current) {
                    getListItemTokens().borderInset(inset = borderInset).toPx()
                }
            val borderColor = getColorByState(
                stateData = getListItemTokens().borderColor(),
                enabled = enabled,
                interactionSource = interactionSource
            )
            val chevronTint = getListItemTokens().chevronTint()
            var expandedState by remember { mutableStateOf(false) }
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
                    .borderModifier(border, borderColor, borderSize, borderInset)
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                ) {
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
                                    fontSize = textSize.fontSize.size,
                                    fontWeight = textSize.weight,
                                    color = textColor,
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
                    AnimatedVisibility(
                        visible = !enableContentOpenCloseTransition || expandedState,
                        enter = enter,
                        exit = exit
                    ) {
                        if (content != null) {
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
     * @param listItemTokens Optional list item tokens for list item appearance.If not provided then drawer tokens will be picked from [AppThemeController]
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
        listItemTokens: ListItemTokens? = null,
        leadingAccessoryView: (@Composable () -> Unit)? = null,
        trailingAccessoryView: (@Composable () -> Unit)? = null
    ) {
        val token = listItemTokens
            ?: FluentTheme.controlTokens.tokens[ControlType.ListItem] as ListItemTokens
        CompositionLocalProvider(LocalListItemTokens provides token) {

            val backgroundColor = getColorByState(
                stateData = getListItemTokens().backgroundColor(),
                enabled = true,
                interactionSource = interactionSource
            )
            val cellHeight = getListItemTokens().cellHeight(listItemType = SectionDescription)
            val textSize = getListItemTokens().textSize(textType = ListTextType.DescriptionText)
            val actionTextSize =
                getListItemTokens().textSize(textType = ListTextType.ActionText)
            val textColor =
                getColorByState(
                    stateData = getListItemTokens().textColor(
                        textType = ListTextType.DescriptionText
                    ),
                    enabled = enabled,
                    interactionSource = interactionSource
                )
            val actionTextColor = getColorByState(
                stateData = getListItemTokens().textColor(
                    textType = ListTextType.ActionText
                ),
                enabled = enabled,
                interactionSource = interactionSource
            )
            val horizontalPadding = getListItemTokens().padding(Medium)
            val borderSize = getListItemTokens().borderSize().value
            val borderInset =
                with(LocalDensity.current) {
                    getListItemTokens().borderInset(inset = borderInset).toPx()
                }
            val borderColor = getColorByState(
                stateData = getListItemTokens().borderColor(),
                enabled = enabled,
                interactionSource = interactionSource
            )
            val descriptionAlignment =
                getListItemTokens().descriptionPlacement(placement = descriptionPlacement)
            val verticalPadding = if (descriptionPlacement == Top) {
                PaddingValues(top = getListItemTokens().padding(size = XSmall))
            } else {
                PaddingValues(bottom = getListItemTokens().padding(size = XSmall))
            }
            Row(
                modifier
                    .fillMaxWidth()
                    .heightIn(min = cellHeight)
                    .background(backgroundColor)
                    .borderModifier(border, borderColor, borderSize, borderInset)
                    .clickAndSemanticsModifier(
                        interactionSource,
                        onClick = onClick ?: {}, enabled
                    ), verticalAlignment = descriptionAlignment
            ) {
                if (leadingAccessoryView != null) {
                    Box(
                        Modifier
                            .padding(start = horizontalPadding)
                            .padding(verticalPadding), contentAlignment = Alignment.Center
                    ) {
                        leadingAccessoryView()
                    }
                }
                Box(
                    Modifier
                        .padding(start = horizontalPadding)
                        .padding(verticalPadding)
                        .weight(1f)
                ) {
                    if (actionText != null) {
                        inlineText(description = description,
                            actionText = actionText,
                            onClick = onActionClick ?: {},
                            actionTextSize = actionTextSize,
                            actionTextColor = actionTextColor,
                            descriptionTextColor = textColor,
                            descriptionTextSize = textSize)
                    } else {
                        Text(
                            text = description,
                            color = textColor,
                            fontSize = textSize.fontSize.size,
                            fontWeight = textSize.weight
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
}