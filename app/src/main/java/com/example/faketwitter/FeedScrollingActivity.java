package com.example.faketwitter;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.faketwitter.databinding.ActivityFeedScrollingBinding;
import com.google.firebase.database.FirebaseDatabase;

public class FeedScrollingActivity extends AppCompatActivity {

    private ActivityFeedScrollingBinding binding;
    RecyclerView recyclerView;
    FeedAdapter feedAdapter;

    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFeedScrollingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle("Twitter Feed");

        FloatingActionButton fab = binding.fab;
        fab.setImageResource(R.drawable.person_icon);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfile();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<FeedModel> options =
                new FirebaseRecyclerOptions.Builder<FeedModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Posts"), FeedModel.class)
                        .build();

        feedAdapter = new FeedAdapter(options);
        recyclerView.setAdapter(feedAdapter);

        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButtonAdd);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        feedAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        feedAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                txtSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                txtSearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void txtSearch(String str){
        FirebaseRecyclerOptions<FeedModel> options =
                new FirebaseRecyclerOptions.Builder<FeedModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Posts").orderByChild("Name").startAt(str).endAt(str+"~"), FeedModel.class)
                        .build();

        feedAdapter = new FeedAdapter(options);
        feedAdapter.startListening();
        recyclerView.setAdapter(feedAdapter);
    }

    private void goToProfile(){
        startActivity(new Intent(FeedScrollingActivity.this, ProfileActivity.class));
    }
}