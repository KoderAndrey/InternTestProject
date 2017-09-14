package com.example.interntestproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<ObjectUserDataBase> mUsersList;
    public static String LOG_TAG = "LOG_TAG";
    private UsersOperationBD mUserOp;
    private ObjectUserDataBase mObDB;
    private Intent mIntent;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initalVariables();
        initUI();
    }

    void initalVariables() {
        mUsersList = new ArrayList<>();
        mUserOp = new UsersOperationBD(this);
        mUserOp.open();
        if (!mUserOp.isExist()) {
            initialData();
        }
        mUsersList = mUserOp.getAllUsers();
    }

    void initUI() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        RecyclerAdapterUsers recyclerAdapterUsers = new RecyclerAdapterUsers(mUsersList, this, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                long id = mUsersList.get(position).getUsId();
                goToUser(id);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setAdapter(recyclerAdapterUsers);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);
    }

    void initialData() {
        for (int i = 0; i < 5; i++) {
            mObDB = new ObjectUserDataBase();
            mObDB.setFirstName("USER");
            int c = i + 1;
            mObDB.setLastName(String.valueOf(c));
            mObDB.setDate("0" + c + "/" + "09/9" + c);
            mUserOp.addUser(mObDB);
        }
    }

    void goToUser(long id) {
        mIntent = new Intent(this, UserActivity.class);
        mIntent.putExtra("mode", "update");
        mIntent.putExtra("position", id);
        startActivity(mIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserOp.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_item) {
            mIntent = new Intent(this, UserActivity.class);
            mIntent.putExtra("mode", "add");
            startActivity(mIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        if (!mUserOp.isOpen()) {
            mUserOp.open();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        mUserOp.close();
        super.onPause();
    }
}
