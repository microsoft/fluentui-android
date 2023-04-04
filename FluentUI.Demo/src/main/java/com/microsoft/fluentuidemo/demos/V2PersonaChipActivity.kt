package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus.Available
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipSize
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle.*
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.persona.PersonaChip
import com.microsoft.fluentui.tokenized.persona.SearchBarPersonaChip
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.R.drawable
import com.microsoft.fluentuidemo.util.invokeToast

class V2PersonaChipActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_compose
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val compose_here = findViewById<ComposeView>(R.id.compose_here)

        compose_here.setContent {
            FluentTheme {
                CreatePersonaChipActivityUI()
            }
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
    fun CreatePersonaChipActivityUI() {
        var context  = this
        val textColor =
            FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                themeMode = FluentTheme.themeMode
            )
        val brandTextColor =
            FluentTheme.aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
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
                        Text(
                            text = "Enable/Disable close button on selected state",
                            color = brandTextColor,
                            fontSize = 10.sp
                        )
                        ToggleSwitch(
                            onValueChange = { showCloseButton = !showCloseButton },
                            checkedState = showCloseButton
                        )
                    }
                }
                item {
                    Text(text = "Basic Persona chip", color = brandTextColor, fontSize = 20.sp)
                }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text = "Person Chip Neutral", color = textColor)
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
                                    { invokeToast("Clicked on close icon", context) }
                                } else null
                            )
                        }
                        Text(text = "Person Chip Brand", color = textColor)
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            PersonaChip(
                                person = createPersonWithName(),
                                size = PersonaChipSize.Small,
                                style = Brand,
                                selected = selectedList[2],
                                onClick = { selectedList[2] = !selectedList[2] })
                            PersonaChip(
                                person = createPersonWithName(),
                                style = Brand,
                                selected = selectedList[3],
                                onClick = { selectedList[3] = !selectedList[3] },
                                onCloseClick = if (showCloseButton) {
                                    { invokeToast("Clicked on close icon", context) }
                                } else null
                            )
                        }
                        Text(text = "Person Chip Danger", color = textColor)
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
                                        { invokeToast("Clicked on close icon", context) }
                                    } else null
                                )
                            }
                        }
                        Text(text = "Person Chip Severe Warning", color = textColor)
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
                                        { invokeToast("Clicked on close icon", context) }
                                    } else null
                                )
                            }
                        }
                        Text(text = "Person Chip Warning", color = textColor)
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            PersonaChip(
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
                                    { invokeToast("Clicked on close icon", context) }
                                } else null
                            )
                        }
                        Text(text = "Person Chip Success", color = textColor)
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
                                    { invokeToast("Clicked on close icon", context) }
                                } else null
                            )
                        }
                        Text(text = "Person Chip Disabled", color = textColor)
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            PersonaChip(
                                person = createPersonWithName(),
                                size = PersonaChipSize.Small,
                                style = Neutral,
                                enabled = false,
                                selected = selectedList[12],
                                onClick = { selectedList[12] = !selectedList[12] },
                                onCloseClick = if (showCloseButton) {
                                    { invokeToast("Clicked on close icon", context) }
                                } else null
                            )
                            PersonaChip(
                                person = createPersonWithName(),
                                style = Neutral,
                                enabled = false,
                                selected = selectedList[13],
                                onClick = { selectedList[13] = !selectedList[13] },
                                onCloseClick = if (showCloseButton) {
                                    { invokeToast("Clicked on close icon", context) }
                                } else null
                            )
                        }
                    }
                }
                item {
                    Text(
                        text = "SearchBox Basic Persona chip",
                        color = brandTextColor,
                        fontSize = 20.sp
                    )
                }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text = "Persona chip Neutral", color = textColor)
                        SearchBarPersonaChip(
                            person = createPersonWithName(),
                            size = PersonaChipSize.Small,
                            selected = selectedList[14],
                            onClick = { selectedList[14] = !selectedList[14] },
                            onCloseClick = if (showCloseButton) {
                                { invokeToast("Clicked on close icon", context) }
                            } else null
                        )
                        Text(text = "Persona chip Brand", color = textColor)
                        SearchBarPersonaChip(
                            person = createPersonWithName(),
                            style = FluentStyle.Brand,
                            selected = selectedList[15],
                            onClick = { selectedList[15] = !selectedList[15] },
                            onCloseClick = if (showCloseButton) {
                                { invokeToast("Clicked on close icon", context) }
                            } else null
                        )
                    }
                }
            }
        }
    }
}