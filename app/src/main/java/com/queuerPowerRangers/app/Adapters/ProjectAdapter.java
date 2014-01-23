package com.queuerPowerRangers.app.Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.queuerPowerRangers.app.Interfaces.RearrangementListener;
import com.queuerPowerRangers.app.Models.Task;
import com.queuerPowerRangers.app.Models.Task;
import com.queuerPowerRangers.app.R;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Created by Rotios on 1/17/14.
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
        Task temp1 = getItem(indexOne);
        Task temp2 = getItem(indexTwo);

        tasks.remove(indexOne);
        tasks.add(indexOne, temp2);

        tasks.remove(indexTwo);
        tasks.add(indexTwo, temp1);
    }

    @Override
    public void onFinishedRearranging() {

    }
}
