package com.guny.hibernate.main;

import java.util.List;

import com.guny.hibernate.model.Address;
import com.guny.hibernate.model.Employee;
import com.guny.hibernate.util.HibernateUtil;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.stat.Statistics;

public class HibernateEHCacheMain {

	public static void main(String[] args) {
		System.out.println("Temp Dir: " + System.getProperty("java.io.tmpdir"));
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Statistics statistics = sessionFactory.getStatistics();
		System.out.println("Stats enabled: " + statistics.isStatisticsEnabled());
		statistics.setStatisticsEnabled(true);
		System.out.println("Stats enabled: " + statistics.isStatisticsEnabled());
		
		Session session = sessionFactory.openSession();
		Session otherSession = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Transaction otherTransaction = otherSession.beginTransaction();
		
		printStats(statistics, 0);
		
		/*Employee emp = (Employee) session.load(Employee.class, 1l);
		printData(emp, statistics, 1);
		emp = (Employee) session.load(Employee.class, 1l);
		printData(emp, statistics, 2);
		
		// clear 1st level cache
		session.evict(emp);
		emp = (Employee) session.load(Employee.class, 1l);
		printData(emp, statistics, 3);
		emp = (Employee) session.load(Employee.class, 1l);
		printData(emp, statistics, 4);
		emp = (Employee) otherSession.load(Employee.class, 1l);
		printData(emp, statistics, 5);*/
		System.out.println("1st query");
		Query query = session.createQuery("from Employee emp where emp.id = 1");
		query.setCacheable(true);
		List users = query.list();
		System.out.println("2nd query");
		Query otherQuery = otherSession.createQuery("from Employee emp where emp.id = 1");
		otherQuery.setCacheable(true);
		List otherUsers = otherQuery.list();
		
		transaction.commit();
		otherTransaction.commit();
		
		session.close();
		sessionFactory.close();

	}

	private static void printData(Employee emp, Statistics statistics, int count) {
		System.out.println(count + ":: Name: " + emp.getName() + "; ZIP: " + emp.getAddress().getZipcode());
		printStats(statistics, count);
	}

	private static void printStats(Statistics statistics, int i) {
		System.out.println("***** " + i + " *****");
		System.out.println("Fetch count: " + statistics.getEntityFetchCount());
		System.out.println("Second level hit count: " + statistics.getSecondLevelCacheHitCount());
		System.out.println("Second level miss count: " + statistics.getSecondLevelCacheMissCount());
		System.out.println("Second level put count: " + statistics.getSecondLevelCachePutCount());
	}

}
