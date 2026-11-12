/*
 * Sluice - 网盘 / 视频分享链接解析与高速下载
 *
 * ColorOS 17 的「q 弹」按压形变。
 *
 * COUI 的底座控件（导航栏、顶栏按钮）按下时会有轻微回弹的缩放；这里把同一套手感
 * 抽成 Modifier，供列表行、卡片、标签等自定义可点击区域复用：
 * 按下缩到 0.96，松手用低阻尼弹簧回弹（dampingRatio 0.55 / stiffness 800），
 * 观感与底栏一致。
 */

package com.sluice.app.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer

/** ColorOS 17 风格按压形变：按下 0.96、松手弹簧回弹。 */
fun Modifier.couiSpringPress(
    interactionSource: MutableInteractionSource,
    enabled: Boolean = true,
    pressedScale: Float = 0.96f,
): Modifier = composed {
    val pressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed && enabled) pressedScale else 1f,
        animationSpec = spring(
            dampingRatio = 0.55f,
            stiffness = Spring.StiffnessMediumLow * 3.2f,
        ),
        label = "coui-spring-press",
    )
    graphicsLayer {
        scaleX = scale
        scaleY = scale
    }
}

/** 供不带自定义 interactionSource 的调用点使用：内部自建一份。 */
@Composable
fun rememberCouiPressSource(): MutableInteractionSource = remember { MutableInteractionSource() }