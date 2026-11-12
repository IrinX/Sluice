/*
 * Sluice - 网盘 / 视频分享链接解析与高速下载
 *
 * COUI 底部弹层与浮动按钮适配层。
 *
 * 弹层：应用里 15 处 ModalBottomSheet，M3 的参数形态（sheetState / dragHandle / scrimColor…）
 * 与 COUI 的 Dialog(placement = Bottom) 不同。这里保留 M3 的拖动关闭行为（用户手感不变），
 * 外观换成 COUI 的容器色与形状。
 * 浮动按钮：保留 M3 的 FAB 尺寸与展开逻辑（CustomFabMenu 依赖这些行为），
 * 配色换成 COUI 的 accent / onAccent / 阴影。
 */

package com.sluice.app.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itos.coui.dialog.CouiDialogDefaults
import com.itos.coui.shape.CouiCapsuleShape
import com.itos.coui.theme.CouiTheme

/** 与 Material3 `ModalBottomSheet` 参数兼容的 COUI 风格底弹层（保留拖动关闭）。 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CouiModalBottomSheetCompat(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    shape: Shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
    containerColor: Color = CouiTheme.colorScheme.container,
    contentColor: Color = CouiTheme.colorScheme.onSurface,
    tonalElevation: Dp = 0.dp,
    scrimColor: Color = Color.Black.copy(alpha = 0.32f),
    dragHandle: @Composable (() -> Unit)? = null,
    sheetGesturesEnabled: Boolean = true,
    properties: androidx.compose.material3.ModalBottomSheetProperties =
        androidx.compose.material3.ModalBottomSheetProperties(),
    content: @Composable androidx.compose.foundation.layout.ColumnScope.() -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        scrimColor = scrimColor,
        dragHandle = dragHandle,
        sheetGesturesEnabled = sheetGesturesEnabled,
        properties = properties,
        content = content,
    )
}

/** 与 Material3 `FloatingActionButton` 参数兼容的 COUI 配色浮动按钮。 */
@Composable
fun CouiFabCompat(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CouiCapsuleShape,
    containerColor: Color = CouiTheme.colorScheme.accent,
    contentColor: Color = CouiTheme.colorScheme.onAccent,
    content: @Composable () -> Unit,
) {
    val pressSource = androidx.compose.runtime.remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier.couiSpringPress(pressSource),
        shape = shape,
        interactionSource = pressSource,
        containerColor = containerColor,
        contentColor = contentColor,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp,
            focusedElevation = 2.dp,
            hoveredElevation = 2.dp,
        ),
        content = content,
    )
}

@Composable
fun CouiSmallFabCompat(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CouiCapsuleShape,
    containerColor: Color = CouiTheme.colorScheme.accent,
    contentColor: Color = CouiTheme.colorScheme.onAccent,
    elevation: androidx.compose.material3.FloatingActionButtonElevation =
        FloatingActionButtonDefaults.elevation(defaultElevation = 2.dp, pressedElevation = 4.dp),
    content: @Composable () -> Unit,
) {
    val pressSource = androidx.compose.runtime.remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
    SmallFloatingActionButton(
        onClick = onClick,
        modifier = modifier.couiSpringPress(pressSource),
        shape = shape,
        interactionSource = pressSource,
        containerColor = containerColor,
        contentColor = contentColor,
        elevation = elevation,
        content = content,
    )
}

/** 供需要自定义宽度的页面复用：COUI 弹层形状。 */
val CouiSheetShape: Shape get() = CouiDialogDefaults.shape
/** 与 Material3 `ExtendedFloatingActionButton` 参数兼容的 COUI 展开式 FAB（带按压回弹）。 */
@Composable
fun CouiExtendedFabCompat(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CouiCapsuleShape,
    containerColor: Color = CouiTheme.colorScheme.accent,
    contentColor: Color = CouiTheme.colorScheme.onAccent,
    elevation: androidx.compose.material3.FloatingActionButtonElevation =
        FloatingActionButtonDefaults.elevation(defaultElevation = 2.dp, pressedElevation = 4.dp),
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
) {
    val pressSource = androidx.compose.runtime.remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
    androidx.compose.material3.ExtendedFloatingActionButton(
        onClick = onClick,
        modifier = modifier.couiSpringPress(pressSource),
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        elevation = elevation,
        interactionSource = pressSource,
        icon = icon,
        text = text,
    )
}
