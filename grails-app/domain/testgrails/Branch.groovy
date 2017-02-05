package testgrails

class Branch {
    String name

    static belongsTo = [bank: Bank]
    static hasMany = [paymentTypes: PaymentType]
    static constraints = {
    }
}
