/*
 * Sluice - 网盘 / 视频分享链接解析与高速下载
 *
 * ColorOS 17 的边缘渐变蒙层。
 *
 * 悬浮底栏/顶栏之下不留死白：内容滑到栏位下方，由一层「透明 → 页面底色」的竖向渐变淡出，
 * 读起来像内容被栏位挡住并逐渐隐去，而不是被一条硬边切断。
 * 用渐变而不是实时模糊：实时模糊要么需要把内容复制进离屏层（大列表会掉帧），
 * 要么得靠 API 34+ 的浮动渲染器；渐变在 31~37 全版本表现一致且零额外开销。
 */

package com.sluice.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itos.coui.theme.CouiTheme

/** 底部渐变蒙层：从透明淡到页面底色，压在悬浮底栏下方的内容之上。 */
@Composable
fun CouiBottomFadeScrim(
    modifier: Modifier = Modifier,
    height: Dp = 132.dp,
) {
    val base = CouiTheme.colorScheme.background
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(
                Brush.verticalGradient(
                    0f to base.copy(alpha = 0f),
                    0.55f to base.copy(alpha = 0.72f),
                    1f to base,
                )
            )
    )
}

/** 顶部渐变蒙层：状态栏/顶栏与内容交界处的淡出（侧边内容滚动时用）。 */
@Composable
fun CouiTopFadeScrim(
    modifier: Modifier = Modifier,
    height: Dp = 72.dp,
) {
    val base = CouiTheme.colorScheme.background
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(
                Brush.verticalGradient(
                    0f to base,
                    0.6f to base.copy(alpha = 0.6f),
                    1f to base.copy(alpha = 0f),
                )
            )
    )
}