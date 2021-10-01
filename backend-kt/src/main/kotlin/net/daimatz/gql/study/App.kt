
package net.daimatz.gql.study

class App(env: Env) {
    fun run() {
        println("Hello, world")
    }
}

fun main(args: Array<String>) {
    val dba = DbaImpl()
    val env = Env(dba)
    App(env).run()
}
