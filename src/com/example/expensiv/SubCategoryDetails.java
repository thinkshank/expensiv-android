package com.example.expensiv;

import java.util.List;

import com.example.expensiv.db.Expenses;
import com.example.expensiv.db.ExpensesCategoryWise;
import com.example.expensiv.db.ExpensesDatasource;
import com.example.expensiv.db.ExpensesSubCategoryWise;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SubCategoryDetails extends Activity {
	
	private ExpensesDatasource datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);
        
        datasource = new ExpensesDatasource(this);
        datasource.open();
        Intent intent = getIntent();
        String category = "";
        if(intent.hasExtra("category")){
        	category = intent.getStringExtra("category");
        }
        
        List<ExpensesSubCategoryWise> values = datasource.getTotalSubCategoryWise(category);
        Log.e("shashank", "category query got results " + values.size());
        
        final ArrayAdapter<ExpensesSubCategoryWise> adapter = 
        		new ArrayAdapter<ExpensesSubCategoryWise>(this,
        								   android.R.layout.simple_list_item_1,
        								   values); 
        
    	//ArrayAdapter<String> adapter =
    	//new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myStringArray);
    	
        ListView listview = (ListView)findViewById(R.id.ListViewCategory);
        listview.setAdapter(adapter);
        
      
        
       
        //TextView totalExpense = (TextView)findViewById(R.id.totalExpense);
        //totalExpense.setText(" Total Expense - " + datasource.getTotal());
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_category_details, menu);
        return true;
    }
    
    @Override
    protected void onPause() {
    	datasource.close();
    	super.onPause();
    }
    
    @Override
    protected void onResume() {
    	datasource.open();
    	super.onResume();
    }
    
    
    //// xml on click handlers for menus ////
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.menu_viewAll:
        	showViewAll(item);
            return true;
        case R.id.menu_add:
        	addNewExpense(item);
            return true;  
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    public void addNewExpense(MenuItem menuItem) {
    	Intent intent = new Intent(this, AddNewExpense.class);
    	startActivity(intent);    	
	}
    
    public void showViewAll(MenuItem menuitem){
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
    }
    ////xml on click handlers for menus ////
    
    String eventToString(MotionEvent event){
    	return 
    	"[X:" + event.getX() + "]" +
    	"[Y:" + event.getY() + "]";
    
    }
    
    //// implement swipe ////
    
    //// implement swipe  ////
}
