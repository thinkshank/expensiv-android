package com.example.expensive.regular;

import com.example.expensiv.db.BankDatasource;
import com.example.expensiv.db.MySqlLiteHelper;
import com.example.expensiv.db.SqlLiteColumn;

import junit.framework.TestCase;

public class TestMe extends TestCase {

	public TestMe(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testColumnCriteriaSQL()
	{
		SqlLiteColumn c = new SqlLiteColumn(MySqlLiteHelper.BANK_ID, "VARCHAR", true, true, true);
		String sql = c.getSQL();
		assertNotNull(sql);
		assertNotSame(sql, "null");
		System.out.println(c.getSQL());
		
	}
	
	public void testCreateTableSQL(){
		BankDatasource bankds = new BankDatasource();
		System.out.println(bankds.getCreateStatement());		
	}
	
	
}
