package com.microsoft.fluentui.tokenized.persona

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import com.microsoft.fluentui.theme.token.controlTokens.BorderInset.XXLarge
import com.microsoft.fluentui.theme.token.controlTokens.BorderType.Bottom

@Composable
fun PersonaListView(persons: List<Person>){
    Box(){
        LazyColumn(){
            itemsIndexed(persons) { index, item ->
                PersonaView(person = item, primaryText = item.firstName, secondaryText = item.lastName, border = Bottom, borderInset = XXLarge)
            }
        }
    }
}