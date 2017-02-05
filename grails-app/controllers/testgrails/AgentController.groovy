package testgrails

import grails.converters.JSON

class AgentController {

    def agentService
    def paymentTypeService

    def index() {
        def millis1 = System.currentTimeMillis()
        agentService.saveAll()
        def millis2 = System.currentTimeMillis()

        render "done : " + (millis2 - millis1) / 1000.0 + " seconds"
    }

    def indexNew() {
        def millis1 = System.currentTimeMillis()
        agentService.saveAllNew()
        def millis2 = System.currentTimeMillis()

        render "new done : " + (millis2 - millis1) / 1000.0 + " seconds"
    }

    def indexAll() {
        def millis1 = System.currentTimeMillis()
        paymentTypeService.saveAll()
        agentService.saveAllBulk()
        def millis2 = System.currentTimeMillis()

        render "bulk done : " + (millis2 - millis1) / 1000.0 + " seconds"
    }

    def count() {
        render agentService.countInfo()
    }

    def clear() {
        render agentService.clear()
    }

}
