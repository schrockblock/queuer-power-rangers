package com.queuerPowerRangers.app.Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.queuerPowerRangers.app.Interfaces.RearrangementListener;
import com.queuerPowerRangers.app.R;
import com.queuerPowerRangers.app.Models.Project;


import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Created by Rotios on 1/15/14.
 */
public class FeedAdapter extends BaseAdapter implements RearrangementListener, ListAdapter{
    private Context context;
    private ArrayList<Project> projects = new ArrayList<Project>();

    public FeedAdapter(Context context, ArrayList<Project> projects) {
        this.context = context;
        this.projects = projects;
    }


    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public int getCount() {
        return projects.size();
    }

    @Override
    public Project getItem(int position) {
        return projects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
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
        projects.remove(position);
        notifyDataSetChanged();
    }
    public void insert(Project project, int position){
        projects.add(position, project);
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
        return projects.isEmpty();
    }

    @Override
    public void onStartedRearranging() {

    }

    @Override
    public void swapElements(int indexOne, int indexTwo) {
        Project temp = getItem(indexOne);
        projects.remove(indexOne);
        projects.add(indexOne, getItem(indexTwo));
        projects.add(indexTwo, temp);
    }

    @Override
    public void onFinishedRearranging() {

    }
}