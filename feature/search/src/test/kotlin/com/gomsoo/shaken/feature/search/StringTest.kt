package com.gomsoo.shaken.core.asset

import org.junit.Test

class JsonSerializationTest {

    @Test
    fun stringTest() {
        val searched = "Nightmare Mojito"
        val keyword = "moji"

        println(searched.split(keyword, ignoreCase = true))
        println(searched.split(keyword, ignoreCase = false))
        println(searched.split("Ni", ignoreCase = true))
    }
}
