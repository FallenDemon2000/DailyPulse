package com.petros.efthymiou.dailypulse.android.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import com.petros.efthymiou.dailypulse.db.DatabaseDriverFactory
import org.koin.dsl.module
import petros.efthymiou.dailypulse.db.DailyPulseDatabase

val databaseModule = module {
    single<SqlDriver> { DatabaseDriverFactory(get<Context>()).createDriver() }
    single<DailyPulseDatabase> { DailyPulseDatabase(get<SqlDriver>()) }
}
