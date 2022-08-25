package com.example.theme.token

import androidx.compose.runtime.Composable
import com.microsoft.fluentui.theme.token.controlTokens.AvatarInfo
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStyle
import com.microsoft.fluentui.theme.token.controlTokens.AvatarTokens

class AnonymousAvatarTokens : AvatarTokens() {

    @Composable
    override fun avatarStyle(avatarInfo: AvatarInfo): AvatarStyle {
        return AvatarStyle.Anonymous
    }
}

class StandardInvertedAvatarTokens : AvatarTokens() {

    @Composable
    override fun avatarStyle(avatarInfo: AvatarInfo): AvatarStyle {
        return AvatarStyle.StandardInverted
    }
}

class AnonymousAccentAvatarTokens : AvatarTokens() {

    @Composable
    override fun avatarStyle(avatarInfo: AvatarInfo): AvatarStyle {
        return AvatarStyle.AnonymousAccent
    }
}
