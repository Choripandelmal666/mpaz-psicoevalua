package com.example.proyectoeva2appmoviles.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseHelper(context: Context) : SQLiteOpenHelper(
    context.applicationContext,
    "psicoevalua.db",
    null,
    2
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE Usuario (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                usuario TEXT NOT NULL UNIQUE,
                contrasena TEXT NOT NULL
            )
            """.trimIndent()
        )
        val valores = ContentValues().apply {
            put("usuario", "psicologo")
            put("contrasena", "1234")
        }
        db.insert("Usuario", null, valores)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Aplicacion")
        db.execSQL("DROP TABLE IF EXISTS Usuario")
        onCreate(db)
    }

    fun ingresar(usuario: String, contrasena: String): Usuario? {
        val cursor = readableDatabase.rawQuery(
            "SELECT id, usuario, contrasena FROM Usuario WHERE usuario = ? AND contrasena = ?",
            arrayOf(usuario.trim(), contrasena)
        )
        cursor.use {
            if (!it.moveToFirst()) return null
            return Usuario(
                id = it.getLong(0),
                usuario = it.getString(1),
                contrasena = it.getString(2)
            )
        }
    }
}
