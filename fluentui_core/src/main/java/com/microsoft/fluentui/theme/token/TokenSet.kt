//
//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//
package com.microsoft.fluentui.theme.token


class TokenSet<T, V>(var defaultValues: ((token: T) -> V)) {
    operator fun get(token: T): V {
        var value = valueOverride[token]
        if (value == null) {
            value = defaultValues(token)
        }
        return value!!
    }

    operator fun set(key: T, value: V) {
        valueOverride[key] = value
    }

    private val valueOverride = HashMap<T, V>()
}