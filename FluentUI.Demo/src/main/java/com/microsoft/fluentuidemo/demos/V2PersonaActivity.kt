package com.microsoft.fluentuidemo.demos

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.persona.Persona
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class V2PersonaActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_compose
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val compose_here = findViewById<ComposeView>(R.id.compose_here)

        compose_here.setContent {
            FluentTheme {
                createActivityUI()
            }
        }
    }
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun createActivityUI(){
        val person1 = Person("Allan", "Munger",
            image = R.drawable.avatar_allan_munger, isActive = true,
            status = AvatarStatus.Available, isOOO = false)
        val person2 = Person("Charlotte", "Waltson",
            image = R.drawable.avatar_charlotte_waltson, isActive = false,
            status = AvatarStatus.Blocked, isOOO = false)
        val person3 = Person("Carole", "Poland",
            image = R.drawable.avatar_carole_poland, isActive = false,
            status = AvatarStatus.Away, isOOO = true)
        val scaffoldState: ScaffoldState = rememberScaffoldState()
        val coroutineScope: CoroutineScope = rememberCoroutineScope()
        Scaffold(scaffoldState = scaffoldState ) {
            Box(){
                Column() {
                    LazyColumn(){
                        item {
                            Column() {
                                Text(modifier = Modifier.padding(start = 8.dp, top = 16.dp), text = "One line Persona view with small Avatar", color = Color(0xFF2886DE))
                                Persona(person = person1, primaryText = person1.firstName+" "+person1.lastName, onClick = { onClick(person1.firstName, coroutineScope, scaffoldState) }, enableAvatarActivityRings = true)
                            }
                        }
                        item {
                            Column() {
                                Text(modifier = Modifier.padding(start = 8.dp, top = 16.dp), text = "Two line Persona view with large Avatar", color = Color(0xFF2886DE))
                                Persona(person = person2, primaryText = person2.firstName+" "+person2.lastName, secondaryText = "Microsoft", onClick = { onClick(person2.firstName, coroutineScope, scaffoldState) }, )
                            }
                        }
                        item {
                            Column() {
                                Text(modifier = Modifier.padding(start = 8.dp, top = 16.dp), text = "Three line Persona View with Xlarge Avatar", color = Color(0xFF2886DE))
                                Persona(person = person3, primaryText = person3.firstName+" "+person3.lastName, secondaryText = "Microsoft", tertiaryText = person3.status.toString(), onClick = { onClick(person3.firstName, coroutineScope, scaffoldState) }, )
                            }
                        }
                    }
                }

            }
        }

    }
    fun onClick(text: String, coroutineScope:CoroutineScope, scaffoldState: ScaffoldState){
        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
        coroutineScope.launch {
            val result = scaffoldState.snackbarHostState.showSnackbar(
                message = "Clicked on $text",
                actionLabel = "Close"
            )
            when(result){
                SnackbarResult.ActionPerformed -> scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
            }
        }
    }
}
