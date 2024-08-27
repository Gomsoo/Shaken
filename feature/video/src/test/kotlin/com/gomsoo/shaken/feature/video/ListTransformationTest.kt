package com.gomsoo.shaken.feature.video

import org.junit.Test

class JsonSerializationTest {
    @Test
    fun listTest() {
        val fold = listOf(1, "some text", 2, "some text 2", "some text 3", "some text 4", 3)
            .fold(emptyList<Any>()) { acc, value ->
                val last = acc.lastOrNull()
                when {
                    last == null -> acc + value
                    last::class == value::class -> acc.subList(0, acc.lastIndex) + listOf(
                        listOf(last, value)
                    )

                    last is List<*> -> {
                        val map = last.mapNotNull { it }
                        val lastOfLast = map.lastOrNull()
                        when {
                            lastOfLast == null -> acc + value
                            lastOfLast::class == value::class -> acc.subList(
                                0,
                                acc.lastIndex
                            ) + listOf((map + value))

                            else -> acc + value
                        }
                    }

                    else -> acc + value
                }
            }
        println(fold)
    }

    @Test
    fun listTest2() {
        listOf(1, "some text", 2, "some text 2", "some text 3", "some text 4", 3)
            .asSequence()
            .withIndex()
            .groupBy { it.value::class }
            .flatMap { (_, indexedValues) ->
                indexedValues
                    .groupBy { it.index - indexedValues.indexOf(it) }
                    .values
                    .map { group ->
                        when {
                            group.size == 1 -> group.first()
                            else -> IndexedValue(group.first().index, group.map { it.value })
                        }
                    }
            }
            .sortedBy { it.index }
            .map { it.value }
            .toList()
            .let(::println)
    }
}
