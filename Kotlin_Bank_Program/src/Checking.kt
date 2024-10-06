class Checking(accountId:Int): Account(accountId) {
    private val overDraftLimit: Double = 10.00

    override fun getAccountType(): String {
        return "Checking."
    }

    override fun overDraftProtection(limit: Double): Boolean {
      return if (returnBalance() <= overDraftLimit) {
          println("Over draft limit reached.")
          false
      } else {
          true
      }
    }
}