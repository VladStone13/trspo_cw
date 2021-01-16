package main

import algorythm.DPosAlgotyhm
import blockchain.Block
import blockchain.Blockchain
import blockchain.Transaction
import main.user.User
import nodes.Node
import kotlin.random.Random


fun createUsers(): MutableList<User> {
    var users: MutableList<User> = mutableListOf<User>()
    users.add(User("Vlad", 100))
    users.add(User("Max", 50))
    users.add(User("Denis", 32))
    users.add(User("Dmitriy", 1000))
    users.add(User("Alina", 10))
    users.add(User("Alex", 200))
    users.add(User("Bogdan", 30))
    users.add(User("Diana", 125))

    return users
}

fun createNodes(): MutableList<Node> {
    var nodes:MutableList<Node> = mutableListOf<Node>()

    for (i in 0 until 10) {
        nodes.add(Node())
    }

    return nodes
}

fun createTransaction(users: MutableList<User>): Transaction {
    var randomUser1 = Random.nextInt(users.size)
    var randomUser2 = Random.nextInt(users.size)

    if(randomUser1 == randomUser2) {
        randomUser1 = 0
        randomUser2 = 1
    }

    return Transaction(users[randomUser1].name, users[randomUser2].name, Random.nextInt(users[randomUser1].coins/10))
}

fun getStakesFromUsers(users: MutableList<User>): MutableList<Int> {
    var stakes: MutableList<Int> = mutableListOf()
    for(user in users) {
        stakes.add(user.coins)
    }

    return stakes
}

fun updateUserInfo(blockchain: Blockchain, users: MutableList<User>) {
    for(block in blockchain.blocks) {
        for(user in users) {
            if(block.data.recieverName.equals(user.name)) {
                user.coins+=block.data.coins
            }
            else if(block.data.senderName.equals(user.name)) {
                user.coins-=block.data.coins
            }
        }
    }
}

fun commitTransactions(users: MutableList<User>, nodes: MutableList<Node>, transactions: MutableList<Transaction>) {
    var electionNodes: MutableList<Node> = DPosAlgotyhm.election(getStakesFromUsers(users), nodes)
    DPosAlgotyhm.commitTransactions(electionNodes, transactions)
    DPosAlgotyhm.upgradeAllNodes(nodes)
    updateUserInfo(nodes[0].blockchain, users)
}

fun main(args: Array<String>) {
    var users: MutableList<User> = createUsers()
    var nodes: MutableList<Node> = createNodes()

    var transactions: MutableList<Transaction> = mutableListOf()


    for (i in 1 until 3) {
        transactions.add(createTransaction(users))
        transactions.add(createTransaction(users))

        commitTransactions(users, nodes, transactions)

        println(transactions[0])
        println(transactions[1])
        println()

        for(user in users) {
            println(user)
        }
        println()
        transactions = mutableListOf()
    }

}