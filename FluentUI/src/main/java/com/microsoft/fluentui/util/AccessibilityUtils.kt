/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.util

import android.content.Context
import android.view.accessibility.AccessibilityManager

/**
 * Utilities for accessibility
 */
val Context.isAccessibilityEnabled: Boolean
    get() = accessibilityManager.isTouchExplorationEnabled

val Context.accessibilityManager: AccessibilityManager
    get() = getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
