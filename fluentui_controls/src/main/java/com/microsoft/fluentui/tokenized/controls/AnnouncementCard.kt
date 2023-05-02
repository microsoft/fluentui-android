package com.microsoft.fluentui.tokenized.controls

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.StateColor
import com.microsoft.fluentui.theme.token.controlTokens.*

@Composable
fun AnnouncementCard(
    title: String,
    text: String,
    buttonText: String,
    buttonOnClick: () -> Unit,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    previewImageVector: ImageVector? = null,
    @DrawableRes previewImageDrawable: Int? = null,
    announcementCardTokens: AnnouncementCardTokens? = null
) {
    val token = announcementCardTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.AnnouncementCard] as AnnouncementCardTokens
    val announcementCardInfo = AnnouncementCardInfo()
    val textColor = token.textColor(announcementCardInfo = announcementCardInfo)
    val titleColor = token.titleColor(announcementCardInfo = announcementCardInfo)
    val buttonColor = token.buttonColor(announcementCardInfo = announcementCardInfo)
    val textSize = token.textSize(announcementCardInfo = announcementCardInfo)
    val titleSize = token.titleSize(announcementCardInfo = announcementCardInfo)
    val previewTitlePadding = token.previewTitlePadding(announcementCardInfo = announcementCardInfo)
    val titleTextPadding = token.titleTextPadding(announcementCardInfo = announcementCardInfo)
    val textButtonPadding = token.textButtonPadding(announcementCardInfo = announcementCardInfo)
    val textHorizontalPadding =
        token.textHorizontalPadding(announcementCardInfo = announcementCardInfo)
    val previewPadding = token.previewPadding(announcementCardInfo = announcementCardInfo)
    val previewCornerRadius = token.previewCornerRadius(announcementCardInfo = announcementCardInfo)
    class CustomBasicCardTokens: BasicCardTokens(){
        @Composable
        override fun cornerRadius(announcementCardInfo: BasicCardControlInfo): Dp {
            return token.cornerRadius(announcementCardInfo = announcementCardInfo)
        }
        @Composable
        override fun elevation(announcementCardInfo: BasicCardControlInfo): Dp {
            return token.elevation(announcementCardInfo = announcementCardInfo)
        }
    }
    class CustomButtonTokens: ButtonTokens(){
        @Composable
        override fun textColor(buttonInfo: ButtonInfo): StateColor{
            return token.buttonTextColor(announcementCardInfo = announcementCardInfo)
        }
    }
    BasicCard(
        modifier = modifier,
        onClick = onClick,
        basicCardTokens = CustomBasicCardTokens() as BasicCardTokens
    ) {
        Box(
            modifier = modifier
                .padding(all = previewPadding)
                .semantics(mergeDescendants = true) {},
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.width(IntrinsicSize.Min),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(previewTitlePadding)
            ) {
                Box(modifier = Modifier.clip(RoundedCornerShape(previewCornerRadius))) {
                    if (previewImageDrawable != null) {
                        Image(
                            painterResource(id = previewImageDrawable), contentDescription = ""
                        )
                    } else if (previewImageVector != null) {
                        Image(
                            imageVector = previewImageVector,
                            contentDescription = ""
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(textButtonPadding)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = textHorizontalPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(titleTextPadding)
                    ) {
                        BasicText(
                            text = title,
                            style = titleSize.merge(TextStyle(color = titleColor))
                                .merge(TextStyle(textAlign = TextAlign.Center))
                        )
                        BasicText(
                            text = text,
                            style = textSize.merge(TextStyle(color = textColor))
                                .merge(TextStyle(textAlign = TextAlign.Center))
                        )
                    }
                    Button(modifier = Modifier.fillMaxWidth(), text = buttonText, onClick = buttonOnClick, style = ButtonStyle.TextButton, size = ButtonSize.Large, buttonTokens = CustomButtonTokens())
                }
            }
        }
    }
}