package com.intense.yolo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.intense.yolo.R;
import com.intense.yolo.entity.DBLocationInfo;

import java.util.ArrayList;
import java.util.List;

public class DBLocationAdapter extends ArrayAdapter<DBLocationInfo> implements Filterable {

    private List<DBLocationInfo> contactsList, filterContactsList;
    private Context context;
    private Filter contactsFilter;

    public DBLocationAdapter(Context context, List<DBLocationInfo> contactsList) {
        super(context, R.layout.db_location, contactsList);
        this.context = context;
        this.contactsList = contactsList;
        filterContactsList = new ArrayList<>();
        this.filterContactsList.addAll(contactsList);
    }

    private static class DBLocationHolder {
        private TextView dbLocationName;
    }

    public int getCount() {
        return contactsList.size();
    }

    public DBLocationInfo getItem(int position) {
        return contactsList.get(position);
    }

    public long getItemId(int position) {
        return contactsList.get(position).hashCode();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        DBLocationHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.db_location, null);
            holder = new DBLocationHolder();
            holder.dbLocationName = (TextView) row.findViewById(R.id.tv_db_place_descriptions);
            row.setTag(holder);
        } else {
            holder = (DBLocationHolder) row.getTag();
        }
        DBLocationInfo contactsInfo = contactsList.get(position);
        holder.dbLocationName.setText(contactsInfo.getName());
        return row;
    }

    void selectedContactsStyle(Integer selectedPosition) {
        notifyDataSetChanged();
    }

    void refreshContacts() {
        contactsList = filterContactsList;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (contactsFilter == null) {
            contactsFilter = new ContactsFilter();
        }
        return contactsFilter;
    }

    public class ContactsFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String data = constraint.toString().toLowerCase();
            FilterResults filterResults = new FilterResults();
            if (data.length() > 0) {
                List<DBLocationInfo> filteredList = new ArrayList<>(filterContactsList);
                List<DBLocationInfo> nList = new ArrayList<>();
                int count = filteredList.size();
                for (int i = 0; i < count; i++) {
                    DBLocationInfo item = filteredList.get(i);
                    String name = item.getName().toLowerCase();
                    if (name.startsWith(data))
                        nList.add(item);
                }
                filterResults.count = nList.size();
                filterResults.values = nList;
            } else {
                List<DBLocationInfo> list = new ArrayList<>(filterContactsList);
                filterResults.count = list.size();
                filterResults.values = list;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contactsList = (ArrayList<DBLocationInfo>) results.values;
            clear();
            for (int i = 0; i < contactsList.size(); i++) {
                DBLocationInfo item = (DBLocationInfo) contactsList.get(i);
                add(item);
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}