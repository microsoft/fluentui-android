package com.microsoft.fluentuidemo

import android.content.Intent
import androidx.lifecycle.ViewModel

object AppProperties : ViewModel() {
}

object Navigation {
     var navigationStack = ArrayDeque<Intent> ()

     fun navigationHandler () {

     }
}