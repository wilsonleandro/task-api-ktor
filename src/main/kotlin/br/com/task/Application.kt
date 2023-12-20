package br.com.task

import br.com.task.di.databaseModule
import br.com.task.di.repositoryModule
import br.com.task.di.serviceModule
import br.com.task.di.usecaseModule
import br.com.task.plugins.*
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
            usecaseModule,
        )
    }
    configureMonitoring()
    configureSerialization()
    configureHTTP()
    configureSecurity()
    configureRouting()
    configureStatusPage()
}
