import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.random.Random

abstract class Account(accountId:Int) {
    protected val accountNumber: Int = accountId
    protected var balance: Double = 0.0
    private var _transactions = mutableListOf<Transaction>()
    private var _existingIds = mutableListOf<Int>()
    protected var interestRate: Double = 0.0
    private var approved: Boolean = false

    fun approveAccount(securityAccess: Int) {
        if (securityAccess < 0)
            approved = true
    }

    private fun newTransaction(transactionType: String, amount: Double, destination: Int) {
        var newId: Int
        do {
            newId = Random.nextInt(100000, 1000000)
        } while (newId in _existingIds)
        _existingIds.add(newId)

        val newTransaction = Transaction(newId, transactionType, amount, accountNumber, destination)
        _transactions.add(newTransaction)
    }

    fun getIfApproved(): Boolean {
        return approved
    }

    fun returnAccountNumber(): Int {
        return accountNumber
    }

    abstract fun getAccountType(): String

    fun returnBalance():Double {
        return balance
    }

    fun setInterest(securityAccess: Int, newInterestRate:Double) {
        if (securityAccess > 0) {
            interestRate = newInterestRate
        }
    }

    abstract fun overDraftProtection(limit: Double): Boolean

    fun deposit(amount: Double, origin: Int) {
        balance += amount
        newTransaction("Deposit", amount, accountNumber)
    }

    open fun withdraw(amount: Double, destination: Int) {
        balance -= amount
        newTransaction("Withdrawal", amount, destination)
    }

    fun displayTransactions() {
        for (transaction in _transactions) {
            transaction.displayTransaction()
            print("")//TODO change to display fun from Transaction
        }
    }

    fun getTransactions(): MutableList<Transaction> {
        return _transactions
    }

    fun calculateInterest(interestRate: Double, futureDate: LocalDate): Double {
        val today = LocalDate.now()

        // Check if the input date is in the future
        if (futureDate.isEqual(today) || futureDate.isBefore(today)) {
            throw IllegalArgumentException("The future date must be later than today's date.")
        }

        // Calculate the number of days between today and the future date
        val daysBetween = ChronoUnit.DAYS.between(today, futureDate).toDouble()

        // Assuming the interest is calculated based on a yearly interest rate
        val yearsBetween = daysBetween / 365.0

        // Calculate the interest
        val interest = balance * interestRate * yearsBetween
        return interest
    }
}