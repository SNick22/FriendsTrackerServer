package com.example.cache

object AuthCodesCache {

    private val cache: HashMap<String, Int> = HashMap()

    fun putCode(phoneNumber: String, code: Int) {
        cache[phoneNumber] = code
    }

    fun getCode(phoneNumber: String): Int? {
        return cache[phoneNumber]
    }

    fun deleteCode(phoneNumber: String) {
        cache.remove(phoneNumber)
    }
}