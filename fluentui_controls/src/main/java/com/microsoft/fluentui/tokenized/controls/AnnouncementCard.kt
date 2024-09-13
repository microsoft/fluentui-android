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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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

/**
 * Cards are flexible containers that group related content and actions together. They reveal more information upon interaction.
 * An Announcement card is made of a preview image on the top and some text and a button below. The card is non-interactive except the button.
 *
 * @param title Primary text of the Card
 * @param description Secondary text of the card, usually an announcement
 * @param buttonText Text for the button
 * @param buttonStyle Optional button styling. Default [ButtonStyle.TextButton]
 * @param buttonOnClick OnClick for the button
 * @param modifier Modifier for the card
 * @param previewImageVector Optional previewImage for the card
 * @param previewImageDrawable Optional previewImage for the card
 * @param announcementCardTokens Optional tokens for customizing the card
 */
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
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = announcementCardTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.AnnouncementCardControlType] as AnnouncementCardTokens
    val announcementCardInfo = AnnouncementCardInfo()
    val textColor = token.textColor(announcementCardInfo = announcementCardInfo)
    val titleColor = token.titleColor(announcementCardInfo = announcementCardInfo)
    val descriptionTypography =
        token.descriptionTypography(announcementCardInfo = announcementCardInfo)
    val titleTypography = token.titleTypography(announcementCardInfo = announcementCardInfo)
    val previewTextSpacing = token.previewTextSPacing(announcementCardInfo = announcementCardInfo)
    val titleTextSpacing = token.titleTextSpacing(announcementCardInfo = announcementCardInfo)
    val textButtonSpacing = token.textButtonSpacing(announcementCardInfo = announcementCardInfo)
    val textHorizontalPadding =
        token.textHorizontalPadding(announcementCardInfo = announcementCardInfo)
    val cardPadding = token.cardPadding(announcementCardInfo = announcementCardInfo)
    val previewCornerRadius = token.previewCornerRadius(announcementCardInfo = announcementCardInfo)

    class CustomBasicCardTokens : BasicCardTokens() {

        @Composable
        override fun backgroundBrush(basicCardInfo: BasicCardInfo): Brush {
            return token.backgroundBrush(announcementCardInfo = announcementCardInfo)
        }

        @Composable
        override fun cornerRadius(basicCardInfo: BasicCardInfo): Dp {
            return token.cornerRadius(announcementCardInfo = announcementCardInfo)
        }

        @Composable
        override fun elevation(basicCardInfo: BasicCardInfo): Dp {
            return token.elevation(announcementCardInfo = announcementCardInfo)
        }

        @Composable
        override fun borderColor(basicCardInfo: BasicCardInfo): Brush {
            return token.borderColor(announcementCardInfo = announcementCardInfo)
        }

        @Composable
        override fun borderStrokeWidth(basicCardInfo: BasicCardInfo): Dp {
            return token.borderStrokeWidth(announcementCardInfo = announcementCardInfo)
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
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .padding(cardPadding)
                .semantics(mergeDescendants = true) {},
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(previewCornerRadius))
            ) {
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
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(previewTextSpacing)
            )
            BasicText(
                modifier = Modifier.padding(textHorizontalPadding),
                text = title,
                style = titleTypography.merge(TextStyle(color = titleColor))
                    .merge(TextStyle(textAlign = TextAlign.Center))
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(titleTextSpacing)
            )
            BasicText(
                modifier = Modifier.padding(textHorizontalPadding),
                text = description,
                style = descriptionTypography.merge(TextStyle(color = textColor))
                    .merge(TextStyle(textAlign = TextAlign.Center))
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(textButtonSpacing)
            )
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