/*
 * Sluice - 网盘 / 视频分享链接解析与高速下载
 *
 * ColorOS 17 的按压形变 —— 直接复用 COUI 自己的 CouiPressMotion。
 *
 * 之前这里是手写的 animateFloatAsState(0.96)，观感与底栏差很远：
 * COUI 的 CouiPressMotion 会按节点面积与宽高比算目标缩放（0.85~0.97），
 * 并用它自己的 CouiSpring 释放，才有那种「按下去陷一点点、松手弹回来」的手感。
 * 现在统一走 library：rememberCouiPressMotion() + Modifier.couiPressMotion()。
 */

package com.sluice.app.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import com.itos.coui.motion.couiPressMotion
import com.itos.coui.motion.rememberCouiPressMotion

/**
 * ColorOS 17 按压形变。
 *
 * [interactionSource] 只为兼容既有调用点保留：COUI 的按压由它自己的 pointerInput 观察，
 * 不依赖 Material 的 InteractionSource。
 */
fun Modifier.couiSpringPress(
    interactionSource: MutableInteractionSource? = null,
    enabled: Boolean = true,
    pressedScale: Float = 0.96f, // 兼容旧签名，实际缩放由 CouiPressMotion 按尺寸决定
): Modifier = composed {
    val press = rememberCouiPressMotion()
    val scale by press.scale
    this
        .couiPressMotion(press = press, enabled = enabled, cancelOnConsumedMove = true)
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
}

/** 供不带自定义 interactionSource 的调用点使用：内部自建一份。 */
@Composable
fun rememberCouiPressSource(): MutableInteractionSource = remember { MutableInteractionSource() }
