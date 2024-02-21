package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus.Available
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipSize
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle.*
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.persona.PersonaChip
import com.microsoft.fluentui.tokenized.persona.SearchBarPersonaChip
import com.microsoft.fluentuidemo.R.drawable
import com.microsoft.fluentuidemo.V2DemoActivity

// Tags used for testing
const val PERSONA_CHIP_SMALL_CHIP = "persona chip small"
const val PERSONA_CHIP_MEDIUM_CHIP = "persona chip medium"
const val PERSONA_CHIP_ANONYMOUS = "persona chip ann"
const val PERSONA_CHIP_DISABLED = "persona chip disabled"
const val PERSONA_CHIP_SWITCH = "persona chip switch"

class V2PersonaChipActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-24"
    override val controlTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-23"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityContent {
            createPersonaChipActivityUI()
        }
    }

    private fun createPersonWithName(): Person {
        return Person(
            "Allan",
            "Munger",
            image = drawable.avatar_allan_munger,
            email = "allan.munger@microsoft.com",
            isActive = true,
            status = Available,
            isOOO = false
        )
    }

    private fun createPersonWithEmail(): Person {
        return Person(
            "",
            "",
            image = drawable.avatar_allan_munger,
            email = "allan.munger@microsoft.com",
            isActive = true,
            status = Available,
            isOOO = false
        )
    }

    private fun createPersonWithNothing(): Person {
        return Person(
            "",
            "",
            image = drawable.avatar_allan_munger,
            email = "",
            isActive = true,
            status = Available,
            isOOO = false
        )
    }

    @Composable
    private fun createPersonaChipActivityUI() {
        val textColor =
            FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                themeMode = FluentTheme.themeMode
            )
        val brandTextColor =
            FluentTheme.aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                themeMode = FluentTheme.themeMode
            )
        var showCloseButton by remember { mutableStateOf(false) }
        var selectedList = rememberSaveable(
            saver = listSaver(
                save = { stateList ->
                    if (stateList.isNotEmpty()) {
                        val first = stateList.first()
                        if (!canBeSaved(first)) {
                            throw IllegalStateException("${first::class} cannot be saved. By default only types which can be stored in the Bundle class can be saved.")
                        }
                    }
                    stateList.toList()
                },
                restore = { it.toMutableStateList() }
            )) {
            mutableStateListOf(
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
            )
        }

        //TODO: Clean Activity using for loops
        Box(Modifier.padding(16.dp)) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicText(
                            text = "Enable/Disable close button on selected state",
                            style = TextStyle(
                                color = brandTextColor,
                                fontSize = 10.sp
                            )
                        )
                        ToggleSwitch(
                            modifier = Modifier.testTag(PERSONA_CHIP_SWITCH),
                            onValueChange = { showCloseButton = !showCloseButton },
                            checkedState = showCloseButton
                        )
                    }
                }
                item {
                    BasicText(
                        text = "Basic Persona chip",
                        style = TextStyle(color = brandTextColor, fontSize = 20.sp),
                        modifier = Modifier.semantics { heading() }
                    )
                }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        BasicText(
                            text = "Person Chip Neutral",
                            style = TextStyle(color = textColor)
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            PersonaChip(
                                person = createPersonWithName(),
                                size = PersonaChipSize.Small,
                                style = Neutral,
                                selected = selectedList[0],
                                onClick = { selectedList[0] = !selectedList[0] })
                            PersonaChip(
                                person = createPersonWithName(),
                                style = Neutral,
                                selected = selectedList[1],
                                onClick = { selectedList[1] = !selectedList[1] },
                                onCloseClick = if (showCloseButton) {
                                    { onClickToast() }
                                } else null
                            )
                        }
                        BasicText(text = "Person Chip Brand", style = TextStyle(color = textColor))
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            PersonaChip(
                                modifier = Modifier.testTag(PERSONA_CHIP_SMALL_CHIP),
                                person = createPersonWithName(),
                                size = PersonaChipSize.Small,
                                style = Brand,
                                selected = selectedList[2],
                                onClick = { selectedList[2] = !selectedList[2] })
                            PersonaChip(
                                modifier = Modifier.testTag(PERSONA_CHIP_MEDIUM_CHIP),
                                person = createPersonWithName(),
                                style = Brand,
                                selected = selectedList[3],
                                onClick = { selectedList[3] = !selectedList[3] },
                                onCloseClick = if (showCloseButton) {
                                    { onClickToast() }
                                } else null
                            )
                        }
                        BasicText(text = "Person Chip Danger", style = TextStyle(color = textColor))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            item {
                                PersonaChip(
                                    person = createPersonWithEmail(),
                                    size = PersonaChipSize.Small,
                                    style = Danger,
                                    selected = selectedList[4],
                                    onClick = { selectedList[4] = !selectedList[4] })
                            }
                            item {
                                PersonaChip(
                                    person = createPersonWithEmail(),
                                    style = Danger,
                                    selected = selectedList[5],
                                    onClick = { selectedList[5] = !selectedList[5] },
                                    onCloseClick = if (showCloseButton) {
                                        { onClickToast() }
                                    } else null
                                )
                            }
                        }
                        BasicText(
                            text = "Person Chip Severe Warning",
                            style = TextStyle(color = textColor)
                        )
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            item {
                                PersonaChip(
                                    person = createPersonWithEmail(),
                                    size = PersonaChipSize.Small,
                                    style = SevereWarning,
                                    selected = selectedList[6],
                                    onClick = { selectedList[6] = !selectedList[6] })
                            }
                            item {
                                PersonaChip(
                                    person = createPersonWithEmail(),
                                    style = SevereWarning,
                                    selected = selectedList[7],
                                    onClick = { selectedList[7] = !selectedList[7] },
                                    onCloseClick = if (showCloseButton) {
                                        { onClickToast() }
                                    } else null
                                )
                            }
                        }
                        BasicText(
                            text = "Person Chip Warning",
                            style = TextStyle(color = textColor)
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            PersonaChip(
                                modifier = Modifier.testTag(PERSONA_CHIP_ANONYMOUS),
                                person = createPersonWithNothing(),
                                size = PersonaChipSize.Small,
                                style = Warning,
                                selected = selectedList[8],
                                onClick = { selectedList[8] = !selectedList[8] })
                            PersonaChip(
                                person = createPersonWithNothing(),
                                style = Warning,
                                selected = selectedList[9],
                                onClick = { selectedList[9] = !selectedList[9] },
                                onCloseClick = if (showCloseButton) {
                                    { onClickToast() }
                                } else null
                            )
                        }
                        BasicText(
                            text = "Person Chip Success",
                            style = TextStyle(color = textColor)
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            PersonaChip(
                                person = createPersonWithName(),
                                size = PersonaChipSize.Small,
                                style = Success,
                                selected = selectedList[10],
                                onClick = { selectedList[10] = !selectedList[10] })
                            PersonaChip(
                                person = createPersonWithName(),
                                style = Success,
                                selected = selectedList[11],
                                onClick = { selectedList[11] = !selectedList[11] },
                                onCloseClick = if (showCloseButton) {
                                    { onClickToast() }
                                } else null
                            )
                        }
                        BasicText(
                            text = "Person Chip Disabled",
                            style = TextStyle(color = textColor)
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            PersonaChip(
                                person = createPersonWithName(),
                                size = PersonaChipSize.Small,
                                style = Neutral,
                                enabled = false,
                                selected = selectedList[12],
                                onClick = { selectedList[12] = !selectedList[12] },
                                onCloseClick = if (showCloseButton) {
                                    { onClickToast() }
                                } else null
                            )
                            PersonaChip(
                                modifier = Modifier.testTag(PERSONA_CHIP_DISABLED),
                                person = createPersonWithName(),
                                style = Neutral,
                                enabled = false,
                                selected = selectedList[13],
                                onClick = { selectedList[13] = !selectedList[13] },
                                onCloseClick = if (showCloseButton) {
                                    { onClickToast() }
                                } else null
                            )
                        }
                    }
                }
                item {
                    BasicText(
                        text = "SearchBox Basic Persona chip",
                        style = TextStyle(
                            color = brandTextColor,
                            fontSize = 20.sp
                        ),
                        modifier = Modifier.semantics { heading() }
                    )
                }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        BasicText(
                            text = "Persona chip Neutral",
                            style = TextStyle(color = textColor)
                        )
                        SearchBarPersonaChip(
                            person = createPersonWithName(),
                            size = PersonaChipSize.Small,
                            selected = selectedList[14],
                            onClick = { selectedList[14] = !selectedList[14] },
                            onCloseClick = if (showCloseButton) {
                                { onClickToast() }
                            } else null
                        )
                        BasicText(text = "Persona chip Brand", style = TextStyle(color = textColor))
                        SearchBarPersonaChip(
                            person = createPersonWithName(),
                            style = FluentStyle.Brand,
                            selected = selectedList[15],
                            onClick = { selectedList[15] = !selectedList[15] },
                            onCloseClick = if (showCloseButton) {
                                { onClickToast() }
                            } else null
                        )
                    }
                }
            }
        }
    }

    private fun onClickToast() {
        Toast.makeText(
            this,
            "Clicked on close icon",
            Toast.LENGTH_SHORT
        ).show()
    }
}