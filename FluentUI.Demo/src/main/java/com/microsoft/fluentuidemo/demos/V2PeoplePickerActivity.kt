package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.LayoutInflater
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle
import com.microsoft.fluentui.tokenized.controls.Label
import com.microsoft.fluentui.tokenized.notification.Snackbar
import com.microsoft.fluentui.tokenized.notification.SnackbarState
import com.microsoft.fluentui.tokenized.peoplepicker.PeoplePicker
import com.microsoft.fluentui.tokenized.peoplepicker.PeoplePickerItemData
import com.microsoft.fluentui.tokenized.peoplepicker.rememberPeoplePickerItemDataList
import com.microsoft.fluentui.tokenized.persona.AvatarGroup
import com.microsoft.fluentui.tokenized.persona.Group
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.persona.Persona
import com.microsoft.fluentui.tokenized.persona.PersonaList
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity
import com.microsoft.fluentuidemo.databinding.V2ActivityComposeBinding
import kotlinx.coroutines.launch

class V2PeoplePickerActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityContent {
                CreatePeoplePickerActivity()
        }
    }

    @Composable
    private fun CreatePeoplePickerActivity() {
        val people = mutableListOf(
            Person(
                "Allan", "Munger",
                image = R.drawable.avatar_allan_munger,
                email = "allan.munger@xyz.com",
                isActive = true
            ),
            Person(
                "Amanda", "Brady",
                email = "amanda.brady@xyz.com",
                isActive = false, status = AvatarStatus.Offline
            ),
            Person(
                "Abhay", "Singh",
                email = "abhay.singh@xyz.com",
                isActive = true, status = AvatarStatus.DND, isOOO = true
            ),
            Person(
                "Carlos", "Slathery",
                email = "carlos.slathery@xyz.com",
                isActive = false, status = AvatarStatus.Busy, isOOO = true
            ),
            Person(
                "Celeste", "Burton",
                email = "celeste.burton@xyz.com",
                image = R.drawable.avatar_celeste_burton,
                isActive = true, status = AvatarStatus.Away
            ),
            Person(
                "Ankit", "Gupta",
                email = "ankit.gupta@xyz.com",
                isActive = true, status = AvatarStatus.Unknown
            ),
            Person(
                "Miguel", "Garcia",
                email = "miguel.garcia@xyz.com",
                image = R.drawable.avatar_miguel_garcia,
                isActive = true, status = AvatarStatus.Blocked
            )
        )
        var selectedPeopleList = rememberPeoplePickerItemDataList()


        val snackbarState = remember { SnackbarState() }
        val scope = rememberCoroutineScope()
        var suggested by rememberSaveable { mutableStateOf(people) }
        var suggestedPersonaList = mutableListOf<Persona>()
        var selectedPersonList = mutableListOf<Person>()
        var errorPeopleList = mutableListOf<Person>()
        var assistiveText by rememberSaveable { mutableStateOf(true) }
        var errorText by rememberSaveable { mutableStateOf(false) }

        Column {
            Row(modifier = Modifier.padding(8.dp)) {
                PeoplePicker(
                    onValueChange = { query, selectedPerson ->
                        scope.launch {
                            suggested = people.filter {
                                it.firstName.lowercase().contains(query.lowercase()) ||
                                        it.lastName.lowercase().contains(query.lowercase())
                            } as MutableList<Person>
                        }
                    },
                    selectedPeopleList = selectedPeopleList,
                    chipValidation = {
                        if (!it.email.isNullOrBlank()) {
                            if (it.email?.contains("@") == true)
                                PersonaChipStyle.Neutral
                            else {
                                errorText = true
                                errorPeopleList.add(it)
                                PersonaChipStyle.Danger
                            }

                        } else {
                            errorText = true
                            errorPeopleList.add(it)
                            PersonaChipStyle.Danger
                        }
                    },
                    modifier = Modifier.weight(1f),
                    onChipClick = {
                        scope.launch {
                            it.selected.value = !it.selected.value
                            snackbarState.showSnackbar("Clicked ${it.person.firstName} ${it.person.lastName}")
                        }
                    },
                    onChipCloseClick = {
                        selectedPeopleList.remove(it)
                        run outer@{
                            errorPeopleList.forEach { errorPerson ->
                                if (errorPerson == it.person) {
                                    errorPeopleList.remove(errorPerson)
                                    return@outer
                                }
                            }
                        }
                        if (errorPeopleList.isEmpty())
                            errorText = false
                    },
                    onTextEntered = { queryText ->
                        if (queryText.isNotBlank() && queryText.isNotEmpty()) {
                            selectedPeopleList.add(
                                PeoplePickerItemData(
                                    Person(queryText, ""),
                                    mutableStateOf(false)
                                )
                            )
                        }
                    },
                    onBackPress = { queryText, it ->
                        if (queryText.isEmpty() && it != null) {
                            if (!it.selected.value) {
                                it.selected.value = !it.selected.value
                            } else {
                                selectedPeopleList.remove(it)
                                errorPeopleList.forEach { errorPerson ->
                                    if (errorPerson == it.person)
                                        errorPeopleList.remove(errorPerson)
                                }
                                if (errorPeopleList.isEmpty())
                                    errorText = false
                            }
                        }
                    },
                    leadingAccessoryContent = {
                        Icon(
                            Icons.Filled.Person,
                            contentDescription = "Person",
                            modifier = Modifier
                                .size(24.dp)
                        )
                    },
                    trailingAccessoryContent = {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "Add",
                            modifier = Modifier
                                .size(24.dp)
                        )
                    },
                    label = "People Picker",
                    searchHint = "Search People",
                    assistiveText = if (assistiveText) "This is a sample Assistive Text" else null,
                    errorString = if (errorText) "This is a sample Error text" else null,
                )
            }

            suggested.forEach outer@{
                selectedPeopleList.forEach { selectedPerson ->
                    if (selectedPerson.person.email == it.email) {
                        return@outer
                    }
                }
                suggestedPersonaList.add(
                    Persona(
                        it,
                        "${it.firstName} ${it.lastName}",
                        subTitle = it.email,
                        onClick = {
                            selectedPeopleList.add(PeoplePickerItemData(it, mutableStateOf(false)))
                            scope.launch {
                                snackbarState.showSnackbar(
                                    "Added ${it.firstName} ${it.lastName}",
                                    enableDismiss = true
                                )
                            }
                        }
                    )
                )
            }

            selectedPeopleList.forEach {
                selectedPersonList.add(it.person)
            }
            Column {
                PersonaList(personas = suggestedPersonaList, modifier = Modifier.padding(8.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Label(
                    modifier = Modifier.padding(8.dp),
                    text = "Selected People from people picker",
                    textStyle = FluentAliasTokens.TypographyTokens.Body1
                )
                AvatarGroup(group = Group(selectedPersonList), modifier = Modifier.padding(8.dp))
                Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                    Snackbar(snackbarState, Modifier.padding(bottom = 12.dp))
                }
            }
        }
    }
}