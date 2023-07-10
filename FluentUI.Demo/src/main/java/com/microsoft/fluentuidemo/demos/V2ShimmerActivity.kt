package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.controlTokens.ColorStyle
import com.microsoft.fluentui.theme.token.controlTokens.ShimmerShape
import com.microsoft.fluentui.tokenized.controls.Label
import com.microsoft.fluentui.tokenized.shimmer.Shimmer
import com.microsoft.fluentuidemo.V2DemoActivity

class V2ShimmerActivity : V2DemoActivity() {
    override var demoActivityLink =
        "https://github.com/microsoft/fluentui-android/blob/master/FluentUI.Demo/src/main/java/com/microsoft/fluentuidemo/demos/V2ShimmerActivity.kt"

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
        }

    }
}