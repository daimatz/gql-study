
package net.daimatz.gql.study

import io.ktor.server.netty.EngineMain

class App(env: Env) {
    fun run(args: Array<String>) {
        EngineMain.main(args)
    }
}

fun main(args: Array<String>) {
    val dba = DbaImpl()
    val env = Env(dba)
    App(env).run(args)
}
