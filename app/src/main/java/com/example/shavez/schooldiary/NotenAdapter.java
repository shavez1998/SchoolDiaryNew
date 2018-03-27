package com.example.shavez.schooldiary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
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

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by ahmed on 11.03.2018.
 */

public class NotenAdapter extends ArrayAdapter<Bewertung> {

    Context context;
    Noten noten;
    ViewHolder vHolder;

    private static class ViewHolder {
        public TextView titel;
        public TextView note;
        public TextView datum;
        public ImageView menuBox;
        public ImageView note_foto;
    }

    public NotenAdapter(Context c, ArrayList<Bewertung> arr_noten, Noten noten) {
        super(c, 0, arr_noten);
        context = c;
        this.noten = noten;
    }

    public View getView(final int position, View converView, final ViewGroup viewGroup) {
        Bewertung bewertung = getItem(position);
        final ViewHolder viewHolder;
        if (converView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            converView = inflater.inflate(R.layout.item_note, viewGroup, false);
            viewHolder.titel = (TextView) converView.findViewById(R.id.note_titel);
            viewHolder.note = (TextView) converView.findViewById(R.id.note_note);
            viewHolder.datum = (TextView) converView.findViewById(R.id.note_datum);
            viewHolder.menuBox = (ImageView) converView.findViewById(R.id.note_menuBox);
            viewHolder.note_foto = (ImageView) converView.findViewById(R.id.note_foto);
            converView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) converView.getTag();
        }
        viewHolder.titel.setText(bewertung.getBewertung_titel());
        viewHolder.note.setText("" + bewertung.getNote());
        viewHolder.datum.setText(bewertung.getBewertung_datum());
        vHolder = viewHolder;

        int note_ohne_komma = (int) bewertung.getNote();
        if(bewertung.getNote() >= 10.0){
            viewHolder.note.setText("" + note_ohne_komma);
        }else{
            viewHolder.note.setText("" + bewertung.getNote());
        }
        if(bewertung.getNote() < 6){
            int red = Color.parseColor("#FFE7442E");
            viewHolder.note_foto.setBackgroundColor(red);
        } else {
            int green = Color.parseColor("#b0e72e");
            viewHolder.note_foto.setBackgroundColor(green);
        }
        try {
            viewHolder.menuBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(v.getId() == R.id.note_menuBox){
                        PopupMenu popup = new PopupMenu(context, v);
                        popup.getMenuInflater().inflate(R.menu.menu,
                                popup.getMenu());
                        popup.show();
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.edit:
                                        Intent i = new Intent(noten.getApplicationContext(),Noten_Edit.class);
                                        i.putExtra("id", position);
                                        i.putExtra("titel", noten.notenAdapter.getItem(position).getBewertung_titel());
                                        i.putExtra("datum", noten.notenAdapter.getItem(position).getBewertung_datum());
                                        i.putExtra("note", noten.notenAdapter.getItem(position).getNote()+"");
                                        startActivity(context, i, null);
                                        Toast.makeText(context, "Edit " + " : " + position, Toast.LENGTH_LONG).show();
                                        break;

                                    case R.id.delete:
                                        AlertDialog.Builder adb=new AlertDialog.Builder(context);
                                        adb.setTitle("Delete?");
                                        adb.setMessage("Are you sure you want to delete " + position);
                                        adb.setNegativeButton("Cancel", null);
                                        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                noten.notenAdapter.remove(noten.notenAdapter.getItem(position));

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
