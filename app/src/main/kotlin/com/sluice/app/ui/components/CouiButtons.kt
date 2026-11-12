/*
 * Sluice - 网盘 / 视频分享链接解析与高速下载
 *
 * COUI 按钮族适配层：Button / TextButton / IconButton。
 *
 * 应用里这三类按钮合计 798 处，参数形态是 Material3 的（onClick / enabled / shape / colors /
 * contentPadding / RowScope content）。COUI 没有独立的通用按钮组件（它的按钮只出现在
 * 弹窗与顶栏内部），所以这里按 COUI 的规范自建：
 * - 形状用 COUI 胶囊（CouiCapsuleShape）
 * - 配色取 COUI 的色彩角色（accent / onAccent / onSurfaceVariant）
 * - 反馈用 COUI 的按压缩放（按下 0.96，松开回弹），与 COUI 组件的手感一致
 * 调用方显式给了 colors 时优先用它的容器色，避免破坏危险操作这类语义色。
 */

package com.sluice.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.itos.coui.shape.CouiCapsuleShape
import com.itos.coui.theme.CouiTheme

private fun Modifier.couiPressScale(pressed: Boolean): Modifier =
    this.scale(if (pressed) 0.96f else 1f)

@Composable
private fun pressScale(interaction: MutableInteractionSource): Float {
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.96f else 1f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 900f),
        label = "coui-button-press",
    )
    return scale
}

/** 与 Material3 `Button` 参数兼容的 COUI 实心按钮。 */
@Composable
fun CouiButtonCompat(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = CouiCapsuleShape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: androidx.compose.foundation.layout.PaddingValues =
        androidx.compose.foundation.layout.PaddingValues(horizontal = 24.dp, vertical = 10.dp),
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val source = interactionSource ?: remember { MutableInteractionSource() }
    val scale = pressScale(source)
    val explicit = colors.containerColor
    val container = if (explicit == ButtonDefaults.buttonColors().containerColor && enabled) {
        CouiTheme.colorScheme.accent
    } else {
        explicit
    }
    val onContainer = if (explicit == ButtonDefaults.buttonColors().containerColor) {
        CouiTheme.colorScheme.onAccent
    } else {
        colors.contentColor
    }
    Surface(
        onClick = onClick,
        modifier = modifier.scale(scale),
        enabled = enabled,
        shape = shape,
        color = container,
        contentColor = onContainer,
        border = border,
        interactionSource = source,
    ) {
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.padding(contentPadding),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        ) { content() }
    }
}

/** 与 Material3 `TextButton` 参数兼容的 COUI 文本按钮。 */
@Composable
fun CouiTextButtonCompat(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = CouiCapsuleShape,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    contentPadding: androidx.compose.foundation.layout.PaddingValues =
        androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val source = interactionSource ?: remember { MutableInteractionSource() }
    val scale = pressScale(source)
    val tint = if (colors.contentColor == ButtonDefaults.textButtonColors().contentColor) {
        CouiTheme.colorScheme.accent
    } else {
        colors.contentColor
    }
    Surface(
        onClick = onClick,
        modifier = modifier.scale(scale),
        enabled = enabled,
        shape = shape,
        color = Color.Transparent,
        contentColor = tint,
        border = border,
        interactionSource = source,
    ) {
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.padding(contentPadding),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        ) { content() }
    }
}

/** 与 Material3 `IconButton` 参数兼容的 COUI 图标按钮（圆形、按压缩放）。 */
@Composable
fun CouiIconButtonCompat(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit,
) {
    val source = interactionSource ?: remember { MutableInteractionSource() }
    val scale = pressScale(source)
    val tint = if (colors.contentColor == IconButtonDefaults.iconButtonColors().contentColor) {
        CouiTheme.colorScheme.onSurface
    } else {
        colors.contentColor
    }
    Surface(
        onClick = onClick,
        modifier = modifier.size(48.dp).scale(scale),
        enabled = enabled,
        shape = CircleShape,
        color = Color.Transparent,
        contentColor = tint,
        interactionSource = source,
    ) {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier.size(48.dp),
            contentAlignment = androidx.compose.ui.Alignment.Center,
        ) {
            CompositionLocalProvider(LocalContentColor provides tint) { content() }
        }
    }
}