/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.datetimepicker

import java.io.Serializable
import java.time.Duration
import java.time.ZonedDateTime

// TODO PBI #668220 investigate whether it's feasible to replace dateTime + duration with this data class
data class TimeSlot(val start: ZonedDateTime, val duration: Duration) : Serializable
