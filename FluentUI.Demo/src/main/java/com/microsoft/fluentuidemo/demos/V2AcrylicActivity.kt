package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.microsoft.fluentui.compose.AcrylicPane
import com.microsoft.fluentui.icons.SearchBarIcons
import com.microsoft.fluentui.icons.searchbaricons.Office
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize
import com.microsoft.fluentui.tokenized.SearchBar
import com.microsoft.fluentui.tokenized.controls.RadioButton
import com.microsoft.fluentui.tokenized.persona.Avatar
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentuidemo.CustomizedSearchBarTokens
import com.microsoft.fluentuidemo.util.DemoAppStrings
import com.microsoft.fluentuidemo.util.getDemoAppString
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class V2AcrylicPaneActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-18" //TODO: Update this URL
    override val controlTokensUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-18"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityContent {
            CreateAcrylicPaneActivityUI(this)
        }
    }

    @Composable
    private fun CreateAcrylicPaneActivityUI(context: Context) {
        AcrylicPane(component = { frozenBackground(context) }) {
            scrollableBackgroundTest()
        }
    }
}


@Composable
fun scrollableBackgroundTest(){
    val person: Person = Person(
        "Kat", "Larsson",
        isActive = true,
        image = R.drawable.avatar_kat_larsson
    )
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(20) {
            ListItem.Item(
                text = "Item $it",
                subText = "This is a list item",
                leadingAccessoryContent = { Avatar(
                    person,
                    cutoutContentDescription = "heart",
                    size = AvatarSize.Size40,
                    enableActivityRings = true,
                    cutoutIconDrawable = R.drawable.cutout_heart16x16
                ) },
                trailingAccessoryContent = {
                    RadioButton(enabled = true, onClick = {}, selected = false)
                }
            )
        }
    }
}

@Composable
fun frozenBackground(context: Context){
    val scope = rememberCoroutineScope()

    val microphonePressedString = getDemoAppString(DemoAppStrings.MicrophonePressed)
    val rightViewPressedString = getDemoAppString(DemoAppStrings.RightViewPressed)
    val keyboardSearchPressedString = getDemoAppString(DemoAppStrings.KeyboardSearchPressed)
    var loading by rememberSaveable { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    var autoCorrectEnabled: Boolean by rememberSaveable { mutableStateOf(false) }
    var enableMicrophoneCallback: Boolean by rememberSaveable { mutableStateOf(true) }
    var searchBarStyle: FluentStyle by rememberSaveable { mutableStateOf(FluentStyle.Neutral) }
    var displayRightAccessory: Boolean by rememberSaveable { mutableStateOf(true) }
    var selectedPeople: Person? by rememberSaveable { mutableStateOf(null) }
    val showCustomizedAppBar = true

    SearchBar(
        onValueChange = { query, selectedPerson ->
            scope.launch {
                loading = true
                delay(2000)
                loading = false
            }
        },
        style = searchBarStyle,
        loading = loading,
        selectedPerson = selectedPeople,
        microphoneCallback = if (enableMicrophoneCallback) {
            {
                Toast.makeText(context, microphonePressedString, Toast.LENGTH_SHORT)
                    .show()
            }
        } else null,
        keyboardOptions = KeyboardOptions(
            autoCorrect = autoCorrectEnabled,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                Toast.makeText(
                    context,
                    keyboardSearchPressedString,
                    Toast.LENGTH_SHORT
                )
                    .show()
                keyboardController?.hide()
            }
        ),
        rightAccessoryIcon = if (displayRightAccessory) {
            FluentIcon(
                SearchBarIcons.Office,
                contentDescription = "Office",
                onClick = {
                    Toast.makeText(
                        context,
                        rightViewPressedString,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            )
        } else null,
        searchBarTokens = if (showCustomizedAppBar) {
            CustomizedSearchBarTokens
        } else null,
        modifier = if (showCustomizedAppBar) Modifier.requiredHeight(60.dp) else Modifier
    )
}
