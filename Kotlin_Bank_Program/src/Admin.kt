import kotlin.random.Random
import kotlin.system.exitProcess

class Admin(adminPassword: String, id: Int, name: String, email: String, phoneNumber: String, address: String, passwd: String, username: String):
    User(id, name, email, phoneNumber, address, passwd, username) {
        private val securityClearance: Int = 1
        private val _adminPassword: String = adminPassword

        override fun newAccountRequest(accountType: String) {
            // There may have been a better way to implement this with the other classes.
            return throw NotImplementedError()
        }

        override fun getUserType(): String {
            return "Admin"
        }

        fun newAdmin(userList: MutableList<User>) {
            var newId: Int
            do {
                newId = Random.nextInt(100000, 1000000)
            } while (newId in existingIds)

            println("Enter admin Name: ")
            val newName = readln()

            println("Enter email: ")
            val email = readln()

            println("Enter phone number: ")
            val phoneNumber = readln()

            println("Enter address: ")
            val address = readln()

            println("Enter username: ")
            val username = readln()

            println("Enter password: ")
            val password = readln()

            if (authCode()) {
                val newAdmin = Admin(_adminPassword, newId, newName, email, phoneNumber, address, password, username)
                userList.add(newAdmin)
            } else {
                throw Exception("Invalid password")
            }

        }

        fun newUser(usernames: List<String>, passwords: List<String>, id: Int) {
            println("Enter account type (Personal/Business): ")
            val accountType = readln()

            val newName: String = when (accountType) {
                "Personal" -> {
                    println("Enter client's full name: ")
                    readln()  // Capture the name
                }
                "Business" -> {
                    println("Enter Business name: ")
                    readln()  // Capture the business name
                }
                else -> {
                    println("Incorrect input. Please try again: ")
                    readln()  // Allow user to input again
                }
            }

            println("Enter email: ")
            val email = readln()

            println("Enter phone number: ")
            val phoneNumber = readln()

            println("Enter address: ")
            val address = readln()

            println("Enter desired username: ")
            var username = readln()
            do {
                println("Sorry, username is taken. Please try again: ")
                username = readln()
            } while (username in usernames)

            println("Enter desired password: ")
            var passwd = readln()
            do {
                println("Sorry, this password is unavailable. Please try again: ")
                passwd = readln()
            } while (passwd in passwords)

            when (accountType) {
                "Personal" -> {
                    println("Enter client's ssn: ")
                    val ssn = readln()
                    val newPersonal = Personal(ssn, id, newName, email, phoneNumber, address, passwd, username)}

                "Business" -> {
                    println("Enter tax Id: ")
                    val taxId = readln()
                }
            }
        }

        fun accountApproval(account: Account) {
            if (loginCheck()) {
                println("Enter Admin Code: ")
                val auth = readln()

                var count = 3

                do {
                    if (auth == _adminPassword) {
                        account.approveAccount(securityClearance)
                        count = -1
                    } else {
                        count -= 1
                    }
                } while (count > 0)

                if (count == 0) {
                    println("You have unsuccessfully attempted to authenticate the admin code 3 times. You will now be logged out.")
                    logout()
                }

            }
        }

    fun authCode(): Boolean {
        var count = 3

        do {
            println("Enter Admin Code: ")
            val userInput = readln()  // Changed variable name to avoid shadowing

            if (userInput == _adminPassword) {
                return true  // Return true immediately if the code matches
            } else {
                count -= 1
                if (count == 0) {
                    println("You have unsuccessfully attempted to authenticate the admin code 3 times. You will now be logged out.")
                    logout()  // Call to 'logout' function
                    return false
                } else {
                    println("Incorrect code. You have $count attempt(s) remaining.")
                }
            }
        } while (count > 0)

        return false  // Fallback in case of unexpected exit from the loop
    }


    fun getSecurityAccess(): Int {
            return securityClearance
        }

}