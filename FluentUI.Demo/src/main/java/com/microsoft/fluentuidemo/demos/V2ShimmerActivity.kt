package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.theme.token.controlTokens.BadgeInfo
import com.microsoft.fluentui.theme.token.controlTokens.BadgeTokens
import com.microsoft.fluentui.theme.token.controlTokens.ColorStyle
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipSize
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle
import com.microsoft.fluentui.theme.token.controlTokens.ShimmerInfo
import com.microsoft.fluentui.theme.token.controlTokens.ShimmerOrientation
import com.microsoft.fluentui.theme.token.controlTokens.ShimmerTokens
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.Label
import com.microsoft.fluentui.tokenized.controls.RadioButton
import com.microsoft.fluentui.tokenized.notification.Badge
import com.microsoft.fluentui.tokenized.persona.Avatar
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.persona.PersonaChip
import com.microsoft.fluentui.tokenized.shimmer.Shimmer
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity

class V2ShimmerActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-34"
    override val controlTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-32"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityContent {
            CreateShimmerActivityUI()
        }
    }

    @Composable
    private fun CreateShimmerActivityUI() {
        var shimmerOrientation by rememberSaveable { mutableStateOf(0) }
        val shimmerTokens = object : ShimmerTokens() {
            @Composable
            override fun orientation(shimmerInfo: ShimmerInfo): ShimmerOrientation {
                return when (shimmerOrientation) {
                    0 -> ShimmerOrientation.LEFT_TO_RIGHT
                    1 -> ShimmerOrientation.RIGHT_TO_LEFT
                    2 -> ShimmerOrientation.TOPLEFT_TO_BOTTOMRIGHT
                    3 -> ShimmerOrientation.BOTTOMRIGHT_TO_TOPLEFT
                    else -> ShimmerOrientation.LEFT_TO_RIGHT
                }
            }
        }
        Column(
            Modifier
                .padding(all = 12.dp)
        ) {
            Label(
                text = "Box Shimmer",
                textStyle = FluentAliasTokens.TypographyTokens.Title2,
                colorStyle = ColorStyle.Brand
            )
            Row(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 16.dp)
                    .height(80.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Shimmer(modifier = Modifier.size(120.dp, 80.dp), shimmerTokens = shimmerTokens)
                Column(
                    Modifier
                        .height(80.dp)
                        .padding(top = 10.dp, bottom = 10.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Shimmer(modifier = Modifier.size(140.dp, 12.dp), shimmerTokens = shimmerTokens)
                    Shimmer(modifier = Modifier.size(180.dp, 12.dp), shimmerTokens = shimmerTokens)
                    Shimmer(modifier = Modifier.size(200.dp, 12.dp), shimmerTokens = shimmerTokens)
                }
            }
            Label(
                text = "Circle Shimmer",
                textStyle = FluentAliasTokens.TypographyTokens.Title2,
                colorStyle = ColorStyle.Brand
            )
            Row(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 16.dp)
                    .height(60.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Shimmer(modifier = Modifier.size(60.dp, 60.dp).clip(RoundedCornerShape(50.dp)), shimmerTokens = shimmerTokens)
                Column(
                    Modifier
                        .height(80.dp)
                        .padding(top = 10.dp, bottom = 10.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Shimmer(modifier = Modifier.size(180.dp, 12.dp), shimmerTokens = shimmerTokens)
                    Shimmer(modifier = Modifier.size(180.dp, 12.dp), shimmerTokens = shimmerTokens)
                }
            }
            Row(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 16.dp)
                    .height(60.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Shimmer(modifier = Modifier.size(60.dp, 60.dp).clip(RoundedCornerShape(50.dp)), shimmerTokens = shimmerTokens)
                Column(
                    Modifier
                        .height(80.dp)
                        .padding(top = 10.dp, bottom = 10.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Shimmer(modifier = Modifier.size(140.dp, 12.dp), shimmerTokens = shimmerTokens)
                    Shimmer(modifier = Modifier.size(180.dp, 12.dp), shimmerTokens = shimmerTokens)
                }
            }
            Row(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 16.dp)
                    .height(60.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Shimmer(modifier = Modifier.size(60.dp, 60.dp).clip(RoundedCornerShape(50.dp)), shimmerTokens = shimmerTokens)
                Column(
                    Modifier
                        .height(80.dp)
                        .padding(top = 10.dp, bottom = 10.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Shimmer(modifier = Modifier.size(140.dp, 12.dp), shimmerTokens = shimmerTokens)
                    Shimmer(modifier = Modifier.size(180.dp, 12.dp), shimmerTokens = shimmerTokens)
                }
            }
            class ShimmerGoldEffectToken : ShimmerTokens() {
                @Composable
                override fun knockoutEffectColor(shimmerInfo: ShimmerInfo): Color {
                    return Color(0XFFE1BA27)
                }

                @Composable
                override fun orientation(shimmerInfo: ShimmerInfo): ShimmerOrientation {
                    return when (shimmerOrientation) {
                        0 -> ShimmerOrientation.LEFT_TO_RIGHT
                        1 -> ShimmerOrientation.RIGHT_TO_LEFT
                        2 -> ShimmerOrientation.TOPLEFT_TO_BOTTOMRIGHT
                        3 -> ShimmerOrientation.BOTTOMRIGHT_TO_TOPLEFT
                        else -> ShimmerOrientation.LEFT_TO_RIGHT
                    }
                }
            }

            class BadgeColorToken : BadgeTokens() {
                @Composable
                override fun backgroundBrush(badgeInfo: BadgeInfo): Brush {
                    return SolidColor(Color(0xFFD59328))
                }

                @Composable
                override fun borderStroke(badgeInfo: BadgeInfo): BorderStroke {
                    return BorderStroke(
                        width = 0.dp,
                        brush = SolidColor(Color(0xFFD59328))
                    )
                }
            }
            Label(
                text = "Shimmer with Content",
                textStyle = FluentAliasTokens.TypographyTokens.Title2,
                colorStyle = ColorStyle.Brand
            )
            Row(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Label(text = "Badge", textStyle = FluentAliasTokens.TypographyTokens.Body1)
                Shimmer(content = {
                    Badge(text = "Badge", badgeTokens = BadgeColorToken())
                }, shimmerTokens = ShimmerGoldEffectToken() , cornerRadius = 100.dp)
            }
            Row(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Label(text = "PersonaChip", textStyle = FluentAliasTokens.TypographyTokens.Body1)
                Shimmer(cornerRadius = 2.dp, content = {
                    PersonaChip(
                        person = Person("PersonaChip"),
                        selected = true,
                        size = PersonaChipSize.Small,
                        style = PersonaChipStyle.Brand
                    )
                }, shimmerTokens = shimmerTokens )
            }
            Row(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Label(text = "Avatar", textStyle = FluentAliasTokens.TypographyTokens.Body1)
                Shimmer(cornerRadius = 50.dp, content = {
                    Avatar(
                        person = Person(
                            "Allan", "Munger",
                            image = R.drawable.avatar_allan_munger,
                            status = AvatarStatus.Available,
                        ), size = AvatarSize.Size72, enableActivityRings = false
                    )
                }, shimmerTokens = shimmerTokens)
            }
            Label(
                text = "Change Orientation",
                textStyle = FluentAliasTokens.TypographyTokens.Title2,
                colorStyle = ColorStyle.Brand
            )
            Spacer( modifier = Modifier.height(10.dp))
            SelectionRow(
                text = "Left to Right",
                testTag = "Left to Right",
                selected = shimmerOrientation == 0,
                onClick = { shimmerOrientation = 0 }
            )
            SelectionRow(
                text = "Right to Left",
                testTag = "Right to Left",
                selected = shimmerOrientation == 1,
                onClick = { shimmerOrientation = 1 }
            )
            SelectionRow(
                text = "Top Left to Bottom Right",
                testTag = "Top Left to Bottom Right",
                selected = shimmerOrientation == 2,
                onClick = { shimmerOrientation = 2 }
            )
            SelectionRow(
                text = "Bottom Right to Top Left",
                testTag = "Bottom Right to Top Left",
                selected = shimmerOrientation == 3,
                onClick = { shimmerOrientation = 3 }
            )
        }

    }
    @Composable
    private fun SelectionRow(
        text: String,
        testTag: String,
        selected: Boolean,
        onClick: () -> Unit
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            BasicText(
                text = text,
                modifier = Modifier.weight(1F),
                style = TextStyle(
                    color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground3].value(
                        themeMode = ThemeMode.Auto
                    )
                )
            )
            RadioButton(
                modifier = Modifier.testTag(testTag),
                selected = selected,
                onClick = onClick
            )
        }
    }
}