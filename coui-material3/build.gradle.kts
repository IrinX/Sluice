/*
 * COUI-Kit 源码模块（本地接入）。原项目：https://github.com/ItosEO/COUI-Kit
 * 许可：MPL 2.0（见同目录 LICENSE / NOTICE）；本文件仅为去除发布相关插件后的构建配置。
 */
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.itos.coui.material3"
    compileSdk = 37
    defaultConfig {
        minSdk = 31
    }
    buildFeatures { compose = true }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    api(project(":coui"))
    api(platform("androidx.compose:compose-bom-alpha:2026.08.01"))
    api("androidx.compose.material3:material3")
}
