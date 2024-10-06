import java.time.LocalDate
import kotlin.random.Random

class Loan(amount: Double) {
    private val _amount: Double = amount
    private val date = LocalDate.now()

    fun display() {
        println("Amount: $_amount")
        println("Date: $date")
    }

    fun getAmount(): Double {
        return _amount
    }
}

class Business(taxId: Int, id: Int, name: String, email: String, phoneNumber: String, address: String, passwd: String, username: String):
    User(id, name, email, phoneNumber, address, passwd, username) {
        val _taxId: Int = taxId
        private var approved: Boolean = false
        private var loans = mutableListOf<Loan>()

        override fun newAccountRequest(accountType: String) {
            /*Creates a new account to be added to a list in Main.kt of requested accounts to be approved by an Admin.*/
            var newId: Int
            do {
                newId = Random.nextInt(100000, 1000000)
            } while (newId in existingIds)

            // Variable to store the requested account
            val requestedAccount: Account = when (accountType) {
                "Business Checking" -> Checking(newId) // Create a Business Checking account
                "Business Saving" -> Saving(newId)     // Create a Business Saving account
                else -> throw IllegalArgumentException("Invalid account type: $accountType") // Throw exception
            }
            requestedAccounts.add(requestedAccount)
        }

        override fun getUserType(): String {
            return "Business"
        }

        fun getTaxId(): Int {
            return _taxId
        }

        fun requestBusinessLoan(amount:  Double) {
            /*Business Users can apply for a business Loan.*/
            if (loginCheck()) {
                val newLoan = Loan(amount)
                loans.add(newLoan)
            }

        }

        fun approveLoan(securityAccess: Int, loan: Loan, account: Account) {
            if (securityAccess > 0) {
                println("Loan approved.")
                approved = true
                account.deposit(loan.getAmount(), 1)
            } else {
                println("Unauthorized access!")
            }

        }


    }