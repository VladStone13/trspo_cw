package blockchain

import java.security.MessageDigest
import java.security.Timestamp
import java.util.*

class Block {
    var data: Transaction
    var hash: String
    var previousHash: String
    var timestamp: Long

    constructor(data: Transaction, previousHash:String) {
        this.data = data
        this.previousHash = previousHash
        this.timestamp = System.currentTimeMillis()
        hash = calculateHash()
    }

    fun calculateHash(): String {
        return hashFunction(data.toString()+previousHash)
    }

    fun hashFunction(str: String): String {
        val bytes = str.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }
}