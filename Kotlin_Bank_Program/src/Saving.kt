import java.time.LocalDateTime

//TODO figure out how withdrawing ticks the withdraw limit down
class Saving(accountId:Int): Account(accountId) {
    private var _withdrawLimit = 6
    private var _withdrawCount: Int = 0

    override fun getAccountType(): String {
        return "Saving."
    }

    override fun overDraftProtection(limit: Double): Boolean {
        return if (_withdrawLimit > limit) {
            println("Withdraw limit reached.")
            false
        } else {
            true
        }
    }

    override fun withdraw(amount: Double, destination: Int) {
//        super.withdraw(amount, destination)
        checkWithdrawReset()
        println("You have ${remainingWithdraw()} withdrawal(s) left. Are you sure you wish to continue?(y/n)")
        val option = readln()

        if (option == "n") {
            return
        }

        _withdrawCount += 1
        super.withdraw(amount, destination)
    }

    private fun checkWithdrawReset() {
        if (_withdrawCount > 0) {
            val currentDate = LocalDateTime.now()

            for (transaction in getTransactions()) {

                if (transaction.getDestinationId() == accountNumber) {
                    val transactionDate = transaction.getTransactionDate()

                    if (transactionDate.year < currentDate.year ||
                        (transactionDate.year == currentDate.year && transactionDate.monthValue < currentDate.monthValue)) {
                        _withdrawCount = 0
                    }

                    break
                }
            }
        }
    }


    fun remainingWithdraw():Int {
        return _withdrawLimit - _withdrawCount
    }

    fun applyInterest() {
        /*In this application, items only get updated when an action is performed.
        * Once integrated into an app, it will be updated live by the server.*/
        val lastTransaction = getTransactions().last()
        val currentDate = LocalDateTime.now()

        if (lastTransaction.getTransactionDate().monthValue < currentDate.monthValue ) {
            balance += (balance * interestRate)
        }
    }


}