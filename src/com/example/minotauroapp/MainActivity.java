/**
 * This is a small demo labyrinth and We will make progress of it.
 * 
 * @autor Eduardo Telaya (@edutrul) luis.eduardo.telaya@gmail.com
 * 
 */

package com.example.minotauroapp;

import java.util.Iterator;
import java.util.LinkedList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnTouchListener {
	GridView gridView;
	LinkedList<Integer> listPositions = new LinkedList<Integer>();
	public static final String WALL = "*";
	public static final int BEGIN = 1;
	public static final int END = 34;
	public boolean isFinished;
	
	static final String[] numbers = new String[] { 
			"*", " ", "*", "*", "*", "*",
			"*", " ", "*", " ", " " ,"*",
			"*", " ", "*", " ", " ", "*",
			"*", " ", "*", " ", " ", "*",
			"*", " ", " ", " ", " ", "*",
			"*", "*", "*", "*", " ", "*",};
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 
		setContentView(R.layout.main_activity);
 
		gridView = (GridView) findViewById(R.id.gridView1);
 
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, numbers);
 
		gridView.setAdapter(adapter);
		gridView.setOnTouchListener(this);
 
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
			   Toast.makeText(getApplicationContext(),
				((TextView) v).getText(), Toast.LENGTH_SHORT).show();
			}
		});
 
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		 int position = 0;
		 int action = event.getActionMasked();
         float currentXPosition = event.getX();
         float currentYPosition = event.getY();
         if (action == MotionEvent.ACTION_MOVE) {
	         position = gridView.pointToPosition((int) currentXPosition, (int) currentYPosition);
	         if (position != GridView.INVALID_POSITION && !checkIfPositionIsTaken(position) && 
	             position == BEGIN) {
	        	 listPositions.addLast(position);
	        	 Toast.makeText(getApplicationContext(), "BEGIN", Toast.LENGTH_SHORT).show();
	         }
	         if (position != GridView.INVALID_POSITION && listPositions.size() > 0 && 
	             (listPositions.getLast() + 6 == position || listPositions.getLast() - 6 == position || 
	              listPositions.getLast() + 1 == position || listPositions.getLast() - 1 == position
	              ) && !checkIfPositionIsTaken(position)) {
	          	 fillGridBackground(position);
	         }
	         if (position != GridView.INVALID_POSITION && listPositions.size() > 0 &&  
	             listPositions.getLast() == END && !isFinished) {
		         Toast.makeText(getApplicationContext(), "FIN", Toast.LENGTH_SHORT).show();
		         isFinished = true;
		     }
         }
         return true;
		
//		return false;
	}
	
	public boolean checkIfPositionIsTaken(int position) {
		Iterator<Integer> iterator = listPositions.iterator();
		while (iterator.hasNext()) {
			int pos = iterator.next();
			if (pos == position) {
				return true;
			}
		}
		return false;
	}
	
	public void fillGridBackground(int position) {
		
        TextView tvCur = (TextView) gridView.getChildAt(position);
        if (tvCur.getText().toString() != WALL) {
       	   tvCur.setBackgroundColor(Color.RED);
       	   listPositions.addLast(position);
       	   Log.d("debug", "Key PASSED AROUND HERE: " + position);
        }
            
        
	}	
}
