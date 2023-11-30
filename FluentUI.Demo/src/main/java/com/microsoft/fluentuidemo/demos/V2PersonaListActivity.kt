package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.testTag
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus.Available
import com.microsoft.fluentui.theme.token.controlTokens.BorderInset
import com.microsoft.fluentui.theme.token.controlTokens.BorderType
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.persona.Persona
import com.microsoft.fluentui.tokenized.persona.PersonaList
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.R.drawable
import com.microsoft.fluentuidemo.V2DemoActivity
import com.microsoft.fluentuidemo.icons.ListItemIcons
import com.microsoft.fluentuidemo.icons.listitemicons.Chevron
import com.microsoft.fluentuidemo.util.invokeToast

// Tags used for testing
const val PERSONA_LIST = "persona_list"

class V2PersonaListActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-25"
    override val controlTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-24"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityContent {
            CreatePersonaListActivityUI(this)
        }
    }

    @Composable
    private fun trailingIcon() {
        Icon(
            painter = rememberVectorPainter(image = ListItemIcons.Chevron),
            contentDescription = "Flag",
            tint = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground3].value(
                FluentTheme.themeMode
            )
        )
    }

    private fun createPersonasList(context: Context): List<Persona> {
        return arrayListOf(
            Persona(Person(
                "Allan",
                "Munger",
                image = drawable.avatar_allan_munger,
                email = "allan.munger@microsoft.com",
                isActive = true,
                status = Available,
                isOOO = false
            ),
                title = "Allan Munger",
                subTitle = "Manager",
                trailingIcon = { trailingIcon() },
                onClick = { invokeToast("Allan Munger", context) }),
            Persona(Person(
                "Amanda",
                "Brady",
                image = drawable.avatar_amanda_brady,
                email = "allan.munger@microsoft.com",
                isActive = true,
                status = Available,
                isOOO = false
            ),
                title = "Amanda Brady",
                subTitle = "Researcher",
                trailingIcon = { trailingIcon() },
                onClick = { invokeToast("Amanda Brady", context) }),
            Persona(Person(
                "Ashley",
                "McCarthy",
                bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.avatar_ashley_mccarthy),
                email = "allan.munger@microsoft.com",
                isActive = true,
                status = Available,
                isOOO = false
            ),
                title = "Ashley McCarthy",
                subTitle = "Designer",
                trailingIcon = { trailingIcon() },
                onClick = { invokeToast("Ashley McCarthy", context) }),
            Persona(Person(
                "Wanda",
                "Howard",
                image = drawable.avatar_wanda_howard,
                email = "allan.munger@microsoft.com",
                isActive = true,
                status = Available,
                isOOO = false
            ),
                title = "Wanda Howard",
                subTitle = "Manager",
                trailingIcon = { trailingIcon() },
                onClick = { invokeToast("Wanda Howard", context) }),
            Persona(Person(
                "Celeste",
                "Burton",
                image = drawable.avatar_celeste_burton,
                email = "allan.munger@microsoft.com",
                isActive = true,
                status = Available,
                isOOO = false
            ),
                title = "Celeste Burton",
                subTitle = "Engineer",
                trailingIcon = { trailingIcon() },
                onClick = { invokeToast("Celeste Burton", context) }),
            Persona(Person(
                "Cecil",
                "Folk",
                image = drawable.avatar_cecil_folk,
                email = "allan.munger@microsoft.com",
                isActive = true,
                status = Available,
                isOOO = false
            ),
                title = "Cecil Folk",
                subTitle = "Researcher",
                trailingIcon = { trailingIcon() },
                onClick = { invokeToast("Cecil Folk", context) }),
            Persona(Person(
                "Carlos",
                "Slattery",
                image = drawable.avatar_carlos_slattery,
                email = "allan.munger@microsoft.com",
                isActive = true,
                status = Available,
                isOOO = false
            ),
                title = "Carlos Slattery",
                subTitle = "Researcher",
                trailingIcon = { trailingIcon() },
                onClick = { invokeToast("Carlos Slattery", context) }),
            Persona(Person(
                "Carole",
                "Poland",
                image = drawable.avatar_carole_poland,
                email = "allan.munger@microsoft.com",
                isActive = true,
                status = Available,
                isOOO = false
            ),
                title = "Carole Poland",
                subTitle = "Designer",
                trailingIcon = { trailingIcon() },
                onClick = { invokeToast("Carole Poland", context) }),
            Persona(Person(
                "Charlotte",
                "Waltson",
                image = drawable.avatar_charlotte_waltson,
                email = "allan.munger@microsoft.com",
                isActive = true,
                status = Available,
                isOOO = false
            ),
                title = "Charlotte Walston",
                subTitle = "Engineer",
                trailingIcon = { trailingIcon() },
                onClick = { invokeToast("Charlotte Walston", context) }),
            Persona(Person(
                "Colin",
                "Badllinger",
                image = drawable.avatar_colin_ballinger,
                email = "allan.munger@microsoft.com",
                isActive = true,
                status = Available,
                isOOO = false
            ),
                title = "Colin Ballinger",
                subTitle = "Engineer",
                trailingIcon = { trailingIcon() },
                onClick = { invokeToast("Colin Ballinger", context) }),
            Persona(Person(
                "Daisy",
                "Phillips",
                image = drawable.avatar_daisy_phillips,
                email = "allan.munger@microsoft.com",
                isActive = true,
                status = Available,
                isOOO = false
            ),
                title = "Daisy Phillips",
                subTitle = "Researcher",
                trailingIcon = { trailingIcon() },
                onClick = { invokeToast("Daisy Phillips", context) }),
            Persona(Person(
                "elliot",
                "Woodward",
                image = drawable.avatar_elliot_woodward,
                email = "allan.munger@microsoft.com",
                isActive = true,
                status = Available,
                isOOO = false
            ),
                title = "Elliot Woodward",
                subTitle = "Intern",
                trailingIcon = { trailingIcon() },
                onClick = { invokeToast("Elliot Woodward", context) }),
            Persona(Person(
                "Elvia",
                "Atkins",
                image = drawable.avatar_elvia_atkins,
                email = "allan.munger@microsoft.com",
                isActive = true,
                status = Available,
                isOOO = false
            ),
                title = "Elvia Atkins",
                subTitle = "Intern",
                trailingIcon = { trailingIcon() },
                onClick = { invokeToast("Elvia Atkins", context) })
        )
    }

    @Composable
    private fun CreatePersonaListActivityUI(context: Context) {
        PersonaList(
            modifier = Modifier.testTag(PERSONA_LIST),
            personas = createPersonasList(context),
            border = BorderType.Bottom,
            borderInset = BorderInset.XXLarge
        )
    }
}