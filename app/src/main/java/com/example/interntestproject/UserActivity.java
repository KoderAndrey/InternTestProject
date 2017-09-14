package com.example.interntestproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import static com.example.interntestproject.MainActivity.LOG_TAG;

public class UserActivity extends AppCompatActivity {
    private EditText mFName;
    private EditText mLName;
    private EditText mDate;
    private long mId;
    private String mMode;
    private Intent mIntent;
    private UsersOperationBD mUserOp;
    private ObjectUserDataBase mObjToUpdate;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initVariables();
        initUI();
    }

    void initUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mFName = (EditText) findViewById(R.id.first_name_ed);
        mLName = (EditText) findViewById(R.id.last_name_ed);
        mDate = (EditText) findViewById(R.id.birth_date_ed);
        if (mMode.equals("update")) {
            mId = mIntent.getLongExtra("position", 0);
            Log.d(LOG_TAG, "position " + mId);
            mObjToUpdate = mUserOp.getUser(mId);
            mFName.setText(mObjToUpdate.getFirstName());
            mLName.setText(mObjToUpdate.getLastName());
            mDate.setText(mObjToUpdate.getDate());
            mToolbar.setTitle(mObjToUpdate.getFirstName() + " " + mObjToUpdate.getLastName());
            setSupportActionBar(mToolbar);
        }
    }

    void initVariables() {
        mIntent = getIntent();
        mMode = mIntent.getStringExtra("mode");
        mUserOp = new UsersOperationBD(this);
        mUserOp.open();
    }

    void addMethod() {
        if (!mFName.getText().toString().equals("") && !mLName.getText().toString().equals("") && !mDate.getText().toString().equals("")) {
            mObjToUpdate = new ObjectUserDataBase();
            mObjToUpdate.setFirstName(mFName.getText().toString());
            mObjToUpdate.setLastName(mLName.getText().toString());
            mObjToUpdate.setDate(mDate.getText().toString());
            mUserOp.addUser(mObjToUpdate);
            Toast t = Toast.makeText(this, "User " + mObjToUpdate.getFirstName() + "has been added successfully !", Toast.LENGTH_SHORT);
            t.show();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    void updateMethod() {
        if (!mFName.getText().toString().equals(mObjToUpdate.getFirstName()) || !mLName.getText().toString().equals(mObjToUpdate.getLastName()) || !mDate.getText().toString().equals(mObjToUpdate.getDate())) {
            if (!mFName.getText().toString().equals("") && !mLName.getText().toString().equals("") && !mDate.getText().toString().equals("")) {
                mObjToUpdate.setFirstName(mFName.getText().toString());
                mObjToUpdate.setLastName(mLName.getText().toString());
                mObjToUpdate.setDate(mDate.getText().toString());
                mUserOp.updateUsers(mObjToUpdate);
                Toast t = Toast.makeText(this, "User " + mObjToUpdate.getFirstName() + " has been updated successfully !", Toast.LENGTH_SHORT);
                t.show();
                startActivity(new Intent(this, MainActivity.class));
            }
        }
    }

    void deleteUserMethod() {
        mUserOp.removeUser(mObjToUpdate);
        Toast t = Toast.makeText(this, "User " + mObjToUpdate.getFirstName() + " has been deleted successfully !", Toast.LENGTH_SHORT);
        t.show();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.check) {
            if (mMode.equals("update")) {
                updateMethod();
            }
            if (mMode.equals("add")) {
                addMethod();
            }
            return true;
        }
        if (id == android.R.id.home) {
            finish();
        }
        if (id == R.id.delete)
        {
            deleteUserMethod();
       }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.delete);
        if (mMode.equals("update")) {
            item.setEnabled(true);
            item.setVisible(true);
        } else {
            item.setEnabled(false);
            item.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
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
