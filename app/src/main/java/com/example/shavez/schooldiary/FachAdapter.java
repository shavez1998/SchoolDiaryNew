package com.example.shavez.schooldiary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ahmed on 11.03.2018.
 */

public class FachAdapter extends ArrayAdapter<Fach> {

    Context context;
    private ListView listView;
    Faecher faecher;
    ViewHolder vHolder;

    private static class ViewHolder {
        public TextView name;
        public TextView beschreibung;
        public TextView dnote;
        public ImageView infoBox;
        public int itemId;
    }

    public FachAdapter(Context c, ArrayList<Fach> arr_faecher, Faecher faecher) {
        super(c, 0, arr_faecher);
        context = c;
        this.faecher = faecher;
    }

    public View getView(final int position, View converView, final ViewGroup viewGroup) {
        Fach fach = getItem(position); //  Akktuelle Buch Anzeige
        final ViewHolder viewHolder;
        if (converView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            converView = inflater.inflate(R.layout.item_fach, viewGroup, false); // Fügt zu unseren ListView Layout item_book Layout zu
            viewHolder.name = (TextView) converView.findViewById(R.id.fach_name);
            viewHolder.beschreibung = (TextView) converView.findViewById(R.id.fach_beschreibung);
            viewHolder.dnote = (TextView) converView.findViewById(R.id.dnote);
            viewHolder.infoBox = (ImageView) converView.findViewById(R.id.infoBox);
            converView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) converView.getTag();
        }
        viewHolder.name.setText(fach.getFach_name());
        viewHolder.beschreibung.setText(fach.getFach_beschreibung());
        viewHolder.dnote.setText("" + fach.getDurchschnittsnote());
        viewHolder.itemId = fach.fach_id;
        vHolder = viewHolder;

        try {
            viewHolder.infoBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(v.getId() == R.id.infoBox){
                            PopupMenu popup = new PopupMenu(context, v);
                            popup.getMenuInflater().inflate(R.menu.menu,
                                    popup.getMenu());
                            popup.show();
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    switch (item.getItemId()) {
                                        case R.id.fach_info:
                                            Toast.makeText(context, " Info " + " : " + viewHolder.name.toString(), Toast.LENGTH_LONG).show();
                                            break;
                                        case R.id.fach_edit:
                                            Toast.makeText(context, "Edit " + " : " + position, Toast.LENGTH_LONG).show();
                                            break;

                                        case R.id.fach_delete:
                                            AlertDialog.Builder adb=new AlertDialog.Builder(context);
                                            adb.setTitle("Delete?");
                                            adb.setMessage("Are you sure you want to delete " + position);
                                            final int positionToRemove = position;
                                            adb.setNegativeButton("Cancel", null);
                                            adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    faecher.fachAdapter.remove(faecher.fachAdapter.getItem(position));

                                                }});
                                            adb.show();
                                            break;

                                        default:
                                            break;
                                    }

                                    return true;
                                }
                            });
                    }


                }
            });

        } catch (Exception e){}

        return converView;
    }
}
