package blockchain

class Blockchain {
    var blocks: MutableList<Block>

    constructor() {
        blocks = mutableListOf<Block>()
        blocks.add(createGenesisBlock())
    }

    fun createGenesisBlock(): Block {
        val transaction = Transaction("0", "0", 0)
        return Block(transaction, "0")
    }

    fun getLatestBlock(): Block {
        return blocks[blocks.lastIndex]
    }

    fun addBlock(data: Transaction) {
        val block = Block(data, getLatestBlock().hash)
        blocks.add(block)
    }

    fun rollback() {
        blocks.removeLast()
    }

    fun checkValid(): Boolean {
        for (i in 1 until blocks.size-1) {
            var curBlock = blocks[i]
            var prevBlock = blocks[i-1]

            if(curBlock.hash != curBlock.calculateHash()) {
                return false
            }

            if(curBlock.previousHash != prevBlock.hash) {
                return false
            }
        }

        return true;
    }
}