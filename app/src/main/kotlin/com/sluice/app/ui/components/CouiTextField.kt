/*
 * Sluice - 网盘 / 视频分享链接解析与高速下载
 *
 * COUI 风格的单行/多行输入框适配层。
 *
 * 为什么要有这一层：应用里 61 处输入框用的是 Material3 的 OutlinedTextField，
 * 参数体系庞大（label / placeholder / leadingIcon / supportingText / keyboardOptions …）。
 * COUI 的输入组件 API 与之不同，逐点重写既慢又容易漏。这里保持 M3 的参数签名，
 * 把外观（描边、焦点色、圆角、容器色）换成 COUI 的色彩角色与形状，
 * 行为（输入、校验、键盘）完全不变。
 */

package com.sluice.app.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import com.itos.coui.shape.CouiCapsuleShape
import com.itos.coui.theme.CouiTheme

/** COUI 输入框默认配色：描边用 outlineVariant，焦点用 accent，容器用 container。 */
@Composable
fun couiTextFieldColors(
    error: Boolean = false,
): TextFieldColors = OutlinedTextFieldDefaults.colors(
    focusedTextColor = CouiTheme.colorScheme.onSurface,
    unfocusedTextColor = CouiTheme.colorScheme.onSurface,
    disabledTextColor = CouiTheme.colorScheme.onSurfaceVariant,
    focusedContainerColor = CouiTheme.colorScheme.container,
    unfocusedContainerColor = CouiTheme.colorScheme.container,
    disabledContainerColor = CouiTheme.colorScheme.container,
    errorContainerColor = CouiTheme.colorScheme.container,
    cursorColor = CouiTheme.colorScheme.accent,
    focusedBorderColor = if (error) CouiTheme.colorScheme.accent else CouiTheme.colorScheme.accent,
    unfocusedBorderColor = CouiTheme.colorScheme.outlineVariant,
    focusedLabelColor = CouiTheme.colorScheme.accent,
    unfocusedLabelColor = CouiTheme.colorScheme.onSurfaceVariant,
    focusedLeadingIconColor = CouiTheme.colorScheme.onSurfaceVariant,
    unfocusedLeadingIconColor = CouiTheme.colorScheme.onSurfaceVariant,
    focusedTrailingIconColor = CouiTheme.colorScheme.onSurfaceVariant,
    unfocusedTrailingIconColor = CouiTheme.colorScheme.onSurfaceVariant,
    focusedPlaceholderColor = CouiTheme.colorScheme.onSurfaceVariant,
    unfocusedPlaceholderColor = CouiTheme.colorScheme.onSurfaceVariant,
)

/** 与 Material3 `OutlinedTextField` 参数一致的输入框，外观走 COUI。 */
@Composable
fun CouiOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    prefix: (@Composable () -> Unit)? = null,
    suffix: (@Composable () -> Unit)? = null,
    supportingText: (@Composable () -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = CouiCapsuleShape,
    colors: TextFieldColors = couiTextFieldColors(isError),
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
    )
}