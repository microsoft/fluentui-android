package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.SearchBarIcons
import com.microsoft.fluentui.icons.searchbaricons.Office
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.theme.token.controlTokens.BorderType
import com.microsoft.fluentui.tokenized.SearchBar
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.listitem.ChevronOrientation
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.persona.Persona
import com.microsoft.fluentui.tokenized.persona.PersonaList
import com.microsoft.fluentuidemo.CustomizedSearchBarTokens
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity
import com.microsoft.fluentuidemo.util.DemoAppStrings
import com.microsoft.fluentuidemo.util.getDemoAppString
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class V2SearchBarActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-29"
    override val controlTokensUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-27"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context: Context = this
        setActivityContent {
            var autoCorrectEnabled: Boolean by rememberSaveable { mutableStateOf(false) }
            var enableMicrophoneCallback: Boolean by rememberSaveable { mutableStateOf(true) }
            var searchBarStyle: FluentStyle by rememberSaveable { mutableStateOf(FluentStyle.Neutral) }
            var displayRightAccessory: Boolean by rememberSaveable { mutableStateOf(true) }
            var induceDelay: Boolean by rememberSaveable { mutableStateOf(false) }
            var selectedPeople: Person? by rememberSaveable { mutableStateOf(null) }
            var customizedSearchBar: Boolean by rememberSaveable { mutableStateOf(false) }

            val listofPeople = listOf(
                Person(
                    "Allan", "Munger",
                    image = R.drawable.avatar_allan_munger,
                    isActive = true
                ),
                Person(
                    "Amanda", "Brady",
                    isActive = false, status = AvatarStatus.Offline
                ),
                Person(
                    "Abhay", "Singh",
                    isActive = true, status = AvatarStatus.DND, isOOO = true
                ),
                Person(
                    "Carlos", "Slathery",
                    isActive = false, status = AvatarStatus.Busy, isOOO = true
                ),
                Person(
                    "Celeste", "Burton",
                    image = R.drawable.avatar_celeste_burton,
                    isActive = true, status = AvatarStatus.Away
                ),
                Person(
                    "Ankit", "Gupta",
                    isActive = true, status = AvatarStatus.Unknown
                ),
                Person(
                    "Miguel", "Garcia",
                    image = R.drawable.avatar_miguel_garcia,
                    isActive = true, status = AvatarStatus.Blocked
                )
            )
            var filteredPeople by rememberSaveable { mutableStateOf(listofPeople.toMutableList()) }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ListItem.SectionHeader(
                    title = getDemoAppString(DemoAppStrings.ModifiableParameters),
                    enableChevron = true,
                    enableContentOpenCloseTransition = true,
                    chevronOrientation = ChevronOrientation(90f, 0f),
                ) {
                    Column {
                        ListItem.Item(
                            text = getDemoAppString(DemoAppStrings.AutoCorrect),
                            subText = if (autoCorrectEnabled)
                                LocalContext.current.resources.getString(R.string.fluentui_enabled)
                            else
                                LocalContext.current.resources.getString(R.string.fluentui_disabled),
                            trailingAccessoryContent = {
                                ToggleSwitch(
                                    onValueChange = {
                                        autoCorrectEnabled = !autoCorrectEnabled
                                    },
                                    checkedState = autoCorrectEnabled
                                )
                            }
                        )
                        ListItem.Item(
                            text = getDemoAppString(DemoAppStrings.MicrophoneCallback),
                            subText = if (enableMicrophoneCallback)
                                LocalContext.current.resources.getString(R.string.fluentui_activated)
                            else
                                LocalContext.current.resources.getString(R.string.fluentui_deactivated),
                            trailingAccessoryContent = {
                                ToggleSwitch(
                                    onValueChange = {
                                        enableMicrophoneCallback = !enableMicrophoneCallback
                                    },
                                    checkedState = enableMicrophoneCallback
                                )
                            }
                        )
                        ListItem.Item(
                            text = getDemoAppString(DemoAppStrings.Style),
                            subText = if (searchBarStyle == FluentStyle.Neutral)
                                LocalContext.current.resources.getString(R.string.fluentui_neutral)
                            else
                                LocalContext.current.resources.getString(R.string.fluentui_brand),
                            trailingAccessoryContent = {
                                ToggleSwitch(
                                    onValueChange = {
                                        searchBarStyle =
                                            if (searchBarStyle == FluentStyle.Neutral)
                                                FluentStyle.Brand
                                            else
                                                FluentStyle.Neutral
                                    },
                                    checkedState = searchBarStyle == FluentStyle.Brand
                                )
                            }
                        )
                        ListItem.Item(
                            text = getDemoAppString(DemoAppStrings.RightAccessoryView),
                            subText = if (displayRightAccessory)
                                LocalContext.current.resources.getString(R.string.fluentui_enabled)
                            else
                                LocalContext.current.resources.getString(R.string.fluentui_disabled),
                            trailingAccessoryContent = {
                                ToggleSwitch(
                                    onValueChange = {
                                        displayRightAccessory = !displayRightAccessory
                                    },
                                    checkedState = displayRightAccessory
                                )
                            }
                        )

                        ListItem.Item(
                            text = "Induce Delay",
                            subText = if (induceDelay)
                                LocalContext.current.resources.getString(R.string.fluentui_enabled)
                            else
                                LocalContext.current.resources.getString(R.string.fluentui_disabled),
                            trailingAccessoryContent = {
                                ToggleSwitch(
                                    onValueChange = {
                                        induceDelay = it
                                    },
                                    checkedState = induceDelay
                                )
                            }
                        )

                        ListItem.Item(
                            text = "Customized Search Bar",
                            subText = if (customizedSearchBar)
                                LocalContext.current.resources.getString(R.string.fluentui_enabled)
                            else
                                LocalContext.current.resources.getString(R.string.fluentui_disabled),
                            trailingAccessoryContent = {
                                ToggleSwitch(
                                    onValueChange = {
                                        customizedSearchBar = it
                                    },
                                    checkedState = customizedSearchBar
                                )
                            }
                        )
                    }
                }

                val microphonePressedString = getDemoAppString(DemoAppStrings.MicrophonePressed)
                val rightViewPressedString = getDemoAppString(DemoAppStrings.RightViewPressed)
                val keyboardSearchPressedString =
                    getDemoAppString(DemoAppStrings.KeyboardSearchPressed)

                val scope = rememberCoroutineScope()
                var loading by rememberSaveable { mutableStateOf(false) }
                val keyboardController = LocalSoftwareKeyboardController.current
                val showCustomizedAppBar = searchBarStyle == FluentStyle.Neutral && customizedSearchBar

                SearchBar(
                    onValueChange = { query, selectedPerson ->
                        scope.launch {
                            loading = true

                            if (induceDelay)
                                delay(2000)

                            filteredPeople = listofPeople.filter {
                                it.firstName.lowercase().contains(query.lowercase()) ||
                                        it.lastName.lowercase().contains(query.lowercase())
                            } as MutableList<Person>
                            selectedPeople = selectedPerson

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

                val filteredPersona = mutableListOf<Persona>()
                filteredPeople.forEach {
                    filteredPersona.add(
                        Persona(
                            it,
                            "${it.firstName} ${it.lastName}",
                            subTitle = it.email,
                            onClick = { selectedPeople = it }
                        )
                    )
                }
                PersonaList(
                    personas = filteredPersona,
                    border = BorderType.Bottom
                )
            }
        }
    }
}