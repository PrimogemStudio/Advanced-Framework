package com.primogemstudio.advancedfmk.kui.yaml.jvm

import kotlin.reflect.KClass

fun sigt(cls: KClass<*>): String = "L${cls.java.name.replace(".", "/")};"
fun sig(cls: KClass<*>): String = cls.java.name.replace(".", "/")
val INIT = "<init>"
val KT_KOLLECTIONS = "kotlin/collections/CollectionsKt"