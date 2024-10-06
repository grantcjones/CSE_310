import java.time.LocalDateTime

public class Transaction(transactionId: Int, transactionType: String, amount: Double, originId: Int, destinationId: Int) {
    private val _transactionId: Int = transactionId
    private val _transactionType: String = transactionType
    private val _date: LocalDateTime = LocalDateTime.now()
    private val _amount: Double = amount
    private val _originId: Int = originId
    private val _destinationId: Int = destinationId

    fun displayTransaction() {
        println("Transaction Id: $_transactionId")
        println("Transaction Type: $_transactionType")
        println("Date: $_date")
        println("Amount: $_amount")
        println("Origin Id: $_originId")
        println("Destination Id: $_destinationId")
    }

    fun getTransactionDate(): LocalDateTime {
        return _date
    }

    fun getTransactionType(): String {
        return _transactionType
    }

    fun getAmount(): Double {
        return _amount
    }

    fun getTransactionId(): Int {
        return _transactionId
    }

    fun getDestinationId(): Int {
        return _destinationId
    }
}