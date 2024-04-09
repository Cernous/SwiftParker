package com.example.swiftpark.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.swiftpark.MainActivity;
import com.example.swiftpark.R;
import com.example.swiftpark.ui.home.parkingInfoDialog;
import com.example.swiftpark.ui.parkingSpot.ParkingLotActivity;
import com.example.swiftpark.ui.spot.SpotFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ListView listview;
    private Button returnButton2;
    private SearchView search;
    private SpotFragment spotFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchactivity);

        search = findViewById(R.id.search);
        listview = findViewById(R.id.listview);
        returnButton2 = findViewById(R.id.returnButton2);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        ArrayList<String> lots = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.search_listitem, lots);
        listview.setAdapter(adapter);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lots.clear();
                for(DataSnapshot s : snapshot.child("parking_lots").getChildren()){
                    lots.add(s.getKey().replace("_", " "));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        search.requestFocus();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(getApplicationContext(), ParkingLotActivity.class);
                intent.putExtra("selectedLot", adapter.getItem(position).replace(" ", "_"));
                startActivity(intent);
            }
        });

        returnButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void filter(String query){
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) listview.getAdapter();
        adapter.getFilter().filter(query);
    }


}
