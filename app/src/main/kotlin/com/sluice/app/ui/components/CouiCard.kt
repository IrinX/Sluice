/*
 * Sluice - 网盘 / 视频分享链接解析与高速下载
 *
 * COUI 卡片适配层。
 *
 * 应用里 87 处 Card 用的是 Material3 的参数形态（shape / colors / elevation / border）。
 * 这里保持签名兼容，外观换成 COUI：容器色取调用方显式指定的 containerColor（保住选中态、
 * 错误态这类语义），没指定时用 COUI 的 card 角色；形状默认沿用调用方传入的 shape。
 */

package com.sluice.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import com.itos.coui.theme.CouiTheme

/** 与 Material3 `Card` 参数兼容的 COUI 卡片。 */
@Composable
fun CouiCardCompat(
    modifier: Modifier = Modifier,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(),
    elevation: CardElevation = CardDefaults.cardElevation(),
    border: BorderStroke? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    // 调用方显式给了容器色就用它（选中/错误等语义色不能被主题覆盖），否则走 COUI 的 card 角色
    val container = colors.containerColor
    val resolved = if (container == CardDefaults.cardColors().containerColor) {
        CouiTheme.colorScheme.card
    } else {
        container
    }
    Surface(
        modifier = modifier,
        shape = shape,
        color = resolved,
        border = border,
        content = content,
    )
}