package testgrails

import grails.transaction.Transactional

@Transactional
class PaymentTypeService {

    def saveAll() {
        if (!PaymentType.count()) {
            new PaymentType(code: "A").save()
            new PaymentType(code: "B").save()
            new PaymentType(code: "C").save()
        }
    }

    def get(String code) {
        PaymentType.findByCode(code)
    }
}
