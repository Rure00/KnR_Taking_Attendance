package com.rure.knr_takingattendance.data.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// Migration 시 이용하기
// https://dongsik93.github.io/til/2022/03/04/til-android-room-migration-study/
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // 예: 새로운 컬럼 추가
        db.execSQL("ALTER TABLE Member ADD COLUMN age INTEGER NOT NULL DEFAULT 0")
    }
}
