package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus.Available
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipSize.Medium
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle.Brand
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle.Danger
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle.Neutral
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle.SevereWarning
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle.Success
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle.Warning
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.persona.PersonChip
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
                createPersonaChipActivityUI()
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
    fun createPersonaChipActivityUI() {
        Box(Modifier.padding(16.dp)){
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
              Text(text = "Person Chip Neutral")
              Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                  PersonChip(text = "Sample", style = Neutral)
                  PersonChip(text = "Sample", person = createPerson(), style = Neutral)
              }
              Text(text = "Person Chip Brand")
              Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                  PersonChip(text = "Sample", style = Brand)
                  PersonChip(text = "Sample", person = createPerson(), style = Brand)
              }
              Text(text = "Person Chip Danger")
              Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                  PersonChip(text = "Sample", style = Danger)
                  PersonChip(text = "Sample", person = createPerson(), style = Danger)
              }
              Text(text = "Person Chip Severe Warning")
              Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                  PersonChip(text = "Sample", style = SevereWarning)
                  PersonChip(text = "Sample", person = createPerson(), style = SevereWarning)
              }
              Text(text = "Person Chip Warning")
              Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                  PersonChip(text = "Sample", style = Warning)
                  PersonChip(text = "Sample", person = createPerson(), style = Warning)
              }
              Text(text = "Person Chip Success")
              Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                  PersonChip(text = "Sample", style = Success)
                  PersonChip(text = "Sample", person = createPerson(), style = Success)
              }
          }  
        }
    }
}
