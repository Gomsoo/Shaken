package com.gomsoo.shaken.core.extension

infix fun <A> Int.indexedWith(that: A): IndexedValue<A> = IndexedValue(this, that)
