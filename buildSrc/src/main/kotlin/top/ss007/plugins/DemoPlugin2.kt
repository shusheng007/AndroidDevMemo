package top.ss007.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create

/**
 * Copyright (C) 2020 shusheng007
 * 完全享有此软件的著作权，违者必究
 *
 * @author       shusheng007
 * @modifier
 * @createDate   2020/6/14 15:41
 * @version      1.0
 * @description
 */
class DemoPlugin2 : Plugin<Project> {
    override fun apply(target: Project) {
        //将"info" extension 添加到project的ExtensionContainer中，便于使用这访问
        val extension = target.extensions.create<DemoPluginExtension2>("info")
        target.task("hello") {
            doLast {
                println("Hello gradle by kotlin")
                println("${extension.profession } ${extension.name} 喜欢${extension.hobby}")
            }
        }
    }
}