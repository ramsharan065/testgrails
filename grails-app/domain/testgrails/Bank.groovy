package testgrails

class Bank {
    String name

    static hasMany = [branches:Branch]

    static constraints = {
    }
}
