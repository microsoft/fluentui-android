package com.microsoft.fluentuidemo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.SolidColor
import androidx.core.app.NavUtils.navigateUpTo
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.theme.token.ExcelAliasTokens
import com.example.theme.token.M365AliasTokens
import com.example.theme.token.OneNoteAliasTokens
import com.example.theme.token.PowerPointAliasTokens
import com.example.theme.token.WordAliasTokens
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.StateBrush
import com.microsoft.fluentui.theme.token.controlTokens.RadioButtonInfo
import com.microsoft.fluentui.theme.token.controlTokens.RadioButtonTokens
import com.microsoft.fluentui.tokenized.controls.RadioButton
import com.microsoft.fluentui.tokenized.listitem.ListItem

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
          val selectedThemeIndex by selectedThemeIndex_.observeAsState()
          val themesList = arrayOf (
               Pair (AliasTokens(), "Fluent Brand"),
               Pair (OneNoteAliasTokens(), "One Note"),
               Pair (WordAliasTokens(), "Word"),
               Pair (ExcelAliasTokens(), "Excel"),
               Pair (PowerPointAliasTokens(), "PowerPoint"),
               Pair (M365AliasTokens(), "M365")
          )

          themesList.forEachIndexed { index, theme ->
               ListItem.Item(
                    onClick = {
                         FluentTheme.updateAliasTokens(theme.first)
                         selectedThemeIndex_.value = index
                    },
                    text = theme.second,
                    leadingAccessoryContent = {
                         RadioButton(
                              selected = selectedThemeIndex == index,
                              onClick = {
                                   FluentTheme.updateAliasTokens(theme.first)
                                   selectedThemeIndex_.value = index
                              },
                              radioButtonToken = object : RadioButtonTokens() {
                                   @Composable
                                   override fun backgroundBrush(radioButtonInfo: RadioButtonInfo): StateBrush {
                                        return StateBrush(
                                             rest = SolidColor(theme.first.brandColor[FluentAliasTokens.BrandColorTokens.Color80]),
                                             selected = SolidColor(theme.first.brandColor[FluentAliasTokens.BrandColorTokens.Color80])
                                        )
                                   }
                              }
                         )
                    }
               )
          }
     }
}

object Navigation {
     var navigationStack = ArrayDeque<Class<ComponentActivity>> ()
     fun forwardNavigation (currentActivity: ComponentActivity, targetActivity: Class<out androidx.core.app.ComponentActivity>, vararg extraData: Pair<String,  java.io.Serializable>) {
          val intent = Intent(currentActivity, targetActivity)
          extraData.forEach {
               intent.putExtra(it.first, it.second)
          }
          navigationStack.add(currentActivity.javaClass)
          currentActivity.startActivity(intent)
     }
     fun backNavigation (context: Context) {
          Log.e("Error", navigationStack.toString())
          navigateUpTo(context as Activity, Intent(context, navigationStack.removeLast()))
     }
}