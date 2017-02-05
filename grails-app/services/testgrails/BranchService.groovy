package testgrails

import grails.transaction.Transactional

@Transactional
class BranchService {

    def save(String name, Bank bank) {
        Branch branch = Branch.findByNameAndBank(name, bank)
        if (!branch) {
            branch = new Branch(name: name, bank: bank)
            branch.save()
        }
        return branch
    }

    def saveNew(String name, Bank bank) {
        def branch = new Branch(name: name, bank: bank)
        branch.save()
    }

    def saveAll(List<String> names, Bank bank, PaymentType paymentType) {
        def millis1 = System.currentTimeMillis()
        def branches = Branch.findAllByNameInListAndBank(names, bank)
        def millis2 = System.currentTimeMillis()
        println "fetch1 time : {millis2 - millis1} = ${millis2 - millis1}"
        millis1 = System.currentTimeMillis()
        def branchesWithGivenPaymentType = []
        branchesWithGivenPaymentType = Branch.createCriteria().list {
            eq("bank", bank)
            paymentTypes{
                eq("code", paymentType.code)
            }
            'in'("name", names)
        }
        millis2 = System.currentTimeMillis()
        println "fetch2 time : {millis2 - millis1} = ${millis2 - millis1}"
        millis1 = System.currentTimeMillis()
        def newBranches = []
        def branchesWithoutGivenPaymentType = []
        def branchesMap = branches.collectEntries{
            [(it.name):it]
        }
        def branchesWithGivenPaymentTypeMap = branchesWithGivenPaymentType.collectEntries {
            [(it.name):it]
        }
        names.each { branchName ->
            if (!branchesMap.containsKey(branchName)) {
                def branch = new Branch()
                branch.name = branchName
                branch.bank = bank
                branch.paymentTypes = []
                branch.paymentTypes.add(paymentType)
                newBranches.add(branch)
            } else if (branchesMap.containsKey(branchName) && !branchesWithGivenPaymentTypeMap.containsKey(branchName)){
                def branchWithoutGivenPaymentType = branchesMap.get(branchName)
                branchWithoutGivenPaymentType.addToPaymentTypes(paymentType)
                branchWithoutGivenPaymentType.save()
                branchesWithoutGivenPaymentType.add(branchWithoutGivenPaymentType)
            }
        }
        Branch.saveAll(newBranches)
        branches.addAll(newBranches)
        branches.addAll(branchesWithoutGivenPaymentType)
        millis2 = System.currentTimeMillis()
        println "{millis2 - millis1} = ${millis2 - millis1}"
        return branches
    }

    def count() {
        Branch.count()
    }

    def clear() {
        Branch.deleteAll(Branch.getAll())
    }
}
