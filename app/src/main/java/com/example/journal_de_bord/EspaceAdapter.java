package com.example.journal_de_bord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class EspaceAdapter extends ArrayAdapter<ItemEspace> {
    public EspaceAdapter(Context context, List<ItemEspace> itemEspaces) {
        super(context, 0, itemEspaces);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_espace,parent, false);
        }

        ItemEspaceViewHolder viewHolder = (ItemEspaceViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ItemEspaceViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.nameEspaceItem);
            convertView.setTag(viewHolder);
        }

        ItemEspace espace = getItem(position);
        viewHolder.name.setText(espace.getTitre());

        return convertView;
    }

    private class ItemEspaceViewHolder{
        public TextView name;

    }
}
