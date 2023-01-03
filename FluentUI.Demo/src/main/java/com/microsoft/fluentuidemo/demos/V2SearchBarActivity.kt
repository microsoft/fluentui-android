package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.compose.Strings
import com.microsoft.fluentui.compose.getString
import com.microsoft.fluentui.icons.SearchBarIcons
import com.microsoft.fluentui.icons.searchbaricons.Office
import com.microsoft.fluentui.theme.FluentTheme
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
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.util.DemoAppStrings
import com.microsoft.fluentuidemo.util.getDemoAppString

class V2SearchBarActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_compose
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context: Context = this
        val compose_here = findViewById<ComposeView>(R.id.compose_here)

        compose_here.setContent {
            FluentTheme {
                var autoCorrectEnabled: Boolean by rememberSaveable { mutableStateOf(false) }
                var enableMicrophoneCallback: Boolean by rememberSaveable { mutableStateOf(true) }
                var searchBarStyle: FluentStyle by rememberSaveable { mutableStateOf(FluentStyle.Neutral) }
                var displayRightAccessory: Boolean by rememberSaveable { mutableStateOf(true) }

                var selectedPeople: Person? by rememberSaveable { mutableStateOf(null) }

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
                    modifier = Modifier
                        .padding(16.dp),
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
                                    getString(Strings.Enabled)
                                else
                                    getString(Strings.Disabled),
                                trailingAccessoryView = {
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
                                    getString(Strings.Activated)
                                else
                                    getString(Strings.DeActivated),
                                trailingAccessoryView = {
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
                                    getString(Strings.Neutral)
                                else
                                    getString(Strings.Brand),
                                trailingAccessoryView = {
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
                                    getString(Strings.Enabled)
                                else
                                    getString(Strings.Disabled),
                                trailingAccessoryView = {
                                    ToggleSwitch(
                                        onValueChange = {
                                            displayRightAccessory = !displayRightAccessory
                                        },
                                        checkedState = displayRightAccessory
                                    )
                                }
                            )
                        }
                    }

                    val microphonePressedString = getDemoAppString(DemoAppStrings.MicrophonePressed)
                    val rightViewPressedString = getDemoAppString(DemoAppStrings.RightViewPressed)
                    val keyboardSearchPressedString = getDemoAppString(DemoAppStrings.KeyboardSearchPressed)
                    val keyboardController = LocalSoftwareKeyboardController.current
                    SearchBar(
                        onValueChange = { query, selectedPerson ->
                            filteredPeople = listofPeople.filter {
                                it.firstName.lowercase().contains(query.lowercase()) ||
                                        it.lastName.lowercase().contains(query.lowercase())
                            } as MutableList<Person>
                            selectedPeople = selectedPerson
                        },
                        style = searchBarStyle,
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
                                Toast.makeText(context, keyboardSearchPressedString, Toast.LENGTH_SHORT)
                                    .show()
                                keyboardController?.hide()
                            }
                        ),
                        rightAccessoryIcon = if (displayRightAccessory) {
                            SearchBarIcons.Office
                        } else null,
                        rightAccessoryIconDescription = "Office",
                        rightAccessoryViewOnClick = {
                            Toast.makeText(context, rightViewPressedString, Toast.LENGTH_SHORT)
                                .show()
                        }
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
}