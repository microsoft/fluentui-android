package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus.Available
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle.Brand
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle.Danger
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle.Neutral
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle.SevereWarning
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle.Success
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle.Warning
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.persona.PersonaChip
import com.microsoft.fluentui.tokenized.persona.SearchBoxPersonaChip
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.R.drawable

class V2PersonaChipActivity: DemoActivity() {
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
    private fun createPerson(): Person{
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
        Box(Modifier.padding(16.dp)){
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                item {
                    Text(text = "Basic Persona chip", color = brandTextColor, fontSize = 20.sp)
                }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text = "Person Chip Neutral", color = textColor)
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            PersonaChip(text = "Text", style = Neutral)
                            PersonaChip(text = "Text", person = createPerson(), style = Neutral)
                        }
                        Text(text = "Person Chip Brand", color = textColor)
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            PersonaChip(text = "Text", style = Brand)
                            PersonaChip(text = "Text", person = createPerson(), style = Brand)
                        }
                        Text(text = "Person Chip Danger", color = textColor)
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            PersonaChip(text = "Text", style = Danger)
                            PersonaChip(text = "Text", person = createPerson(), style = Danger)
                        }
                        Text(text = "Person Chip Severe Warning", color = textColor)
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            PersonaChip(text = "Text", style = SevereWarning)
                            PersonaChip(text = "Text", person = createPerson(), style = SevereWarning)
                        }
                        Text(text = "Person Chip Warning", color = textColor)
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            PersonaChip(text = "Text", style = Warning)
                            PersonaChip(text = "Text", person = createPerson(), style = Warning)
                        }
                        Text(text = "Person Chip Success", color = textColor)
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            PersonaChip(text = "Text", style = Success)
                            PersonaChip(text = "Text", person = createPerson(), style = Success)
                        }
                        Text(text = "Person Chip Disabled", color = textColor)
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            PersonaChip(text = "Text", style = Neutral, enabled = false)
                            PersonaChip(text = "Text", person = createPerson(), style = Neutral, enabled = false)
                        }
                    }
                }
                item {
                    Text(text = "SearchBox Basic Persona chip", color = brandTextColor, fontSize = 20.sp)
                }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text="Persona chip Neutral", color = textColor)
                        SearchBoxPersonaChip(text = "Text", person = createPerson())
                        Text(text="Persona chip Brand", color = textColor)
                        SearchBoxPersonaChip(text = "Text", person = createPerson(), style = FluentStyle.Brand)
                    }
                }
                item {
                    Text(text = "Persona chip with close button", color = brandTextColor, fontSize = 20.sp)
                }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        PersonaChip(text = "Text", person = createPerson(), onClick ={ Toast.makeText(context, "Clicked on close icon", Toast.LENGTH_SHORT).show() }, showCloseButton = true, style = Brand)
                    }
                }
            }

        }
    }
}