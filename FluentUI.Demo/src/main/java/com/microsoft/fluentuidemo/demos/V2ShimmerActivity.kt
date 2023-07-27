package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.theme.token.controlTokens.BadgeInfo
import com.microsoft.fluentui.theme.token.controlTokens.BadgeTokens
import com.microsoft.fluentui.theme.token.controlTokens.ColorStyle
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipSize
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipTokens
import com.microsoft.fluentui.theme.token.controlTokens.ShimmerInfo
import com.microsoft.fluentui.theme.token.controlTokens.ShimmerShape
import com.microsoft.fluentui.theme.token.controlTokens.ShimmerTokens
import com.microsoft.fluentui.tokenized.controls.Label
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

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-31"
    override val controlTokensUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-31"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityContent {
            CreateShimmerActivityUI()
        }
    }

    @Composable
    private fun CreateShimmerActivityUI() {
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
                Shimmer(modifier = Modifier.size(120.dp, 80.dp))
                Column(
                    Modifier
                        .height(80.dp)
                        .padding(top = 10.dp, bottom = 10.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Shimmer(modifier = Modifier.size(140.dp, 12.dp))
                    Shimmer(modifier = Modifier.size(180.dp, 12.dp))
                    Shimmer(modifier = Modifier.size(200.dp, 12.dp))
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
                Shimmer(modifier = Modifier.size(60.dp, 60.dp), shape = ShimmerShape.Circle)
                Column(
                    Modifier
                        .height(80.dp)
                        .padding(top = 10.dp, bottom = 10.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Shimmer(modifier = Modifier.size(140.dp, 12.dp))
                    Shimmer(modifier = Modifier.size(180.dp, 12.dp))
                }
            }
            Row(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 16.dp)
                    .height(60.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Shimmer(modifier = Modifier.size(60.dp, 60.dp), shape = ShimmerShape.Circle)
                Column(
                    Modifier
                        .height(80.dp)
                        .padding(top = 10.dp, bottom = 10.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Shimmer(modifier = Modifier.size(140.dp, 12.dp))
                    Shimmer(modifier = Modifier.size(180.dp, 12.dp))
                }
            }
            Row(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 16.dp)
                    .height(60.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Shimmer(modifier = Modifier.size(60.dp), shape = ShimmerShape.Circle)
                Column(
                    Modifier
                        .height(80.dp)
                        .padding(top = 10.dp, bottom = 10.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Shimmer(modifier = Modifier.size(140.dp, 12.dp))
                    Shimmer(modifier = Modifier.size(180.dp, 12.dp))
                }
            }
            class TransparentToken: ShimmerTokens(){
                @Composable
                override fun color(shimmerInfo: ShimmerInfo): Color {
                    return Color.Transparent
                }
                @Composable
                override fun knockoutEffectColor(shimmerInfo: ShimmerInfo): Color {
                    return Color(0XFFE1BA27)
                }
                @Composable
                override fun cornerRadius(shimmerInfo: ShimmerInfo): Dp {
                    return 100.dp
                }
            }
            class BadgeColorToken: BadgeTokens(){
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
                Shimmer( content = {
                    Badge(text = "Badge", badgeTokens = BadgeColorToken())
                }, shimmerTokens = TransparentToken())
            }
            class TransparentChipToken: ShimmerTokens(){
                @Composable
                override fun color(shimmerInfo: ShimmerInfo): Color {
                    return Color.Transparent
                }
                @Composable
                override fun cornerRadius(shimmerInfo: ShimmerInfo): Dp {
                    return 2.dp
                }
            }
            Row(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Label(text = "PersonaChip", textStyle = FluentAliasTokens.TypographyTokens.Body1)
                Shimmer( content = {
                    PersonaChip(person = Person("PersonaChip"), selected = true, size = PersonaChipSize.Small, style = PersonaChipStyle.Brand)
                }, shimmerTokens = TransparentChipToken())
            }
            Row(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Label(text = "Avatar", textStyle = FluentAliasTokens.TypographyTokens.Body1)
                Shimmer( content = {
                    Avatar(person = Person(
                        "Allan", "Munger",
                        image = R.drawable.avatar_allan_munger,
                        status = AvatarStatus.Available,
                    ), size = AvatarSize.Size72, enableActivityRings = false)
                }, shimmerTokens = TransparentChipToken())
            }

        }

    }
}