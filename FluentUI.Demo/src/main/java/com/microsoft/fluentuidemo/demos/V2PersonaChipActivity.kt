package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus.Available
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle.*
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.persona.PersonaChip
import com.microsoft.fluentui.tokenized.persona.SearchBoxPersonaChip
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.R.drawable

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
                createPersonaChipActivityUI(this)
            }
        }
    }

    private fun createPerson(): Person {
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

    @Composable
    fun createPersonaChipActivityUI(context: Context) {
        val textColor =
            FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                themeMode = FluentTheme.themeMode
            )
        val brandTextColor =
            FluentTheme.aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                themeMode = FluentTheme.themeMode
            )
        var chipSelected1 by remember { mutableStateOf(false) }
        var chipSelected2 by remember { mutableStateOf(false) }
        var chipSelected3 by remember { mutableStateOf(false) }
        var chipSelected4 by remember { mutableStateOf(false) }
        var chipSelected5 by remember { mutableStateOf(false) }
        var chipSelected6 by remember { mutableStateOf(false) }
        var chipSelected7 by remember { mutableStateOf(false) }
        var chipSelected8 by remember { mutableStateOf(false) }
        var chipSelected9 by remember { mutableStateOf(false) }
        var chipSelected10 by remember { mutableStateOf(false) }
        var chipSelected11 by remember { mutableStateOf(false) }
        var chipSelected12 by remember { mutableStateOf(false) }
        var chipSelected13 by remember { mutableStateOf(false) }
        var chipSelected14 by remember { mutableStateOf(false) }
        var chipSelected15 by remember { mutableStateOf(false) }
        var chipSelected16 by remember { mutableStateOf(false) }
        var chipSelected17 by remember { mutableStateOf(false) }

        Box(Modifier.padding(16.dp)) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                item {
                    Text(text = "Basic Persona chip", color = brandTextColor, fontSize = 20.sp)
                }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text = "Person Chip Neutral", color = textColor)
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            PersonaChip(text = "Text", style = Neutral, selected = chipSelected1, onClick = {chipSelected1 = !chipSelected1})
                            PersonaChip(text = "Text", person = createPerson(), style = Neutral, selected = chipSelected2, onClick = {chipSelected2 = !chipSelected2})
                        }
                        Text(text = "Person Chip Brand", color = textColor)
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            PersonaChip(text = "Text", style = Brand, selected = chipSelected3, onClick = {chipSelected3 = !chipSelected3})
                            PersonaChip(text = "Text", person = createPerson(), style = Brand, selected = chipSelected4, onClick = {chipSelected4 = !chipSelected4})
                        }
                        Text(text = "Person Chip Danger", color = textColor)
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            PersonaChip(text = "Text", style = Danger, selected = chipSelected5, onClick = {chipSelected5 = !chipSelected5})
                            PersonaChip(text = "Text", person = createPerson(), style = Danger, selected = chipSelected6, onClick = {chipSelected6 = !chipSelected6})
                        }
                        Text(text = "Person Chip Severe Warning", color = textColor)
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            PersonaChip(text = "Text", style = SevereWarning, selected = chipSelected7, onClick = {chipSelected7 = !chipSelected7})
                            PersonaChip(
                                text = "Text",
                                person = createPerson(),
                                style = SevereWarning,
                                selected = chipSelected8,
                                onClick = {chipSelected8 = !chipSelected8}
                            )
                        }
                        Text(text = "Person Chip Warning", color = textColor)
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            PersonaChip(text = "Text", style = Warning, selected = chipSelected9, onClick = {chipSelected9 = !chipSelected9})
                            PersonaChip(text = "Text", person = createPerson(), style = Warning, selected = chipSelected10, onClick = {chipSelected10 = !chipSelected10})
                        }
                        Text(text = "Person Chip Success", color = textColor)
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            PersonaChip(text = "Text", style = Success, selected = chipSelected11, onClick = {chipSelected11 = !chipSelected11})
                            PersonaChip(text = "Text", person = createPerson(), style = Success, selected = chipSelected12, onClick = {chipSelected12 = !chipSelected12})
                        }
                        Text(text = "Person Chip Disabled", color = textColor)
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            PersonaChip(text = "Text", style = Neutral, enabled = false, selected = chipSelected13, onClick = {chipSelected13 = !chipSelected13})
                            PersonaChip(
                                text = "Text",
                                person = createPerson(),
                                style = Neutral,
                                enabled = false,
                                selected = chipSelected14,
                                onClick = {chipSelected14 = !chipSelected14}
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
                        SearchBoxPersonaChip(text = "Text", person = createPerson(), selected = chipSelected15, onClick = {chipSelected15 = !chipSelected15})
                        Text(text = "Persona chip Brand", color = textColor)
                        SearchBoxPersonaChip(
                            text = "Text",
                            person = createPerson(),
                            style = FluentStyle.Brand,
                            selected = chipSelected16,
                            onClick = {chipSelected16 = !chipSelected16}
                        )
                    }
                }
                item {
                    Text(
                        text = "Persona chip with close button",
                        color = brandTextColor,
                        fontSize = 20.sp
                    )
                }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        PersonaChip(
                            text = "Text",
                            person = createPerson(),
                            onCloseClick = {
                                Toast.makeText(
                                    context,
                                    "Clicked on close icon",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            showCloseButton = true,
                            style = Brand, selected = chipSelected17, onClick = {chipSelected17 = !chipSelected17}
                        )
                    }
                }
            }
        }
    }
}