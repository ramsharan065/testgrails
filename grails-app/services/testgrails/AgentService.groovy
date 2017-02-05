package testgrails

import grails.transaction.Transactional

@Transactional
class AgentService {

    def bankService
    def branchService
    def paymentTypeService

    def getAgents() {
        DataGenerator dataGenerator = new DataGenerator()
        return dataGenerator.getAgents(20, 1000)
    }

    def saveAll() {
        def agents = getAgents()
        agents.eachWithIndex { agent, index ->
            println "index = $index"
            save(agent)
        }
    }

    def save(Agent agent) {
        def bank = bankService.save(agent.getBankName())
        def branch = branchService.save(agent.getBranchName(), bank)
    }

    def saveAllNew() {
        def agents = getAgents()
        agents.eachWithIndex { agent, index ->
            println "index = $index"
            saveNew(agent)
        }
    }

    def saveNew(Agent agent) {
        def bank = bankService.saveNew(agent.getBankName())
        def branch = branchService.saveNew(agent.getBranchName(), bank)
    }

    def saveAllBulk() {
        def agents = getAgents()
        def agentsByBankName = agents.groupBy({ agent -> agent.bankName })
        def bankNames = agentsByBankName.keySet().asList()
        def banks = bankService.saveAll(bankNames)
        def paymentType = paymentTypeService.get("A")
        println "banks = ${banks.size()}"
        banks.each { bank ->
            println "bank.name = $bank.name"
            def branches = agentsByBankName.get(bank.name)
            println "branches = ${branches.size()}"
            def listOfBranches = branchService.saveAll(branches*.branchName, bank, paymentType)
            println "listOfBranches.size() = $listOfBranches.size()"
        }
    }

    def countInfo() {
        "bank : ${bankService.count()}, branch : ${branchService.count()}"
    }

    def clear() {
        branchService.clear()
        bankService.clear()
        "cleared"
    }
}
