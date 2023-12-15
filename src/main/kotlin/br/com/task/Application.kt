package br.com.task

import br.com.task.di.databaseModule
import br.com.task.di.repositoryModule
import br.com.task.di.serviceModule
import br.com.task.plugins.configureHTTP
import br.com.task.plugins.configureMonitoring
import br.com.task.plugins.configureRouting
import br.com.task.plugins.configureSerialization
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(Koin) {
        modules(
            databaseModule,
            repositoryModule,
            serviceModule,
        )
    }
    configureMonitoring()
    configureSerialization()
    configureHTTP()
    configureRouting()
}
