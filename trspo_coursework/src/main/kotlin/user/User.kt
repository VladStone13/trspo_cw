package main.user

class User {
    var name: String
    var coins: Int

    constructor(name: String, coins: Int) {
        this.name = name
        this.coins = coins
    }

    override fun toString(): String {
        return name + " " + coins.toString()
    }
}