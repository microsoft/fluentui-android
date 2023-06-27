package com.microsoft.fluentuidemo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.core.app.NavUtils.navigateUpTo
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.theme.token.ExcelAliasTokens
import com.example.theme.token.M365AliasTokens
import com.example.theme.token.OneNoteAliasTokens
import com.example.theme.token.PowerPointAliasTokens
import com.example.theme.token.WordAliasTokens
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.IAliasTokens
import com.microsoft.fluentui.theme.token.Icon

object AppTheme : ViewModel() {
     private var themeStyle: MutableLiveData<FluentStyle> = MutableLiveData(FluentStyle.Neutral)
     private var selectedThemeIndex_: MutableLiveData<Int> = MutableLiveData(0)
     fun updateThemeStyle (overrideThemeStyle: FluentStyle) {
          themeStyle.value = overrideThemeStyle
     }
     @Composable
     fun observeThemeStyle(initial: FluentStyle): State<FluentStyle> {
          return this.themeStyle.observeAsState(initial)
     }

     @Composable
     fun SetAppTheme () {
          val painter = painterResource(id = R.drawable.ic_fluent_circle_32_filled)
          val selectedThemeIndex by selectedThemeIndex_.observeAsState()
          val painterIfSelected = painterResource(id = R.drawable.ic_fluent_checkmark_circle_32_filled)
          val themesList = arrayOf (
               Pair (AliasTokens(), "Fluent Brand"),
               Pair (OneNoteAliasTokens(), "One Note"),
               Pair (WordAliasTokens(), "Word"),
               Pair (ExcelAliasTokens(), "Excel"),
               Pair (PowerPointAliasTokens(), "PowerPoint"),
               Pair (M365AliasTokens(), "M365")
          )

          themesList.forEachIndexed { index, theme ->
               Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                         FluentGlobalTokens.size(
                              FluentGlobalTokens.SizeTokens.Size100))
               ) {
                    Icon (
                         painter = if(selectedThemeIndex == index) painterIfSelected else painter,
                         contentDescription = "Theme Icon",
                         tint = theme.first.brandColor[FluentAliasTokens.BrandColorTokens.Color80],
                         modifier = Modifier
                              .clickable {
                                   FluentTheme.updateAliasTokens(theme.first)
                                   selectedThemeIndex_.value = index
                              }
                    )
                    val themeName = theme.second
                    BasicText(
                         text = themeName,
                         modifier = Modifier
                              .clickable {
                                   FluentTheme.updateAliasTokens(theme.first)
                                   selectedThemeIndex_.value = index
                              },
                         style = TextStyle(
                              color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(themeMode = ThemeMode.Auto)
                         )
                    )
               }
          }
     }
}

object Navigation {
     var navigationStack = ArrayDeque<Class<ComponentActivity>> ()
//     fun forwardNavigation (intent: Intent, demoId: String, demoTitle: String) {
//          intent.putExtra(DemoActivity.DEMO_ID, demoId)
//          intent.putExtra(V2DemoActivity.DEMO_TITLE, demoTitle)
//          navigationStack.add(intent)
//     }

     fun backNavigation (context: Context) {
          Log.e("Error", navigationStack.toString())
          navigateUpTo(context as Activity, Intent(context, navigationStack.removeLast()))
     }
}