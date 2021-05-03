package com.microsoft.fluentui.vNext.persona

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.annotation.DrawableRes
import com.microsoft.fluentui.R
import com.microsoft.fluentui.generator.AvatarSize
import com.microsoft.fluentui.generator.AvatarStyle


interface IAvatar {
    /**
     * [name] is used in conjunction with [email] to set initials.
     */
    var name: String
    var email: String
    var avatarImageBitmap: Bitmap?
    var avatarImageDrawable: Drawable?
    var avatarImageResourceId: Int
    var avatarImageUri: Uri?
    var avatarBackgroundColor: Int?
    var avatarForegroundColor: Int?
    var avatarRingColor: Int?
    var isRingVisible: Boolean
    var isTransparent: Boolean
    var isOOF: Boolean
    var avatarStyle: AvatarStyle?
    var avatarSize: AvatarSize?
    var presence: AvatarPresence

}

enum class AvatarPresence() {
    NONE {
        override fun getImage(isOOF: Boolean): Int {
            return -1
        }

    },
    AVAILABLE {
        override fun getImage(isOOF: Boolean): Int {
            return if (isOOF) R.drawable.ic_fluent_presence_available_16_regular else R.drawable.ic_fluent_presence_available_16_filled
        }
    },
    AWAY {
        override fun getImage(isOOF: Boolean): Int {
            return if (isOOF) R.drawable.ic_fluent_presence_oof_16_regular else R.drawable.ic_fluent_presence_away_16_filled
        }
    },
    BLOCKED {
        override fun getImage(isOOF: Boolean): Int {
            return R.drawable.ic_fluent_presence_blocked_16_regular
        }
    },
    BUSY {
        override fun getImage(isOOF: Boolean): Int {
            return if (isOOF) R.drawable.ic_fluent_presence_busy_16_regular else R.drawable.ic_fluent_presence_busy_16_filled
        }
    },
    DND {
        override fun getImage(isOOF: Boolean): Int {
            return if (isOOF) R.drawable.ic_fluent_presence_dnd_16_regular else R.drawable.ic_fluent_presence_dnd_16_filled
        }
    },
    OFFLINE {
        override fun getImage(isOOF: Boolean): Int {
            return if (isOOF) R.drawable.ic_fluent_presence_oof_16_regular else R.drawable.ic_fluent_presence_offline_16_regular
        }
    },
    UNKNOWN {
        override fun getImage(isOOF: Boolean): Int {
            return R.drawable.ic_fluent_presence_unknown_16_regular
        }
    };

    @DrawableRes
    abstract fun getImage(isOOF: Boolean = false): Int
}