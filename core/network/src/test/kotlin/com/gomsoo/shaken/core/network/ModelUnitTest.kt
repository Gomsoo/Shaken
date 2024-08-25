package com.gomsoo.shaken.core.network

import org.junit.Test

class ModelUnitTest {

    @Test
    fun stringListZipTest() {
        listOf("1", "2", null, "4", "5")
            .zip(listOf("1", null, "3", "4", null))
            .forEach { println(it) }
    }
}
