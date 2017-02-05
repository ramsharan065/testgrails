package testgrails

import grails.transaction.Transactional

@Transactional
class BankService {

    def save(String name) {
        Bank bank = Bank.findByName(name)
        if (!bank) {
            bank = new Bank(name: name)
            bank.save()
        }
        return bank
    }

    def saveNew(String name) {
        Bank bank = Bank.findByName(name)
        if (!bank) {
            bank = new Bank(name: name)
            bank.save()
        }
        return bank
    }

    def saveAll(List<String> names) {
        def banks = Bank.findAllByNameInList(names)
        def newBanks = []
        names.each { bankName ->
            if (!banks*.name.contains(bankName)) {
                newBanks.add(new Bank(name: bankName))
            }
        }
        Bank.saveAll(newBanks)
        banks.addAll(newBanks)
        return banks
    }

    def count() {
        Bank.count()
    }

    def clear() {
        Bank.deleteAll(Bank.getAll())
    }
}
