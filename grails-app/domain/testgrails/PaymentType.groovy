package testgrails

class PaymentType {

    String code

    static hasMany = [branches: Branch]
    static belongsTo = Branch
    static constraints = {
    }
}
