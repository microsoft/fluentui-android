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
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.listitem.R
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens.ControlType
import com.microsoft.fluentui.theme.token.FontInfo
import com.microsoft.fluentui.theme.token.GlobalTokens.SpacingTokens.*
import com.microsoft.fluentui.theme.token.controlTokens.*
import com.microsoft.fluentui.theme.token.controlTokens.BorderInset.None
import com.microsoft.fluentui.theme.token.controlTokens.BorderType.NoBorder
import com.microsoft.fluentui.theme.token.controlTokens.ListItemType.*
import com.microsoft.fluentui.theme.token.controlTokens.ListTextType.SecondarySubLabelText
import com.microsoft.fluentui.theme.token.controlTokens.ListTextType.SubLabelText
import com.microsoft.fluentui.theme.token.controlTokens.TextPlacement.Top

val LocalListItemTokens = compositionLocalOf { ListItemTokens() }

@Composable
fun getListItemTokens(): ListItemTokens {
    return LocalListItemTokens.current
}

class ListItem {
    companion object {

        private fun Modifier.clickAndSemanticsModifier(
            interactionSource: MutableInteractionSource,
            onClick: () -> Unit
        ): Modifier = composed {
            Modifier.clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(),
                onClickLabel = null,
                role = Role.Tab,
                onClick = onClick
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
        private fun descriptionText(
            description: String,
            text: String,
            onClick: () -> Unit,
            descriptionTextColor: Color,
            actionTextColor: Color,
            descriptionTextSize: FontInfo,
            actionTextSize: FontInfo
        ) {
            val annotatedText = buildAnnotatedString {
                if (description != null && description.isNotEmpty()) {
                    withStyle(
                        style = SpanStyle(
                            color = descriptionTextColor,
                            fontSize = descriptionTextSize.fontSize.size,
                            fontWeight = descriptionTextSize.weight
                        )
                    ) {
                        append(description)
                    }
                }
                pushStringAnnotation(
                    tag = "Action",
                    annotation = text
                )
                withStyle(
                    style = SpanStyle(
                        color = actionTextColor,
                        fontSize = actionTextSize.fontSize.size,
                        fontWeight = actionTextSize.weight
                    )
                ) {
                    append(text)
                }
                pop()
            }

            ClickableText(
                text = annotatedText,
                onClick = { offset ->
                    var list = annotatedText.getStringAnnotations(
                        tag = "Action",// tag which you used in the buildAnnotatedString
                        start = offset,
                        end = offset
                    )
                    if (list.isNotEmpty()) {
                        onClick()
                    }
                }
            )
        }

        /**
         * Create a Single line or a multi line List item. A multi line list can be formed by providing either a secondary text or a tertiary text
         *
         * @param modifier Optional modifier for List item.
         * @param text Primary text.
         * @param subText Optional secondaryText or a subtitle.
         * @param secondarySubText Optional tertiary text or a footer.
         * @param enableCenterText Optional boolean to align text in the center.
         * @param textMaxLines Optional max visible lines for primary text.
         * @param subTextMaxLines Optional max visible lines for secondary text.
         * @param secondarySubTextMaxLines Optional max visible lines for tertiary text.
         * @param onClick Optional onClick action for list item.
         * @param primaryLeadingTextIcons Optional primary text leading icons(20X20). Supply text icons using [TextIcons]
         * @param primaryTrailingTextIcons Optional primary text trailing icons(20X20). Supply text icons using [TextIcons]
         * @param secondaryLeadingTextIcons Optional secondary text leading icons(16X16). Supply text icons using [TextIcons]
         * @param secondaryTailingTextIcons Optional secondary text trailing icons(16X16). Supply text icons using [TextIcons]
         * @param border [BorderType] Optional border for the list item.
         * @param borderInset [BorderInset]Optional borderInset for list item.
         * @param listItemTokens Optional list item tokens for list item appearance.If not provided then drawer tokens will be picked from [AppThemeController]
         * @param leadingAccessoryView Optional composable leading accessory view.
         * @param trailingAccessoryView Optional composable trailing accessory view.
         *
         */
        @Composable
        fun Item(
            modifier: Modifier = Modifier,
            text: String,
            subText: String? = null,
            secondarySubText: String? = null,
            enableCenterText: Boolean = false,
            textMaxLines: Int = 1,
            subTextMaxLines: Int = 1,
            secondarySubTextMaxLines: Int = 1,
            onClick: (() -> Unit)? = null,
            primaryLeadingTextIcons: TextIcons? = null,
            primaryTrailingTextIcons: TextIcons? = null,
            secondaryLeadingTextIcons: TextIcons? = null,
            secondaryTailingTextIcons: TextIcons? = null,
            progressIndicator: (@Composable () -> Unit)? = null,
            border: BorderType = NoBorder,
            borderInset: BorderInset = None,
            listItemTokens: ListItemTokens? = null,
            interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
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
                if(listItemType == TwoLine && subText == null){
                    isTertiaryTextIconsRequired = true
                }
                val backgroundColor = getColorByState(
                    stateData = getListItemTokens().backgroundColor(),
                    enabled = true,
                    interactionSource = interactionSource
                )
                val cellHeight = getListItemTokens().cellHeight(listItemType = listItemType)
                val textSize = getListItemTokens().textSize(textType = ListTextType.Text)
                val subLabelSize =
                    getListItemTokens().textSize(textType = SubLabelText)
                var secondarySubLabelSize =
                    getListItemTokens().textSize(textType = SecondarySubLabelText)
                val textColor = getColorByState(
                    stateData = getListItemTokens().textColor(textType = ListTextType.Text),
                    enabled = true,
                    interactionSource = interactionSource
                )
                val subLabelColor = getColorByState(
                    stateData = getListItemTokens().textColor(textType = SubLabelText),
                    enabled = true,
                    interactionSource = interactionSource
                )
                var secondarySubLabelColor = getColorByState(
                    stateData = getListItemTokens().textColor(textType = SecondarySubLabelText),
                    enabled = true,
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
                    enabled = true,
                    interactionSource = interactionSource
                )
                Row(
                    modifier
                        .background(backgroundColor)
                        .fillMaxWidth()
                        .heightIn(min = cellHeight)
                        .borderModifier(border, borderColor, borderSize, borderInset)
                        .clickAndSemanticsModifier(interactionSource, onClick = onClick ?: {}),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (leadingAccessoryView != null) {
                        Box(
                            Modifier.padding(start = horizontalPadding),
                            contentAlignment = Alignment.Center
                        ) {
                            leadingAccessoryView()
                        }
                    }
                    var contentAlignment =
                        if (enableCenterText) Alignment.Center else Alignment.CenterStart
                    Box(
                        Modifier
                            .padding(start = horizontalPadding, end = horizontalPadding)
                            .weight(1f), contentAlignment = contentAlignment
                    ){
                        Column(Modifier.padding(top = verticalPadding, bottom = verticalPadding)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if(primaryLeadingTextIcons != null){
                                    primaryLeadingTextIcons.icon1()
                                    primaryLeadingTextIcons.icon2?.let { it() }
                                }

                                Text(
                                    text = text,
                                    fontSize = textSize.fontSize.size,
                                    fontWeight = textSize.weight,
                                    color = textColor,
                                    maxLines = textMaxLines,
                                    overflow = TextOverflow.Ellipsis
                                )
                                if(primaryTrailingTextIcons != null){
                                    primaryTrailingTextIcons.icon1()
                                    primaryTrailingTextIcons.icon2?.let { it() }
                                }
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (subText != null) {
                                    Row {
                                        Text(
                                            text = subText,
                                            fontSize = subLabelSize.fontSize.size,
                                            fontWeight = subLabelSize.weight,
                                            color = subLabelColor,
                                            maxLines = subTextMaxLines,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                            Row(verticalAlignment = Alignment.CenterVertically){
                                if((isTertiaryTextIconsRequired || listItemType == ThreeLine) && progressIndicator != null){
                                    Row(modifier.padding(top = 7.dp, bottom = 7.dp)) {
                                        progressIndicator()
                                    }
                                }else{
                                    if((isTertiaryTextIconsRequired || listItemType == ThreeLine) && secondaryLeadingTextIcons != null){
                                        secondaryLeadingTextIcons.icon1()
                                        secondaryLeadingTextIcons.icon2?.let { it() }
                                    }
                                    if (secondarySubText != null) {
                                        Row(modifier.padding(top = 1.dp)) {
                                            Text(
                                                text = secondarySubText,
                                                fontSize = secondarySubLabelSize.fontSize.size,
                                                fontWeight = secondarySubLabelSize.weight,
                                                color = secondarySubLabelColor,
                                                maxLines = secondarySubTextMaxLines,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                    if((isTertiaryTextIconsRequired || listItemType == ThreeLine) && secondaryTailingTextIcons != null){
                                        secondaryTailingTextIcons.icon1()
                                        secondaryTailingTextIcons.icon2?.let { it() }
                                    }
                                }
                            }
                        }
                    }
                    if (progressIndicator == null && trailingAccessoryView != null) {
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
         * @param style [SectionHeaderStyle] Section header style.
         * @param enableChevron Adds a chevron icon before text
         * @param chevronOrientation Pass [ChevronTransition] to apply chevron icon transition when clicked on the list item. Defaults to static (enter and exit transition are same).
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
            modifier: Modifier = Modifier,
            title: String,
            titleMaxLines: Int = 1,
            accessoryTextTitle: String? = null,
            accessoryTextOnClick: (() -> Unit)? = null,
            listItemTokens: ListItemTokens? = null,
            style: SectionHeaderStyle = SectionHeaderStyle.Standard,
            enableChevron: Boolean = true,
            chevronOrientation: ChevronTransition = ChevronTransition(0f, 0f),
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
                val textColor = getListItemTokens().textColor(textType = ListTextType.Text)
                val actionTextColor =
                    getListItemTokens().textColor(textType = ListTextType.ActionText)
                val horizontalPadding = getListItemTokens().padding(Medium)
                val verticalPadding = getListItemTokens().padding(size = XSmall)
                val borderSize = getListItemTokens().borderSize().value
                val borderInset =
                    with(LocalDensity.current) {
                        getListItemTokens().borderInset(inset = borderInset).toPx()
                    }
                val borderColor = getColorByState(
                    stateData = getListItemTokens().borderColor(),
                    enabled = true,
                    interactionSource = interactionSource
                )
                val chevronTint = getListItemTokens().chevronTint()
                var expandedState by remember { mutableStateOf(false) }
                val rotationState by animateFloatAsState(
                    targetValue = if (!enableContentOpenCloseTransition || expandedState) chevronOrientation.enterTransition else chevronOrientation.exitTransition
                )
                Surface(
                    modifier = modifier
                        .fillMaxWidth()
                        .heightIn(min = cellHeight)
                        .background(backgroundColor),
                    onClick = {
                        expandedState = !expandedState
                    },
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .borderModifier(border, borderColor, borderSize, borderInset)
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
                                        Icon(painter = painterResource(id = R.drawable.ic_chevron_right_12),
                                            "chevron",
                                            modifier
                                                .clickable { expandedState = !expandedState }
                                                .rotate(rotationState), tint = chevronTint)
                                    }
                                    Text(
                                        text = title,
                                        fontSize = textSize.fontSize.size,
                                        fontWeight = textSize.weight,
                                        color = textColor.rest,
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
                                        color = actionTextColor.rest,
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
            modifier: Modifier = Modifier,
            description: String,
            listItemTokens: ListItemTokens? = null,
            actionText: Boolean = false,
            descriptionPlacement: TextPlacement = Top,
            border: BorderType = NoBorder,
            borderInset: BorderInset = None,
            onClick: (() -> Unit)? = null,
            onActionClick: (() -> Unit)? = null,
            interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
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
                    getListItemTokens().textColor(textType = ListTextType.DescriptionText)
                val actionTextColor =
                    getListItemTokens().textColor(textType = ListTextType.ActionText)
                val horizontalPadding = getListItemTokens().padding(Medium)
                val borderSize = getListItemTokens().borderSize().value
                val borderInset =
                    with(LocalDensity.current) {
                        getListItemTokens().borderInset(inset = borderInset).toPx()
                    }
                val borderColor = getColorByState(
                    stateData = getListItemTokens().borderColor(),
                    enabled = true,
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
                            onClick = onClick ?: {}), verticalAlignment = descriptionAlignment
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
                        if (actionText) {
                            descriptionText(
                                description = description,
                                text = " Action",
                                onClick = onActionClick ?: {},
                                descriptionTextColor = textColor.rest,
                                actionTextColor = actionTextColor.rest,
                                descriptionTextSize = textSize,
                                actionTextSize = actionTextSize
                            )
                        } else {
                            Text(
                                text = description,
                                color = textColor.rest,
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
}


