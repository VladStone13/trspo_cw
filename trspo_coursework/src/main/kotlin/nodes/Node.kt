package nodes

import blockchain.Block
import blockchain.Blockchain
import blockchain.Transaction

class Node {
    var blockchain: Blockchain
    var reputation: Int

    var stake: Int

    constructor() {
        blockchain = Blockchain()
        reputation = 5
        stake = 0
    }

    fun commitBlock(transaction: Transaction) {
        if(!blockchain.checkValid()) {
            return
        }

        blockchain.addBlock(transaction)

        if(!blockchain.checkValid()) {
            blockchain.rollback()
        }

    }

    fun addStake(stake:Int) {
        this.stake += stake
    }

}