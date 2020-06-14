open class DemoPluginExtension {
    var name = "ShuSheng007"
    var profession="码农"
    var hobby = "年轻漂亮的20多岁小姑娘"
}

class DemoPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        //将"info" extension 添加到project的ExtensionContainer中，便于使用这访问
        val extension = target.extensions.create<DemoPluginExtension>("info")
        target.task("hello") {
            this.doLast {
                println("Hello gradle by kotlin")
                println("${extension.profession } ${extension.name} 喜欢${extension.hobby}")
            }
        }
    }
}

//使用插件
apply<DemoPlugin>()

// 设置 extension的值
configure<DemoPluginExtension>{
    name="王二狗"
    hobby="牛翠花"
}