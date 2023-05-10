package com.microsoft.fluentui.tokenized.controls

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.LabelInfo
import com.microsoft.fluentui.theme.token.controlTokens.LabelTokens
import com.microsoft.fluentui.theme.token.controlTokens.TypographyTokens

object Label {

    @Composable
    fun Display(
        text: String,
        modifier: Modifier = Modifier,
        labelTokens: LabelTokens? = null
    ) {
        val tokens = labelTokens
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Label] as LabelTokens
        val labelInfo = LabelInfo(TypographyTokens.Display)
        val textStyle = tokens.typography(labelInfo = labelInfo)
        val textColor = tokens.textColor(labelInfo = labelInfo)
        BasicText(
            modifier = modifier,
            text = text,
            style = textStyle.merge(TextStyle(color = textColor))
        )
    }

    @Composable
    fun LargeTitle(
        text: String,
        modifier: Modifier = Modifier,
        labelTokens: LabelTokens? = null
    ) {
        val tokens = labelTokens
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Label] as LabelTokens
        val labelInfo = LabelInfo(TypographyTokens.LargeTitle)
        val textStyle = tokens.typography(labelInfo = labelInfo)
        val textColor = tokens.textColor(labelInfo = labelInfo)
        BasicText(
            modifier = modifier,
            text = text,
            style = textStyle.merge(TextStyle(color = textColor))
        )
    }

    @Composable
    fun Title1(
        text: String,
        modifier: Modifier = Modifier,
        labelTokens: LabelTokens? = null
    ) {
        val tokens = labelTokens
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Label] as LabelTokens
        val labelInfo = LabelInfo(TypographyTokens.Title1)
        val textStyle = tokens.typography(labelInfo = labelInfo)
        val textColor = tokens.textColor(labelInfo = labelInfo)
        BasicText(
            modifier = modifier,
            text = text,
            style = textStyle.merge(TextStyle(color = textColor))
        )
    }

    @Composable
    fun Title2(
        text: String,
        modifier: Modifier = Modifier,
        labelTokens: LabelTokens? = null
    ) {
        val tokens = labelTokens
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Label] as LabelTokens
        val labelInfo = LabelInfo(TypographyTokens.Title2)
        val textStyle = tokens.typography(labelInfo = labelInfo)
        val textColor = tokens.textColor(labelInfo = labelInfo)
        BasicText(
            modifier = modifier,
            text = text,
            style = textStyle.merge(TextStyle(color = textColor))
        )
    }

    @Composable
    fun Title3(
        text: String,
        modifier: Modifier = Modifier,
        labelTokens: LabelTokens? = null
    ) {
        val tokens = labelTokens
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Label] as LabelTokens
        val labelInfo = LabelInfo(TypographyTokens.Title3)
        val textStyle = tokens.typography(labelInfo = labelInfo)
        val textColor = tokens.textColor(labelInfo = labelInfo)
        BasicText(
            modifier = modifier,
            text = text,
            style = textStyle.merge(TextStyle(color = textColor))
        )
    }

    @Composable
    fun Body1Strong(
        text: String,
        modifier: Modifier = Modifier,
        labelTokens: LabelTokens? = null
    ) {
        val tokens = labelTokens
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Label] as LabelTokens
        val labelInfo = LabelInfo(TypographyTokens.Body1Strong)
        val textStyle = tokens.typography(labelInfo = labelInfo)
        val textColor = tokens.textColor(labelInfo = labelInfo)
        BasicText(
            modifier = modifier,
            text = text,
            style = textStyle.merge(TextStyle(color = textColor))
        )
    }

    @Composable
    fun Body1(
        text: String,
        modifier: Modifier = Modifier,
        labelTokens: LabelTokens? = null
    ) {
        val tokens = labelTokens
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Label] as LabelTokens
        val labelInfo = LabelInfo(TypographyTokens.Body1)
        val textStyle = tokens.typography(labelInfo = labelInfo)
        val textColor = tokens.textColor(labelInfo = labelInfo)
        BasicText(
            modifier = modifier,
            text = text,
            style = textStyle.merge(TextStyle(color = textColor))
        )
    }

    @Composable
    fun Body2Strong(
        text: String,
        modifier: Modifier = Modifier,
        labelTokens: LabelTokens? = null
    ) {
        val tokens = labelTokens
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Label] as LabelTokens
        val labelInfo = LabelInfo(TypographyTokens.Body2Strong)
        val textStyle = tokens.typography(labelInfo = labelInfo)
        val textColor = tokens.textColor(labelInfo = labelInfo)
        BasicText(
            modifier = modifier,
            text = text,
            style = textStyle.merge(TextStyle(color = textColor))
        )
    }

    @Composable
    fun Body2(
        text: String,
        modifier: Modifier = Modifier,
        labelTokens: LabelTokens? = null
    ) {
        val tokens = labelTokens
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Label] as LabelTokens
        val labelInfo = LabelInfo(TypographyTokens.Body2)
        val textStyle = tokens.typography(labelInfo = labelInfo)
        val textColor = tokens.textColor(labelInfo = labelInfo)
        BasicText(
            modifier = modifier,
            text = text,
            style = textStyle.merge(TextStyle(color = textColor))
        )
    }

    @Composable
    fun Caption1Strong(
        text: String,
        modifier: Modifier = Modifier,
        labelTokens: LabelTokens? = null
    ) {
        val tokens = labelTokens
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Label] as LabelTokens
        val labelInfo = LabelInfo(TypographyTokens.Caption1Strong)
        val textStyle = tokens.typography(labelInfo = labelInfo)
        val textColor = tokens.textColor(labelInfo = labelInfo)
        BasicText(
            modifier = modifier,
            text = text,
            style = textStyle.merge(TextStyle(color = textColor))
        )
    }

    @Composable
    fun Caption1(
        text: String,
        modifier: Modifier = Modifier,
        labelTokens: LabelTokens? = null
    ) {
        val tokens = labelTokens
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Label] as LabelTokens
        val labelInfo = LabelInfo(TypographyTokens.Caption1)
        val textStyle = tokens.typography(labelInfo = labelInfo)
        val textColor = tokens.textColor(labelInfo = labelInfo)
        BasicText(
            modifier = modifier,
            text = text,
            style = textStyle.merge(TextStyle(color = textColor))
        )
    }

    @Composable
    fun Caption2(
        text: String,
        modifier: Modifier = Modifier,
        labelTokens: LabelTokens? = null
    ) {
        val tokens = labelTokens
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Label] as LabelTokens
        val labelInfo = LabelInfo(TypographyTokens.Caption2)
        val textStyle = tokens.typography(labelInfo = labelInfo)
        val textColor = tokens.textColor(labelInfo = labelInfo)
        BasicText(
            modifier = modifier,
            text = text,
            style = textStyle.merge(TextStyle(color = textColor))
        )
    }
}