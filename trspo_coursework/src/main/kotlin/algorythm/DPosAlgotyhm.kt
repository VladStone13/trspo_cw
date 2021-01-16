package algorythm

import blockchain.Blockchain
import blockchain.Transaction
import nodes.Node
import kotlin.random.Random

class DPosAlgotyhm {
    companion object {
        fun election(userStakes: MutableList<Int>, nodes: MutableList<Node>): MutableList<Node> {
            for(userStake in userStakes) {
                var randNumber:Int = Random.nextInt(0,  nodes.size-1)
                nodes[randNumber].addStake(userStake)
            }

            nodes.sortedBy { it.stake }
            nodes.reversed()

            var electionNodes: MutableList<Node> = emptyList<Node>().toMutableList()

            for(i in 0 until 3) {
                electionNodes.add(nodes[i])
            }

            for(node in nodes) {
                node.stake = 0
            }

            return electionNodes
        }

        fun commitTransactions(electionsNodes: MutableList<Node>, transactions: MutableList<Transaction>) {
            electionsNodes.shuffle()

            for(transaction in transactions) {
                electionsNodes[0].commitBlock(transaction);
            }
        }

        fun upgradeAllNodes(nodes: MutableList<Node>) {
            var lateBlockchain: Blockchain = nodes[0].blockchain

            for(node in nodes) {
                if(node.blockchain.getLatestBlock().timestamp > lateBlockchain.getLatestBlock().timestamp) {
                    lateBlockchain = node.blockchain
                }
            }

            for(node in nodes) {
                node.blockchain = lateBlockchain
            }
        }
    }
}