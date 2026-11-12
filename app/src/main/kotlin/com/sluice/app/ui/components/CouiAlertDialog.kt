/*
 * Sluice - 网盘 / 视频分享链接解析与高速下载
 *
 * COUI 风格的通用确认弹窗。
 *
 * 为什么要有这一层：应用里 79 处弹窗原本直接调 Material3 的 AlertDialog（可组合槽位：
 * icon / title / text / confirmButton / dismissButton）。COUI 自己的 Dialog 用的是
 * 「字符串 + CouiDialogAction 动作对象」，两者签名不兼容，逐点重写成本极高且容易漏。
 * 这里做一个签名兼容的适配层：调用点一行不改，外观改由 COUI 的色彩角色与形状绘制。
 */

package com.sluice.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.itos.coui.dialog.CouiDialogDefaults
import com.itos.coui.theme.CouiTheme

/**
 * 与 Material3 `AlertDialog` 参数一一对应的 COUI 弹窗。
 *
 * 外观取自 COUI：容器形状用 `CouiDialogDefaults.shape`，底色用 COUI 的 surface，
 * 标题/正文颜色与字号走 CouiTheme（因此跟着主题的深浅与取色变化）。
 */
@Composable
fun CouiAlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dismissButton: (@Composable () -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    title: (@Composable () -> Unit)? = null,
    text: (@Composable () -> Unit)? = null,
    properties: DialogProperties = DialogProperties(),
) {
    Dialog(onDismissRequest = onDismissRequest, properties = properties) {
        Surface(
            modifier = modifier.widthIn(min = 280.dp, max = 560.dp),
            shape = CouiDialogDefaults.shape,
            color = CouiTheme.colorScheme.container,
            tonalElevation = 0.dp,
            shadowElevation = 0.dp,
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                if (icon != null) {
                    icon()
                    Spacer(Modifier.height(16.dp))
                }
                if (title != null) {
                    androidx.compose.runtime.CompositionLocalProvider(
                        androidx.compose.material3.LocalContentColor provides CouiTheme.colorScheme.onSurface
                    ) {
                        title()
                    }
                    Spacer(Modifier.height(12.dp))
                }
                if (text != null) {
                    androidx.compose.runtime.CompositionLocalProvider(
                        androidx.compose.material3.LocalContentColor provides CouiTheme.colorScheme.onSurfaceVariant,
                        androidx.compose.material3.LocalTextStyle provides CouiTheme.typography.body,
                    ) {
                        text()
                    }
                    Spacer(Modifier.height(8.dp))
                }
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    dismissButton?.invoke()
                    confirmButton()
                }
            }
        }
    }
}

/** 供调用点直接复用的标题样式（COUI 标题档位）。 */
@Composable
fun CouiDialogTitle(text: String) {
    androidx.compose.material3.Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = CouiTheme.colorScheme.onSurface,
    )
}