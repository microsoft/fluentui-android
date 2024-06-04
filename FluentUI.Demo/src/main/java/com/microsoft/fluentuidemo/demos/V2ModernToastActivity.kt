package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.tokenized.controls.BorderlessBasicCard
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class V2ModernToastActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-29"
    override val controlTokensUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-27"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityContent {
            CreateModernToastUI()
        }
    }

    @Composable
    fun CreateModernToastUI() {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ModernToast()
        }
    }

    @Composable
    fun ModernToast() {
        var isVisible by remember {
            mutableStateOf(true)
        }
        val coroutineScope = rememberCoroutineScope()
        Column {
            AnimatedVisibility(
                visible = isVisible,
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn()
            ) {
                BorderlessBasicCard(
                    modifier = Modifier
                        .padding(8.dp)
                        .shadow(6.dp, RoundedCornerShape(14.dp))
                        .clip(RoundedCornerShape(14.dp))
                        .clickable(onClick = {

                        })
                ) {
                    Row(
                        modifier = Modifier
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Image(
                            painterResource(id = R.drawable.diamond),
                            contentDescription = "diamond",
                            Modifier
                                .size(36.dp)
                                .padding(6.dp)
                        )
                        Text(
                            text = "Activate Microsoft 365 to create and edit documents – ",
                            color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(),
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Try a month free",
                            color = Color(0xFF185ABD),
                            fontSize = 16.sp
                        )
                        IconButton(
                            onClick = {
                                isVisible = false
                            }
                        ) {
                            Icon(
                                painterResource(id = R.drawable.dismiss),
                                contentDescription = "dismiss",
                                Modifier
                                    .size(38.dp)
                                    .padding(12.dp)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(64.dp))
            Row {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            isVisible = false
                            delay(250)
                            isVisible = true
                        }
                    },
                    modifier = Modifier.width(120.dp)
                ) {
                    if (isVisible) {
                        Text(text = "Animate")
                    } else {
                        Text(text = "Show")
                    }
                }
                Spacer(modifier = Modifier.width(64.dp))
                Button(
                    onClick = {
                        coroutineScope.launch {
                            isVisible = false
                        }
                    },
                    modifier = Modifier.width(120.dp)
                ) {
                    Text(text = "Dismiss")
                }
            }
        }
    }
}