package com.itos.coui.topappbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection

/** 先折叠顶栏，正文到顶后再展开；连接只挂到实际滚动容器的一个祖先。 */
@Stable
class CouiTopAppBarScrollBehavior internal constructor(val state: CouiCollapsingAppBarState) {
    val nestedScrollConnection: NestedScrollConnection get() = state.nestedScrollConnection
}

/**
 * 宿主侧创建入口。
 * 原构造器是模块 internal，源码接入后由本工程复用，这里补一个公开工厂，
 * 避免把整个模块的内部构造器改成 public。
 */
@Composable
fun rememberCouiTopAppBarScrollBehavior(
    canScroll: () -> Boolean = { true },
): CouiTopAppBarScrollBehavior {
    val state = rememberCouiCollapsingAppBarState(canScroll)
    return remember(state) { CouiTopAppBarScrollBehavior(state) }
}
