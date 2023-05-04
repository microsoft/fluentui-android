package com.microsoft.fluentui.tokenized.controls

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.StateColor
import com.microsoft.fluentui.theme.token.controlTokens.*

@Composable
fun AnnouncementCard(
    title: String,
    description: String,
    buttonText: String,
    buttonOnClick: () -> Unit,
    modifier: Modifier = Modifier,
    previewImageVector: ImageVector? = null,
    @DrawableRes previewImageDrawable: Int? = null,
    announcementCardTokens: AnnouncementCardTokens? = null
) {
    val token = announcementCardTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.AnnouncementCard] as AnnouncementCardTokens
    val announcementCardInfo = AnnouncementCardInfo()
    val textColor = token.textColor(announcementCardInfo = announcementCardInfo)
    val titleColor = token.titleColor(announcementCardInfo = announcementCardInfo)
    val descriptionTypography =
        token.descriptionTypography(announcementCardInfo = announcementCardInfo)
    val titleTypography = token.titleTypography(announcementCardInfo = announcementCardInfo)
    val previewTitlePadding = token.previewTitlePadding(announcementCardInfo = announcementCardInfo)
    val titleTextPadding = token.titleTextPadding(announcementCardInfo = announcementCardInfo)
    val textButtonPadding = token.textButtonPadding(announcementCardInfo = announcementCardInfo)
    val textHorizontalPadding =
        token.textHorizontalPadding(announcementCardInfo = announcementCardInfo)
    val previewPadding = token.previewPadding(announcementCardInfo = announcementCardInfo)
    val previewCornerRadius = token.previewCornerRadius(announcementCardInfo = announcementCardInfo)

    class CustomBasicCardTokens : BasicCardTokens() {
        @Composable
        override fun cornerRadius(announcementCardInfo: BasicCardControlInfo): Dp {
            return token.cornerRadius(announcementCardInfo = announcementCardInfo)
        }

        @Composable
        override fun elevation(announcementCardInfo: BasicCardControlInfo): Dp {
            return token.elevation(announcementCardInfo = announcementCardInfo)
        }
    }

    class CustomButtonTokens : ButtonTokens() {
        @Composable
        override fun textColor(buttonInfo: ButtonInfo): StateColor {
            return token.buttonTextColor(announcementCardInfo = announcementCardInfo)
        }
    }
    BasicCard(
        modifier = modifier,
        basicCardTokens = CustomBasicCardTokens() as BasicCardTokens
    ) {
        Box(
            modifier = Modifier
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
                            style = titleTypography.merge(TextStyle(color = titleColor))
                                .merge(TextStyle(textAlign = TextAlign.Center))
                        )
                        BasicText(
                            text = description,
                            style = descriptionTypography.merge(TextStyle(color = textColor))
                                .merge(TextStyle(textAlign = TextAlign.Center))
                        )
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        text = buttonText,
                        onClick = buttonOnClick,
                        style = ButtonStyle.TextButton,
                        size = ButtonSize.Large,
                        buttonTokens = CustomButtonTokens()
                    )
                }
            }
        }
    }
}