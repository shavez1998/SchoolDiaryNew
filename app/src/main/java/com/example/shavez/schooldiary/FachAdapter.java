package com.example.shavez.schooldiary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
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

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ahmed on 11.03.2018.
 */

public class FachAdapter extends ArrayAdapter<Fach> {

    Context context;
    Faecher faecher;
    ViewHolder vHolder;

    private static class ViewHolder {
        public TextView name;
        public TextView dnote;
        public ImageView menuBox;
        public ImageView fach_foto;
    }

    public FachAdapter(Context c, ArrayList<Fach> arr_faecher, Faecher faecher) {
        super(c, 0, arr_faecher);
        context = c;
        this.faecher = faecher;
    }

    public View getView(final int position, View converView, final ViewGroup viewGroup) {
        Fach fach = getItem(position);
        final ViewHolder viewHolder;
        if (converView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            converView = inflater.inflate(R.layout.item_fach, viewGroup, false);
            viewHolder.name = (TextView) converView.findViewById(R.id.fach_name);
            viewHolder.dnote = (TextView) converView.findViewById(R.id.dnote);
            viewHolder.menuBox = (ImageView) converView.findViewById(R.id.fach_menuBox);
            viewHolder.fach_foto = (ImageView) converView.findViewById(R.id.fach_foto);
            converView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) converView.getTag();
        }
        viewHolder.name.setText(fach.getFach_name());
        int note_ohne_komma = (int) fach.getDurchschnittsnote();
        if(fach.getDurchschnittsnote() >= 10.0){
            viewHolder.dnote.setText("" + note_ohne_komma);
        }else{
            viewHolder.dnote.setText("" + fach.getDurchschnittsnote());
        }


        if(fach.getDurchschnittsnote() < 6){
            int red = Color.parseColor("#FFE7442E");
            viewHolder.fach_foto.setBackgroundColor(red);
        } else {
            int green = Color.parseColor("#b0e72e");
            viewHolder.fach_foto.setBackgroundColor(green);
        }
        vHolder = viewHolder;

        try {
            viewHolder.menuBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(v.getId() == R.id.fach_menuBox){
                            PopupMenu popup = new PopupMenu(context, v);
                            popup.getMenuInflater().inflate(R.menu.menu_fach,
                                    popup.getMenu());
                            popup.show();
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    switch (item.getItemId()) {

                                        case R.id.delete:


                                            AlertDialog.Builder adb=new AlertDialog.Builder(context);
                                            adb.setTitle("Fach löschen");
                                            adb.setMessage("Sind Sie sicher?");
                                            final int positionToRemove = position;
                                            adb.setNegativeButton("Nein", null);
                                            adb.setPositiveButton("Ja", new AlertDialog.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                 try {
                                                    faecher.proOn();
                                                    JSONObject json = new JSONObject();
                                                    json.put("fachID", "" + faecher.fachAdapter.getItem(position).getFach_id());
                                                    json.put("userID", "" + MainActivity.benutzer.getBenutzer_id());
                                                    DatenHochladen t = new DatenHochladen("faecher","delFach");
                                                    t.execute(new JSONObject[]{json});
                                                    faecher.fachAdapter.remove(faecher.fachAdapter.getItem(position));
                                                    faecher.proOff();
                                                } catch (Exception e){ Log.w("DELETE ERROR", "asdf"); e.getMessage();}

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
