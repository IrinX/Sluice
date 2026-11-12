/*
 * Sluice - 网盘 / 视频分享链接解析与高速下载
 *
 * COUI 进度指示器与筛选标签适配层。
 *
 * 进度：应用里 37 处 CircularProgressIndicator、1 处 LinearProgressIndicator，
 * 参数形态是 Material3 的（progress / modifier / color / trackColor / strokeWidth / strokeCap）。
 * 这里保持签名，配色默认换成 COUI 的 accent / outlineVariant。
 * 标签：13 处 FilterChip 集中在主题页的方案选择，COUI 只有 ChipTab，
 * 因此按 COUI 的形状与配色自建筛选标签。
 */

package com.sluice.app.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itos.coui.shape.CouiCapsuleShape
import com.itos.coui.theme.CouiTheme

/** 与 Material3 `CircularProgressIndicator` 参数兼容的 COUI 转圈。 */
@Composable
fun CouiCircularProgressCompat(
    modifier: Modifier = Modifier,
    color: Color = CouiTheme.colorScheme.accent,
    strokeWidth: Dp = 4.dp,
    trackColor: Color = CouiTheme.colorScheme.outlineVariant,
    strokeCap: StrokeCap = StrokeCap.Round,
    progress: (() -> Float)? = null,
) {
    if (progress != null) {
        CircularProgressIndicator(
            progress = progress,
            modifier = modifier,
            color = color,
            strokeWidth = strokeWidth,
            trackColor = trackColor,
            strokeCap = strokeCap,
        )
    } else {
        CircularProgressIndicator(
            modifier = modifier,
            color = color,
            strokeWidth = strokeWidth,
            trackColor = trackColor,
            strokeCap = strokeCap,
        )
    }
}

/** 与 Material3 `LinearProgressIndicator` 参数兼容的 COUI 进度条。 */
@Composable
fun CouiLinearProgressCompat(
    modifier: Modifier = Modifier,
    color: Color = CouiTheme.colorScheme.accent,
    trackColor: Color = CouiTheme.colorScheme.outlineVariant,
    strokeCap: StrokeCap = StrokeCap.Round,
    gapSize: Dp = 0.dp,
    drawStopIndicator: (() -> Unit)? = null,
    progress: (() -> Float)? = null,
) {
    if (progress != null) {
        LinearProgressIndicator(
            progress = progress,
            modifier = modifier,
            color = color,
            trackColor = trackColor,
            strokeCap = strokeCap,
        )
    } else {
        LinearProgressIndicator(
            modifier = modifier,
            color = color,
            trackColor = trackColor,
            strokeCap = strokeCap,
        )
    }
}

/** 与 Material3 `FilterChip` 参数兼容的 COUI 筛选标签。 */
@Composable
fun CouiFilterChipCompat(
    selected: Boolean,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    colors: androidx.compose.material3.SelectableChipColors = FilterChipDefaults.filterChipColors(),
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = CouiCapsuleShape,
        color = if (selected) CouiTheme.colorScheme.accent else CouiTheme.colorScheme.container,
        contentColor = if (selected) CouiTheme.colorScheme.onAccent else CouiTheme.colorScheme.onSurface,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(6.dp),
        ) {
            leadingIcon?.invoke()
            label()
            trailingIcon?.invoke()
        }
    }
}