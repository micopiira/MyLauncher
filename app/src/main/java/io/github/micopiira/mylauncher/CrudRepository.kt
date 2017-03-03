package io.github.micopiira.mylauncher

interface CrudRepository<T> {
    fun count(): Long
    fun findAll(): List<T>
}
