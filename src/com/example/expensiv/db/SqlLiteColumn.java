package com.example.expensiv.db;

public class SqlLiteColumn {
	private String name;
	private String type;
	private int size;
	private boolean primary;
	private boolean notnull;
	private boolean autoincrement;
	private String sql;
	
		
	public SqlLiteColumn(String name, String type) {
		setName(name);
		setType(type);
		buildSQL();
		
	}
	
	public SqlLiteColumn(String name, String type, boolean notnull) {
		setName(name);
		setType(type);
		setNotnull(true);
		buildSQL();
	}
	
	public SqlLiteColumn(String name, String type, boolean notnull, boolean primary) {
		setName(name);
		setType(type);
		setNotnull(true);
		setPrimary(primary);
		buildSQL();
	}
	
	public SqlLiteColumn(String name, String type, boolean notnull, boolean primary, boolean autoincrement) {
		setName(name);
		setType(type);
		setNotnull(true);
		setPrimary(primary);
		setAutoincrement(autoincrement);
		buildSQL();
	}
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public boolean isPrimary() {
		return primary;
	}
	public void setPrimary(boolean primary) {
		this.primary = primary;
	}
	public boolean isNotnull() {
		return notnull;
	}
	public void setNotnull(boolean notnull) {
		this.notnull = notnull;
	}
	public boolean isAutoincrement() {
		return autoincrement;
	}
	public void setAutoincrement(boolean autoincrement) {
		this.autoincrement = autoincrement;
	}
	
	protected void buildSQL(){
		String sql = "";
		if(name==null || type ==null){
			return;
		}
		
		sql += name + " ";
		
		sql += type + " ";
		if(isPrimary()){
			sql += " PRIMARY KEY "+ " ";
			if(isAutoincrement()){
				sql += " AUTOINCREMENT"+ " ";
			}
		}
		else if(isNotnull()){
			sql += " NOT NULL "+ " ";
		}
		
		this.sql = sql;
		
	}
	
	public String getSQL()
	{
		return this.sql;
	}
}
