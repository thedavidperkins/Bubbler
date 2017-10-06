package djp.djp.djp.bubbler; /**
 * Created by DJP on 8/27/2017.
 */

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
    private final Context context;
    private PItemList list;
    private final ListView lv;
    private final BubblerMain.RegisterListener rl;

    public MyAdapter(Context ctx, PItemList list, ListView lv, BubblerMain.RegisterListener rl) {
        this.context = ctx;
        this.list = list;
        this.lv = lv;
        this.rl = rl;
    }

    public boolean addItem(PItem item) {
        return list.addItem(item);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View priorityView = inflater.inflate(R.layout.priority_layout, parent, false);
        TextView textView = (TextView) priorityView.findViewById(R.id.priorityText);
        textView.setText(list.getItemAt(position).getName());

        priorityView.setOnLongClickListener(handleLongClick);
        priorityView.setTag(textView.getText());

        rl.onEvent(priorityView);
        return priorityView;

    }

    private View.OnLongClickListener handleLongClick = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            v.showContextMenu();
            return true;
        }
    };

    @Override
    public Object getItem(int position) {
        return list.getItemAt(position);
    }
}
