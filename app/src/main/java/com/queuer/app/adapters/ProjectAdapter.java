package com.queuer.app.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.queuer.app.interfaces.RearrangementListener;
import com.queuer.app.models.Task;
import com.queuer.app.models.Task;
import com.queuer.app.R;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Created by Anthoney on 1/17/14.
 */
public class ProjectAdapter extends BaseAdapter implements RearrangementListener {
    private Context context;
    private ArrayList<Task> tasks = new ArrayList<Task>();

    public ProjectAdapter(Context context, ArrayList<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Task getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Nullable
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_project, null);
        }
        ((TextView)convertView.findViewById(R.id.tv_title)).setText(getItem(position).getName());
        return convertView;
    }

    public void remove(int position){
        tasks.remove(position);
        notifyDataSetChanged();
    }
    public void insert(Task project, int position){
        tasks.add(position, project);
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    @Override
    public void onStartedRearranging() {

    }

    @Override
    public void swapElements(int indexOne, int indexTwo) {
        Task temp = getItem(indexOne);
        tasks.remove(indexOne);
        tasks.add(indexOne, getItem(indexTwo));
        tasks.add(indexTwo, temp);
    }

    @Override
    public void onFinishedRearranging() {

    }
}