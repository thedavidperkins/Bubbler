package djp.djp.djp.bubbler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class BubblerMain extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "djp.djp.djp.bubbler.MESSAGE";
    ConstraintLayout cl;
    ListView lv;
    PItemList list = new PItemList();
    RegisterListener rl = new RegisterListener();
    private int selectedPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubbler_main);
        cl = (ConstraintLayout) findViewById(R.id.bubblerCl);
        lv = (ListView) findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(this, list, lv, rl);
        lv.setAdapter(adapter);
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        if (pref.getBoolean("Loading", false)) {
            int length = pref.getInt("NumItems", -1);
            if (length > 0) {
                for(int iter = length - 1; iter >= 0; --iter) {
                    String ind = Integer.toString(iter);
                    list.addItem(new PItem(pref.getString(ind, "")));
                }
            }
            lv.invalidateViews();
        }
    }

    /** Called when the user taps the Add button */
    public void addPriority(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        editText.setText("");
        list.addItem(new PItem(message));
        lv.invalidateViews();
    }

    public void demote(View view) {
        int pos = lv.getPositionForView(view);
        list.demoteToBottom(pos);
        lv.invalidateViews();
    }

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.priority_context_menu, menu);
        selectedPos = lv.getPositionForView(view);
    }

    public void onPause() {
        super.onPause();
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("Loading", true);
        editor.putInt("NumItems", list.size());
        for(int iter = 0; iter < list.size(); ++iter) {
            editor.putString(Integer.toString(list.getItemAt(iter).getPriority() - 1), list.getItemAt(iter).getName());
        }
        editor.apply();
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("DEBUG", "Checking for saved data");
        if(savedInstanceState != null) {
            Log.d("DEBUG", "Checking if anything to load");
            if (savedInstanceState.getBoolean("Loading")) {
                Log.d("DEBUG", "Getting String array");
                ArrayList<String> unpack = savedInstanceState.getStringArrayList("Items");
                if (unpack != null) {
                    Log.d("DEBUG", "Loading Strings into PItems");
                    for (String s : unpack) {
                        list.addItem(new PItem(s));
                    }
                    lv.invalidateViews();
                }
            }
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d("DEBUG", "Saving");
        savedInstanceState.putBoolean("Loading", true);
        ArrayList<String> items = new ArrayList<>();
        for(int iter = 0; iter < list.size(); ++iter) {
            items.add(iter, list.getItemAt(iter).getName());
        }
        savedInstanceState.putStringArrayList("Items", items);
        super.onSaveInstanceState(savedInstanceState);
    }

    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.optionTop:
                list.promoteToTop(selectedPos);
                lv.invalidateViews();
                return true;
            case R.id.optionBottom:
                list.demoteToBottom(selectedPos);
                lv.invalidateViews();
                return true;
            case R.id.optionUp:
                list.moveTo(selectedPos, selectedPos - 1);
                lv.invalidateViews();
                return true;
            case R.id.optionDown:
                list.moveTo(selectedPos, selectedPos + 1);
                lv.invalidateViews();
                return true;
            case R.id.optionDelete:
                list.removeItem(selectedPos);
                lv.invalidateViews();
                return true;
        }
        return false;
    }

    public class RegisterListener {
        void onEvent(View view) {
            registerForContextMenu(view);
        }
    }
}
