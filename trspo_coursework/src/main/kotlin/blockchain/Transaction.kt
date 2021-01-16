package blockchain

class Transaction {
    var senderName: String
    var recieverName: String

    var coins: Int

    constructor(senderName:String, recieverName:String, coins:Int) {
        this.recieverName = recieverName
        this.senderName = senderName
        this.coins = coins
    }

    override fun toString(): String {
        return senderName + " " + recieverName + " " + coins.toString()
    }
}