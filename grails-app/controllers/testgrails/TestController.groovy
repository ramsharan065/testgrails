package testgrails

import grails.converters.JSON

class TestController {

    def index() {
        Test test = new Test(name: "n")
        render test.save() as JSON
    }

    def get() {
//        def all = Test.findAll()
//        println all.size()
        render Test.count()
    }

    def save() {
        def millis = System.currentTimeMillis()
        (1..5000).each { counter ->
            Test person = new Test(name : "t")
            person.save()
        }
        def millis1 = System.currentTimeMillis()
        println "single each time"
        render millis1 - millis
    }

    def save2() {
        def millis = System.currentTimeMillis()
        (1..5000).each { counter ->
            Test person = new Test(name : "t")
            person.save(flush:true)
        }
        def millis1 = System.currentTimeMillis()
        println "single each time"
        render millis1 - millis
    }

    def bulk() {
        def millis = System.currentTimeMillis()
        List<Test> l = new ArrayList()
        (1..50000).each { counter ->
            Test person = new Test(name: "t")
//            person.save()
            l.add(person)
            if (l.size() > 50) {
//                Test.saveAll(l)
                Test.withTransaction {
                    l.each {
                        it.save()
                    }
                    l = new ArrayList<>()
                }
            }
        }
//        Test.saveAll(l)
        Test.withTransaction {
            l.each {
                it.save()
            }
        }
        def millis1 = System.currentTimeMillis()
        println "bulk"
        render millis1 - millis
    }

    def sessionFactory

    def bulk1() {
        def millis = System.currentTimeMillis()
        testService.method3()
        def millis1 = System.currentTimeMillis()
        println "bulk1"
        println millis1 - millis
        render millis1 - millis
    }

    def testService

    def bulk2() {
        def millis = System.currentTimeMillis()
        testService.method1()
        def millis1 = System.currentTimeMillis()
        println "bulk" + (millis1 - millis)
        render millis1 - millis
    }

    def bulk3() {
        def millis = System.currentTimeMillis()
        testService.method2()
        def millis1 = System.currentTimeMillis()
        println "bulk" + (millis1 - millis)
        render millis1 - millis
    }

    def test() {
        def sample = ["test5555", "test9999", "test9837"]
        def a = []
        for (int i = 0; i < 10000; i++) {
            a.add("test" + i)
        }
        def b = a.collectEntries {
            [(it) : true]
        }
        //first ma list wala
        def millis1 = System.currentTimeMillis()
        a.each {
            a.contains(it)
        }
        def millis2 = System.currentTimeMillis()
        println "{millis2 - millis1} = ${millis2 - millis1}"
        //second ma map wala

        millis1 = System.currentTimeMillis()
        a.each {
            b.containsKey(it)
        }
        millis2 = System.currentTimeMillis()
        println "{millis2 - millis1} = ${millis2 - millis1}"
        render "done"
    }
}
