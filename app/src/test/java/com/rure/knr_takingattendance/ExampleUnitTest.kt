package com.rure.knr_takingattendance

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rure.knr_takingattendance.data.entities.Position
import org.junit.Test

import org.junit.Assert.*
import java.lang.reflect.Type

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun mapToString() {
        val map = mapOf(
            Position.Forward to true,
            Position.Defender to true,
            Position.Midfielder to true,
            Position.GoalKeeper to false
        )

        val str = map.toString()
        println("str: ${str}")

        val typeToken = object: TypeToken<Map<Position, Boolean>>() { } .type
        val map2 = Gson().fromJson<Map<Position, Boolean>>(str, typeToken)

        println("str: ${map2.toString()}")
        map2.forEach {
            println("${it.key}: ${it.value}")
        }
    }
}