package com.microsoft.fluentuidemo.demos

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.controlTokens.AvatarCarouselSize
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.tokenized.persona.AvatarCarousel
import com.microsoft.fluentui.tokenized.persona.AvatarCarouselItem
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R

class V2AvatarCarouselActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_compose

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val composeHere = findViewById<ComposeView>(R.id.compose_here)

        composeHere.setContent {
            FluentTheme {
                CreateAvatarCarouselActivityUI()
            }
        }
    }
}

@Composable
fun createAvatarPersons(mContext: Context): ArrayList<AvatarCarouselItem> {
    return arrayListOf(
        AvatarCarouselItem(
            person = Person(
                "Allan",
                "Munger",
                image = R.drawable.avatar_allan_munger,
                isActive = true,
                status = AvatarStatus.Available,
                isOOO = false
            ),
            enableActivityRing = true,
            onItemClick = { mToast(mContext, "Allan") }
        ),
        AvatarCarouselItem(
            person = Person(
                "Amanda",
                "Brady",
                image = R.drawable.avatar_amanda_brady,
                isActive = true,
                status = AvatarStatus.Away,
                isOOO = false
            ),
            enableActivityRing = true,
            onItemClick = { mToast(mContext, "Amanda") }
        ),
        AvatarCarouselItem(
            person = Person(
                "Ashley",
                "McCarthy",
                image = R.drawable.avatar_ashley_mccarthy,
                isActive = true,
                status = AvatarStatus.Blocked,
                isOOO = false,
            ),
            enabled = false,
            onItemClick = { mToast(mContext, "Ashley") }
        ),
        AvatarCarouselItem(
            person = Person(
                "Wanda",
                "Howard",
                image = R.drawable.avatar_wanda_howard,
                isActive = true,
                status = AvatarStatus.Busy,
                isOOO = false
            ),
            enableActivityRing = false,
            onItemClick = { mToast(mContext, "Wanda") }
        ),
        AvatarCarouselItem(
            person = Person(
                "Celeste",
                "Burton",
                image = R.drawable.avatar_celeste_burton,
                isActive = true,
                status = AvatarStatus.Blocked,
                isOOO = false,
            ),
            enabled = false,
            onItemClick = { mToast(mContext, "Celeste") }
        ),
        AvatarCarouselItem(
            person = Person(
                "Cecil",
                "Folk",
                image = R.drawable.avatar_cecil_folk,
                isActive = true,
                status = AvatarStatus.Available,
                isOOO = false
            ),
            onItemClick = { mToast(mContext, "Cecil") }
        ),
        AvatarCarouselItem(
            person = Person(
                "Carlos",
                "Slattery",
                image = R.drawable.avatar_carlos_slattery,
                isActive = true,
                status = AvatarStatus.Offline,
                isOOO = false
            ),
            onItemClick = { mToast(mContext, "Carlos") }
        ),
        AvatarCarouselItem(
            person = Person(
                "Carole",
                "Poland",
                image = R.drawable.avatar_carole_poland,
                isActive = true,
                status = AvatarStatus.Unknown,
                isOOO = false
            ),
            enableActivityRing = true,
            onItemClick = { mToast(mContext, "Carole") }
        ),
        AvatarCarouselItem(
            person = Person(
                "Charlotte",
                "Waltson",
                image = R.drawable.avatar_charlotte_waltson,
                isActive = true,
                status = AvatarStatus.Blocked,
                isOOO = false
            ),
            enabled = false,
            onItemClick = { mToast(mContext, "Charlotte") }
        ),
        AvatarCarouselItem(
            person = Person(
                "Colin",
                "Badllinger",
                image = R.drawable.avatar_colin_ballinger,
                isActive = true,
                status = AvatarStatus.Available,
                isOOO = false
            ),
            onItemClick = { mToast(mContext, "Colin") }
        ),
        AvatarCarouselItem(
            person = Person(
                "Daisy",
                "Phillips",
                image = R.drawable.avatar_daisy_phillips,
                isActive = true,
                status = AvatarStatus.Available,
                isOOO = false
            ),
            onItemClick = { mToast(mContext, "Daisy") }
        ),
        AvatarCarouselItem(
            person = Person(
                "Elliot",
                "Woodward",
                image = R.drawable.avatar_elliot_woodward,
                isActive = true,
                status = AvatarStatus.Available,
                isOOO = false
            ),
            enableActivityRing = true,
            onItemClick = { mToast(mContext, "Elliot") }
        ),
        AvatarCarouselItem(
            person = Person(
                "Elvia",
                "Atkins",
                image = R.drawable.avatar_elvia_atkins,
                isActive = true,
                status = AvatarStatus.Blocked,
                isOOO = false
            ),
            enabled = false,
            onItemClick = { mToast(mContext, "Elvia") }
        )
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreateAvatarCarouselActivityUI() {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val mContext = LocalContext.current
    Scaffold(scaffoldState = scaffoldState) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "Large Avatar Carousel",
                color = Color(0xFF2886DE)
            )
            AvatarCarousel(
                avatarList = createAvatarPersons(mContext),
                size = AvatarCarouselSize.Large
            )


            Divider(Modifier.fillMaxWidth())
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "Medium Avatar Carousel with Presence indicator",
                color = Color(0xFF2886DE)
            )
            AvatarCarousel(
                avatarList = createAvatarPersons(mContext),
                size = AvatarCarouselSize.Medium,
                enablePresence = true
            )
        }
    }
}

var toast: Toast? = null
private fun mToast(context: Context, name: String) {
    toast?.cancel()
    toast = Toast.makeText(context, "Clicked on $name", Toast.LENGTH_SHORT)
    toast?.show()
}