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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CategoryDetails extends Activity {
	
	private ExpensesDatasource datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);
        
        datasource = new ExpensesDatasource(this);
        datasource.open();
        
        List<ExpensesCategoryWise> values = datasource.getTotalCategoryWise();
        Log.e("shashank", "category query got results " + values.size());
        
        final ArrayAdapter<ExpensesCategoryWise> adapter = 
        		new ArrayAdapter<ExpensesCategoryWise>(this,
        								   android.R.layout.simple_list_item_1,
        								   values); 
        
    	//ArrayAdapter<String> adapter =
    	//new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myStringArray);
    	
        ListView listview = (ListView)findViewById(R.id.ListViewCategory);
        listview.setAdapter(adapter);
        
        
        
        listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
				Toast.makeText(CategoryDetails.this, "Swipe from Left To Right on a selection for details", Toast.LENGTH_LONG).show();
				String category = ((ExpensesCategoryWise)adapter.getItem(position)).getCategory(); 
				Intent intent = new Intent(CategoryDetails.this, SubCategoryDetails.class);				
				intent.putExtra("category", category);
				startActivity(intent);
				
			}
		});
        
        
        
        
       final GestureDetector gestureDetector = 
    		   new GestureDetector(new MyGestureDetector(this));
       View.OnTouchListener gestureListener = 
    		   new View.OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if(gestureDetector.onTouchEvent(event)){
						
						return true;
					}
					return false;
				}
			};
        listview.setOnTouchListener(gestureListener);
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
