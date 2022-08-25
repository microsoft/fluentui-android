package com.microsoft.fluentuidemo.util

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.ContextCompat
import com.microsoft.fluentui.persona.IAvatar
import com.microsoft.fluentuidemo.R
import org.junit.*
import org.junit.runner.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class AvatarUtilsTest {

    private val context = RuntimeEnvironment.getApplication().applicationContext
    companion object{
        private const val NAME = "Lorem"
        private const val EMAIL = "Lorem@mymail.com"
        private const val IMAGE_RESOURCE_ID = 121
        private val IMAGE_URI = Uri.parse("uri:lorem")
        private const val BACKGROUND_COLOR = 10
        private const val CONTENT_DESCRIPTION_LABEL = "Lorem Ipsum is great"
    }
    private val IMAGE_DRAWABLE = ContextCompat.getDrawable(context, R.drawable.avatar_elvia_atkins)
    private val IMAGE_BITMAP = BitmapFactory.decodeResource(context.resources, R.drawable.avatar_katri_ahokas)

    @Test
    fun testCreateAvatar(){
        var avatar = generateAvatar()

        Assert.assertEquals("Lorem", avatar.name)
        Assert.assertEquals(121, avatar.avatarImageResourceId)
        Assert.assertEquals(IMAGE_DRAWABLE, avatar.avatarImageDrawable)
        Assert.assertEquals(IMAGE_BITMAP, avatar.avatarImageBitmap)
        Assert.assertEquals(IMAGE_URI, avatar.avatarImageUri)
        Assert.assertEquals(10, avatar.avatarBackgroundColor)
        Assert.assertEquals("Lorem@mymail.com", avatar.email)
    }

    private fun generateAvatar(): IAvatar{
       return createAvatar(NAME, IMAGE_RESOURCE_ID, IMAGE_DRAWABLE, IMAGE_BITMAP, IMAGE_URI, BACKGROUND_COLOR, EMAIL)
    }
}
