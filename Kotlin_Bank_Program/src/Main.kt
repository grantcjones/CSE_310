import com.sun.net.httpserver.Request
import kotlin.random.Random

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

fun idGenerator(existingIds: MutableList<Int> = mutableListOf()): Int {
    var newId: Int

    do {
        newId = Random.nextInt(100000, 999999)
    } while (existingIds.contains(newId))

    existingIds.add(newId)  // Modify the mutable list
    return newId
}

fun persMenu(user: User): Int {

    println("\n1. Display Accounts")
    println("2. Show Account Transactions")
    println("3. Funds Transfer")
    println("4. Request Account")
    println("5. View Profile")
    println("6. Log Out.")

    val option = readln().toInt()
    return (option)
}

fun busMenu(user: User): Int {

    println("\n1. Display Accounts")
    println("2. Show Account Transactions")
    println("3. Funds Transfer")
    println("4. Request Account")
    println("5. View Profile")
    println("6. Request New Business Loan")
    println("7. View Tax Id")
    println("8. Log Out")

    val option = readln().toInt()
    return (option)
}

fun adminMenu(user: User): Int {
    println("\n1. New User")
    println("2. New Admin")
    println("3. Approve new accounts")
    println("4. View Profile")
    println("5. View all users")
    println("6. Undo transaction")
    println("7. Log Out")

    val option = readln().toInt()
    return (option)
}

fun fundsTransfer(user: User) {
    // Transfer Funds
    println("Enter deposit origin id: ")
    val originId = readln().toInt()

    println("Enter destination account id: ")
    val destinationId = readln().toInt()

    println("Enter transfer amount: ")
    val transferAmount = readln().toDouble()

    if (user.getAccount(originId)!!.returnBalance() >= transferAmount) {

        user.getAccount(originId)?.withdraw(transferAmount, destinationId)
        user.getAccount(destinationId)?.deposit(transferAmount, originId)

    } else {
        println("Insufficient Funds.")
    }
}

fun showAccountTransactions(user: User) {
    println("Enter Account Id: ")
    val accountId = readln().toInt()
    user.getAccount(accountId)!!.displayTransactions()
}

fun requestAccount(user: User) {
    println("What kind of account would you like to request? (Checking/Saving)")
    val accountTypeRequest = readln()
    user.newAccountRequest(accountTypeRequest)
}

fun loginUser(userList: List<User>, username: String, password: String): User? {
    return userList.find { it.getUserName() == username && it.getPassword() == password }
}

fun main() {
    val userList = mutableListOf<User>()
    val idList = mutableListOf<Int>()
    val requestList = mutableListOf<Int>()
    val primaryAdmin: Admin = Admin("default12854", id = idGenerator(idList), "Principal Administrator", "principalAd@kotlinBank.com", "520-910-5236", "707 S Surrey, Rexburg ID", "adminPassword", "Admin1")
    userList.add(primaryAdmin)

    var exit = false

    println("Welcome to Kotlin Bank.")

    while (!exit) {
        println("Please Log In: ")
        println("Username: ")
        val username = readln()

        println("Password: ")
        val password = readln()

        // Try to log the user in
        val loggedInUser: User? = loginUser(userList, username, password)

        if (loggedInUser != null) {
            println("User authenticated.\n")

            var loggedOut = false
            while (!loggedOut) {
                when (loggedInUser.getUserType()) {
                    "Personal" -> {
                        val option = persMenu(loggedInUser)

                        when (option) {
                            1 -> loggedInUser.displayAccounts()
                            2 -> showAccountTransactions(loggedInUser)
                            3 -> fundsTransfer(loggedInUser)
                            4 -> requestAccount(loggedInUser)
                            5 -> loggedInUser.viewProfile()
                            6 -> {
                                loggedInUser.logout()
                                loggedOut = true
                            }
                        }
                    }

                    "Business" -> {
                        val option = busMenu(loggedInUser)

                        when (option) {
                            1 -> loggedInUser.displayAccounts()
                            2 -> showAccountTransactions(loggedInUser)
                            3 -> fundsTransfer(loggedInUser)
                            4 -> requestAccount(loggedInUser)
                            5 -> loggedInUser.viewProfile()
                            6 -> {
                                if (loggedInUser is Business) {
                                    println("Enter desired loan amount: ")
                                    val loanAmount = readln().toDouble()
                                    loggedInUser.requestBusinessLoan(loanAmount)
                                }
                            }
                            7 -> {
                                if (loggedInUser is Business) {
                                    println("Tax ID: ${loggedInUser.getTaxId()}")
                                }
                            }
                            8 -> {
                                loggedInUser.logout()
                                loggedOut = true
                            }
                        }
                    }

                    "Admin" -> {
                        val option = adminMenu(loggedInUser)

                        when (option) {
                            1 -> {
                                println("Enter new account type (Personal/Business): ")
                                val newUserType = readln()

                                println("Name: ")
                                val name = readln()

                                println("UserName: ")
                                val newUsername = readln()

                                println("Email: ")
                                val email = readln()

                                println("Phone: ")
                                val phone = readln()

                                println("Address: ")
                                val address = readln()

                                println("Password: ")
                                val newPassword = readln()

                                if (newUserType == "Personal") {
                                    println("Social Security Number: ")
                                    val ssn = readln()

                                    val newPersonal = Personal(ssn, idGenerator(idList), name, email, phone, address, newPassword, newUsername)
                                    userList.add(newPersonal)

                                } else if (newUserType == "Business") {
                                    println("Tax ID: ")
                                    val taxId = readln().toInt()

                                    val newBusiness = Business(taxId, idGenerator(idList), name, email, phone, address, newPassword, newUsername)
                                    userList.add(newBusiness)
                                }
                            }
                            2 -> {
                                if (loggedInUser is Admin) {
                                    if (loggedInUser.authCode()) {
                                        loggedInUser.newAdmin(userList)
                                    }
                                }
                            }
                            3 -> {
                                if (loggedInUser is Admin) {
                                    println("Approve all accounts?(y/n)")
                                    val allApprove = readln()

                                    if (allApprove == "y") {
                                        for (user in userList) {
                                            for (account: Account in user.getRequests()) {
                                                account.approveAccount(loggedInUser.getSecurityAccess())
                                            }
                                        }
                                    }

                                    for (user in userList) {
                                        for (account: Account in user.getRequests()) {
                                            println("Approve Account?(y/n)")
                                            val approval = readln()

                                            if (approval == "y") {
                                                account.approveAccount(loggedInUser.getSecurityAccess())
                                            }
                                        }
                                    }
                                }
                            }
                            4 -> loggedInUser.viewProfile()
                            5 -> {
                                for (user in userList) {
                                    user.viewProfile()
                                }
                            }
                            6 -> {
                                println("Enter User Id: ")
                                val userId = readln().toInt()

                                val selectedUser = userList.find { it.getIdNum() == userId }
                                if (selectedUser != null) {
                                    println("Enter Account Id: ")
                                    val accountId = readln().toInt()

                                    val selectedAccount = selectedUser.getAccount(accountId)
                                    if (selectedAccount != null) {
                                        val transactionList = selectedAccount.getTransactions()

                                        println("Enter target Transaction Id: ")
                                        val targetTransactionId = readln().toInt()

                                        val removed = transactionList.removeIf { it.getTransactionId() == targetTransactionId }

                                        if (removed) {
                                            println("Transaction with ID $targetTransactionId has been removed.")
                                        } else {
                                            println("Transaction with ID $targetTransactionId not found.")
                                        }
                                    } else {
                                        println("Account with ID $accountId not found.")
                                    }
                                } else {
                                    println("User with ID $userId not found.")
                                }
                            }
                            7 -> {
                                loggedInUser.logout()
                                loggedOut = true
                            }
                        }
                    }
                }
            }
        } else {
            println("Login failed. Please try again.")
        }

        // After logging out, ask if the user wants to log in again or exit
        println("Do you want to log in again? (y/n)")
        val continueOption = readln()

        if (continueOption.toLowerCase() != "y") {
            exit = true
        }
    }

    println("Goodbye!")
}
