import com.sun.net.httpserver.Request

abstract class User(id: Int, name: String, email: String, phoneNumber: String, address: String, passwd: String, userName: String) {
    private val _id = id
    private val _name = name
    private var _email = email
    private var _phone = phoneNumber
    private var _address = address
    private var _passwd = passwd
    private var _userName = userName
    protected var accounts = mutableListOf<Account>()
    protected var existingIds = mutableListOf<Int>()
    protected var requestedAccounts = mutableListOf<Account>()
    private var _loggedIn = false

    fun login(username: String, password: String) {
        if (username == _userName && password == _passwd)  {
            println("Logged in as $username")
            _loggedIn = true
        }
        else {
            println("Wrong username or password. Please try again later.")
            _loggedIn = false
        }
    }

    fun getIdNum(): Int {
        return _id
    }

    fun logoutOption() {
        println("Are you sure you wish to logout?(y/n)")
        val option = readln()
        if (option == "y") {
            _loggedIn = false // logged out.
            return
        }
        return // remain logged in.
    }

    fun logout() {
        _loggedIn = false
    }

    fun loginCheck(): Boolean {
        return _loggedIn
    }

    open fun newAccountRequest(accountType: String) {

    }

    fun displayAccounts() {
        /*Called in Main.kt to view all approved accounts.*/
        if (_loggedIn) {
            println("Accounts")
            for (account in accounts) {
                if (account.getIfApproved()) {
                    println("Account: " + account.getAccountType() + " " + account.returnAccountNumber())
                    println("Balance: " + account.returnBalance())
                    println("")
                }
            }
        }
    }

    fun getAccount(accountId: Int): Account? {
        return accounts.find { it.returnAccountNumber() == accountId }
    }

    fun getRequests(): List<Account> {
        return requestedAccounts
    }

    open fun getUserType(): String {
        return "Account"
    }

    fun getUserName(): String {
        return _userName
    }

    fun getPassword(): String {
        return _passwd
    }

    open fun viewProfile() {

        println("Profile ID: $_id")
        println("Name: $_name")
        println("UserName: $_userName")
        println("Email: $_email")
        println("Phone: $_phone")
        println("Address: $_address\n")

    }
}