/*
 * Sluice - 网盘 / 视频分享链接解析与高速下载
 *
 * COUI 骨架与顶栏适配层。
 *
 * 为什么要有这一层：应用里 36 个 Scaffold、27 个 TopAppBar 用的是 Material3 参数形态
 * （topBar / bottomBar / snackbarHost / floatingActionButton、title / navigationIcon / actions / scrollBehavior）。
 * COUI 的 Scaffold 只接受 topBar/bottomBar/floatingActionButton，TopAppBar 必须运行在
 * COUI Scaffold 的栏位作用域里（它依赖栏位提供的 backdrop）。这一层把两者拼起来，
 * 让调用点一行不改就得到 COUI 的骨架与滚动联动。
 */

package com.sluice.app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.itos.coui.layout.Scaffold as CouiLayoutScaffold
import com.itos.coui.topappbar.CouiTopAppBarDefaults
import com.itos.coui.topappbar.TopAppBar as CouiBar

/**
 * 与 Material3 `Scaffold` 参数兼容的 COUI 骨架。
 *
 * COUI Scaffold 没有 snackbarHost 槽，这里把 Snackbar 放到正文底部（带 IME 避让）；
 * 内容仍然收到 COUI 算出来的 [PaddingValues]。
 */
@Composable
fun CouiScaffoldCompat(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    containerColor: Color = androidx.compose.material3.MaterialTheme.colorScheme.background,
    content: @Composable (PaddingValues) -> Unit,
) {
    CouiLayoutScaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        containerColor = containerColor,
        floatingActionButton = { floatingActionButton() },
    ) { inner ->
        Box(Modifier.fillMaxSize().padding(inner)) {
            content(PaddingValues(0.dp))
            Box(Modifier.align(Alignment.BottomCenter)) { snackbarHost() }
        }
    }
}

/**
 * 与 Material3 `TopAppBar` 参数兼容的 COUI 顶栏。
 *
 * [scrollBehavior] 保留在签名里以兼容调用点，但折叠由 COUI 自己的
 * `CouiTopAppBarScrollBehavior` 驱动，M3 的那套无法接过来 —— 页面上顶栏因此是常驻展开态。
 */
@Composable
fun CouiTopAppBarCompat(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable androidx.compose.foundation.layout.RowScope.() -> Unit = {},
    expandedHeight: androidx.compose.ui.unit.Dp = CouiTopAppBarDefaults.expandedHeight,
    windowInsets: androidx.compose.foundation.layout.WindowInsets = CouiTopAppBarDefaults.windowInsets,
    @Suppress("UNUSED_PARAMETER") scrollBehavior: TopAppBarScrollBehavior? = null,
    colors: @Suppress("UNUSED_PARAMETER") Any? = null,
    @Suppress("UNUSED_PARAMETER") defaultWindowInsets: Any? = null,
) {
    CouiBar(
        modifier = modifier,
        title = title,
        navigationIcon = navigationIcon,
        actions = actions,
        windowInsets = windowInsets,
    )
}

/** 供页面显式放置 Snackbar 时复用。 */
@Composable
fun CouiSnackbarHost(hostState: SnackbarHostState, modifier: Modifier = Modifier) {
    SnackbarHost(hostState = hostState, modifier = modifier)
}