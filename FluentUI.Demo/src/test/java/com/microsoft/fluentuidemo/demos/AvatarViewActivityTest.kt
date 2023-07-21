package com.microsoft.fluentuidemo.demos

import android.app.Activity
import android.content.Intent
import com.microsoft.fluentui.persona.AvatarBorderStyle
import com.microsoft.fluentui.persona.AvatarSize
import com.microsoft.fluentui.persona.AvatarStyle
import com.microsoft.fluentui.persona.AvatarView
import com.microsoft.fluentuidemo.V1DEMO
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.Builder
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.*
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.lang.IllegalStateException

@RunWith(RobolectricTestRunner::class)
class AvatarViewActivityTest {

    private var activity: Activity? = null

    @Before
    fun setup() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        try {
            Picasso.get()
        }catch (e: IllegalStateException){
            Picasso.setSingletonInstance(Builder(context).build())
        }
        var intent = Intent(context, AvatarViewActivity::class.java)
        intent.putExtra(DemoActivity.DEMO_ID, V1DEMO[2].id)
        activity = Robolectric.buildActivity(AvatarViewActivity::class.java, intent).create().start().visible().get()
    }

    @Test
    fun testAvatarImages(){
        //Style: CIRCLE, Border: NO_BORDER
        var image = activity?.findViewById<AvatarView>(R.id.avatar_example_xxlarge_photo)
        assertEquals(AvatarStyle.CIRCLE, image?.avatarStyle)
        assertEquals(AvatarBorderStyle.NO_BORDER, image?.avatarBorderStyle)
        assertEquals(AvatarSize.XXLARGE, image?.avatarSize)

        image = activity?.findViewById<AvatarView>(R.id.avatar_example_xlarge_photo)
        assertEquals(AvatarStyle.CIRCLE, image?.avatarStyle)
        assertEquals(AvatarBorderStyle.NO_BORDER, image?.avatarBorderStyle)
        assertEquals(AvatarSize.XLARGE, image?.avatarSize)

        image = activity?.findViewById<AvatarView>(R.id.avatar_example_large_photo)
        assertEquals(AvatarStyle.CIRCLE, image?.avatarStyle)
        assertEquals(AvatarBorderStyle.NO_BORDER, image?.avatarBorderStyle)
        assertEquals(AvatarSize.LARGE, image?.avatarSize)

        image = activity?.findViewById<AvatarView>(R.id.avatar_example_medium_photo)
        assertEquals(AvatarStyle.CIRCLE, image?.avatarStyle)
        assertEquals(AvatarBorderStyle.NO_BORDER, image?.avatarBorderStyle)
        assertEquals(AvatarSize.MEDIUM, image?.avatarSize)

        image = activity?.findViewById<AvatarView>(R.id.avatar_example_small_photo)
        assertEquals(AvatarStyle.CIRCLE, image?.avatarStyle)
        assertEquals(AvatarBorderStyle.NO_BORDER, image?.avatarBorderStyle)
        assertEquals(AvatarSize.SMALL, image?.avatarSize)

        image = activity?.findViewById<AvatarView>(R.id.avatar_example_xsmall_photo)
        assertEquals(AvatarStyle.CIRCLE, image?.avatarStyle)
        assertEquals(AvatarBorderStyle.NO_BORDER, image?.avatarBorderStyle)
        assertEquals(AvatarSize.XSMALL, image?.avatarSize)

        //Style: SQUARE, Border: NO_BORDER
        image = activity?.findViewById<AvatarView>(R.id.avatar_example_xxlarge_photo_square)
        assertEquals(AvatarStyle.SQUARE, image?.avatarStyle)
        assertEquals(AvatarBorderStyle.NO_BORDER, image?.avatarBorderStyle)
        assertEquals(AvatarSize.XXLARGE, image?.avatarSize)

        image = activity?.findViewById<AvatarView>(R.id.avatar_example_xlarge_photo_square)
        assertEquals(AvatarStyle.SQUARE, image?.avatarStyle)
        assertEquals(AvatarBorderStyle.NO_BORDER, image?.avatarBorderStyle)
        assertEquals(AvatarSize.XLARGE, image?.avatarSize)

        image = activity?.findViewById<AvatarView>(R.id.avatar_example_large_photo_square)
        assertEquals(AvatarStyle.SQUARE, image?.avatarStyle)
        assertEquals(AvatarBorderStyle.NO_BORDER, image?.avatarBorderStyle)
        assertEquals(AvatarSize.LARGE, image?.avatarSize)

        image = activity?.findViewById<AvatarView>(R.id.avatar_example_medium_photo_square)
        assertEquals(AvatarStyle.SQUARE, image?.avatarStyle)
        assertEquals(AvatarBorderStyle.NO_BORDER, image?.avatarBorderStyle)
        assertEquals(AvatarSize.MEDIUM, image?.avatarSize)

        image = activity?.findViewById<AvatarView>(R.id.avatar_example_small_photo_square)
        assertEquals(AvatarStyle.SQUARE, image?.avatarStyle)
        assertEquals(AvatarBorderStyle.NO_BORDER, image?.avatarBorderStyle)
        assertEquals(AvatarSize.SMALL, image?.avatarSize)

        image = activity?.findViewById<AvatarView>(R.id.avatar_example_xsmall_photo_square)
        assertEquals(AvatarStyle.SQUARE, image?.avatarStyle)
        assertEquals(AvatarBorderStyle.NO_BORDER, image?.avatarBorderStyle)
        assertEquals(AvatarSize.XSMALL, image?.avatarSize)

    }

    @Test
    fun testAvatarInitials(){
        //Style: CIRCLE, Border: NO_BORDER, Image: Null
        var image = activity?.findViewById<AvatarView>(R.id.avatar_example_xlarge_initials)
        assertEquals(AvatarStyle.CIRCLE, image?.avatarStyle)
        assertEquals(AvatarBorderStyle.NO_BORDER, image?.avatarBorderStyle)
        assertEquals(AvatarSize.XLARGE, image?.avatarSize)
        assertNull(image?.avatarImageResourceId)
        assertNull(image?.avatarImageDrawable)
        assertNull(image?.avatarImageBitmap)

        image = activity?.findViewById<AvatarView>(R.id.avatar_example_large_initials)
        assertEquals(AvatarStyle.CIRCLE, image?.avatarStyle)
        assertEquals(AvatarBorderStyle.NO_BORDER, image?.avatarBorderStyle)
        assertEquals(AvatarSize.LARGE, image?.avatarSize)
        assertNull(image?.avatarImageResourceId)
        assertNull(image?.avatarImageDrawable)
        assertNull(image?.avatarImageBitmap)

        image = activity?.findViewById<AvatarView>(R.id.avatar_example_medium_initials)
        assertEquals(AvatarStyle.CIRCLE, image?.avatarStyle)
        assertEquals(AvatarBorderStyle.NO_BORDER, image?.avatarBorderStyle)
        assertEquals(AvatarSize.MEDIUM, image?.avatarSize)
        assertNull(image?.avatarImageResourceId)
        assertNull(image?.avatarImageDrawable)
        assertNull(image?.avatarImageBitmap)

        image = activity?.findViewById<AvatarView>(R.id.avatar_example_small_initials)
        assertEquals(AvatarStyle.CIRCLE, image?.avatarStyle)
        assertEquals(AvatarBorderStyle.NO_BORDER, image?.avatarBorderStyle)
        assertEquals(AvatarSize.SMALL, image?.avatarSize)
        assertNull(image?.avatarImageResourceId)
        assertNull(image?.avatarImageDrawable)
        assertNull(image?.avatarImageBitmap)

        image = activity?.findViewById<AvatarView>(R.id.avatar_example_xsmall_initials)
        assertEquals(AvatarStyle.CIRCLE, image?.avatarStyle)
        assertEquals(AvatarBorderStyle.NO_BORDER, image?.avatarBorderStyle)
        assertEquals(AvatarSize.XSMALL, image?.avatarSize)
        assertNull(image?.avatarImageResourceId)
        assertNull(image?.avatarImageDrawable)
        assertNull(image?.avatarImageBitmap)

        //Style: SQUARE, Border: NO_BORDER, Image: Null
        image = activity?.findViewById<AvatarView>(R.id.avatar_example_xxlarge_initials_square)
        assertEquals(AvatarStyle.SQUARE, image?.avatarStyle)
        assertEquals(AvatarBorderStyle.NO_BORDER, image?.avatarBorderStyle)
        assertEquals(AvatarSize.XXLARGE, image?.avatarSize)
        assertNull(image?.avatarImageResourceId)
        assertNull(image?.avatarImageDrawable)
        assertNull(image?.avatarImageBitmap)

        image = activity?.findViewById<AvatarView>(R.id.avatar_example_xlarge_initials_square)
        assertEquals(AvatarStyle.SQUARE, image?.avatarStyle)
        assertEquals(AvatarBorderStyle.NO_BORDER, image?.avatarBorderStyle)
        assertEquals(AvatarSize.XLARGE, image?.avatarSize)
        assertNull(image?.avatarImageResourceId)
        assertNull(image?.avatarImageDrawable)
        assertNull(image?.avatarImageBitmap)

        image = activity?.findViewById<AvatarView>(R.id.avatar_example_large_initials_square)
        assertEquals(AvatarStyle.SQUARE, image?.avatarStyle)
        assertEquals(AvatarBorderStyle.NO_BORDER, image?.avatarBorderStyle)
        assertEquals(AvatarSize.LARGE, image?.avatarSize)
        assertNull(image?.avatarImageResourceId)
        assertNull(image?.avatarImageDrawable)
        assertNull(image?.avatarImageBitmap)

        image = activity?.findViewById<AvatarView>(R.id.avatar_example_medium_initials_square)
        assertEquals(AvatarStyle.SQUARE, image?.avatarStyle)
        assertEquals(AvatarBorderStyle.NO_BORDER, image?.avatarBorderStyle)
        assertEquals(AvatarSize.MEDIUM, image?.avatarSize)
        assertNull(image?.avatarImageResourceId)
        assertNull(image?.avatarImageDrawable)
        assertNull(image?.avatarImageBitmap)

        image = activity?.findViewById<AvatarView>(R.id.avatar_example_small_initials_square)
        assertEquals(AvatarStyle.SQUARE, image?.avatarStyle)
        assertEquals(AvatarBorderStyle.NO_BORDER, image?.avatarBorderStyle)
        assertEquals(AvatarSize.SMALL, image?.avatarSize)
        assertNull(image?.avatarImageResourceId)
        assertNull(image?.avatarImageDrawable)
        assertNull(image?.avatarImageBitmap)

        image = activity?.findViewById<AvatarView>(R.id.avatar_example_xsmall_initials_square)
        assertEquals(AvatarStyle.SQUARE, image?.avatarStyle)
        assertEquals(AvatarBorderStyle.NO_BORDER, image?.avatarBorderStyle)
        assertEquals(AvatarSize.XSMALL, image?.avatarSize)
        assertNull(image?.avatarImageResourceId)
        assertNull(image?.avatarImageDrawable)
        assertNull(image?.avatarImageBitmap)


    }
}