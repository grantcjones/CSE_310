import kotlin.random.Random
import kotlin.system.exitProcess

class Personal(ssn: String, id: Int, name: String, email: String, phoneNumber: String, address: String, passwd: String, username: String):
    User(id, name, email, phoneNumber, address, passwd, username) {
        private val _ssn = ssn

    override fun newAccountRequest(accountType: String) {
        /*Creates a new account to be added to a list of requested accounts to be approved by an admin.*/
        var newId: Int
        do {
            newId = Random.nextInt(100000, 1000000)
        } while (newId in existingIds)

        // Variable to store the requested account
        val requestedAccount: Account = when (accountType) {
            "Checking" -> Checking(newId) // Create a Checking account
            "Saving" -> Saving(newId)     // Create a Saving account
            else -> throw IllegalArgumentException("Invalid account type: $accountType") // Throw exception
        }
        requestedAccounts.add(requestedAccount)
    }

    override fun getUserType(): String {
        return "Personal"
    }

}