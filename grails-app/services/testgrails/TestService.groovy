package testgrails

import grails.transaction.Transactional
import org.hibernate.Session
import org.hibernate.Transaction

class TestService {

    @Transactional
    def method1() {
        int n = 50000
        List<Test> list = new ArrayList<>()
        for (int i = 0; i < n; i++) {
//            Test person = new Test(name: "n")
            Test person = new Test()
            person.setName("n")
            list.add(person)
            if (list.size() > 100) {
                subMethod1(list)
//                Test.saveAll(list)

                list = new ArrayList<>()
            }
        }
        subMethod1(list)
    }

//    @Transactional
    private def subMethod1(list) {
//        Test.saveAll(list)
//        def size = list.size()
//        for (int i = 0; i < size; i++) {
//            list.get(i).save()
//        }
//        list.each {
//            it.save()
//        }
    }

    def sessionFactory

    @Transactional
    public def method2() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        (1..50000).each { counter ->
            Test person = new Test()
            person.setName("n")
            session.save(person)
            if(counter.mod(100)==0) {
                //clear session and save records after every 100 records
                session.flush();
                session.clear();
            }
        }
        tx.commit();
        session.close();
    }

    @Transactional
    def method3() {
        List<Test> l = new ArrayList()
        (1..50000).each { counter ->
            Test person = new Test(name: "t")
            l.add(person)
            if (l.size() > 50) {
                Test.saveAll(l)
                l = new ArrayList<>()
            }
        }
        Test.saveAll(l)
    }
}
