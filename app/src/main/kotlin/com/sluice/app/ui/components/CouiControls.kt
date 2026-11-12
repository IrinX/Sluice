/*
 * Sluice - 网盘 / 视频分享链接解析与高速下载
 *
 * COUI 控件适配层：开关 / 复选 / 单选 / 滑杆。
 *
 * 为什么要有这一层：调用点用的是 Material3 的参数形态（colors、onCheckedChange、onClick 等），
 * 而 COUI 的对应组件签名不同（Checkbox/RadioButton 不带点击回调、Switch 带拖拽与 loading 态）。
 * 这里保持调用点参数不变，内部渲染 COUI 控件，点击/选中语义由适配层补齐。
 */

package com.sluice.app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SwitchColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import com.itos.coui.checkbox.Checkbox as CouiCheckbox
import com.itos.coui.radiobutton.RadioButton as CouiRadioButton
import com.itos.coui.slider.Slider as CouiSlider
import com.itos.coui.switch.Switch as CouiSwitch

/** 与 Material3 `Switch` 参数兼容的 COUI 开关；[colors] 仅为兼容调用点保留，不参与绘制。 */
@Composable
fun CouiSwitchCompat(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: SwitchColors? = null,
    interactionSource: MutableInteractionSource? = null,
    thumbContent: (@Composable () -> Unit)? = null,
) {
    CouiSwitch(
        modifier = modifier,
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled,
        interactionSource = interactionSource,
    )
}

/** 与 Material3 `Checkbox` 参数兼容的 COUI 复选框：点击语义由适配层安装。 */
@Composable
fun CouiCheckboxCompat(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: CheckboxColors? = null,
) {
    val interaction = remember { MutableInteractionSource() }
    Box(
        modifier = modifier.then(
            if (onCheckedChange != null) {
                Modifier.toggleable(
                    value = checked,
                    enabled = enabled,
                    role = Role.Checkbox,
                    interactionSource = interaction,
                    indication = null,
                    onValueChange = onCheckedChange,
                )
            } else Modifier
        )
    ) {
        CouiCheckbox(checked = checked, enabled = enabled)
    }
}

/** 与 Material3 `RadioButton` 参数兼容的 COUI 单选：点击语义由适配层安装。 */
@Composable
fun CouiRadioButtonCompat(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: RadioButtonColors? = null,
) {
    val interaction = remember { MutableInteractionSource() }
    Box(
        modifier = modifier.then(
            if (onClick != null) {
                Modifier.selectable(
                    selected = selected,
                    enabled = enabled,
                    role = Role.RadioButton,
                    interactionSource = interaction,
                    indication = null,
                    onClick = onClick,
                )
            } else Modifier
        )
    ) {
        CouiRadioButton(selected = selected, enabled = enabled)
    }
}

/** 与 Material3 `Slider` 参数兼容的 COUI 滑杆。 */
@Composable
fun CouiSliderCompat(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    onValueChangeFinished: (() -> Unit)? = null,
    colors: SliderColors? = null,
) {
    CouiSlider(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        valueRange = valueRange,
        steps = steps,
        enabled = enabled,
        onValueChangeFinished = { onValueChangeFinished?.invoke() },
    )
}