package com.example.shavez.schooldiary;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
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

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by ahmed on 11.03.2018.
 */

public class TerminAdapter extends ArrayAdapter<Termin> {

    Context context;
    Terminen terminen;
    ViewHolder vHolder;

    private static class ViewHolder {
        public TextView titel;
        public TextView beschreibung;
        public TextView datum;
        public ImageView menuBox;
        public ImageView foto;
    }

    public TerminAdapter(Context c, ArrayList<Termin> arr_termin, Terminen terminen) {
        super(c, 0, arr_termin);
        context = c;
        this.terminen = terminen;
    }

    public View getView(final int position, View converView, final ViewGroup viewGroup) {
        Termin termin = getItem(position);
        final ViewHolder viewHolder;
        if (converView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            converView = inflater.inflate(R.layout.item_termin, viewGroup, false);
            viewHolder.titel = (TextView) converView.findViewById(R.id.termin_titel);
            viewHolder.datum = (TextView) converView.findViewById(R.id.termin_datum);
            viewHolder.menuBox = (ImageView) converView.findViewById(R.id.termin_menuBox);
            viewHolder.foto = (ImageView) converView.findViewById(R.id.termin_foto);
            converView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) converView.getTag();
        }
        viewHolder.titel.setText(termin.getTermin_titel());
        viewHolder.datum.setText(termin.getTermin_datum());
        vHolder = viewHolder;

        try {
            viewHolder.menuBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(v.getId() == R.id.termin_menuBox){
                        PopupMenu popup = new PopupMenu(context, v);
                        popup.getMenuInflater().inflate(R.menu.menu,
                                popup.getMenu());
                        popup.show();
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.edit:
                                        Intent i = new Intent(terminen.getApplicationContext(),Termine_Edit.class);
                                        i.putExtra("termin_id", "" + terminen.terminAdapter.getItem(position).getTermin_id());
                                        i.putExtra("titel", terminen.terminAdapter.getItem(position).getTermin_titel());
                                        i.putExtra("datum", terminen.terminAdapter.getItem(position).getTermin_datum());
                                        i.putExtra("beschreibung", terminen.terminAdapter.getItem(position).getTermin_beschreibung());
                                        startActivity(context, i, null);
                                        break;

                                    case R.id.delete:
                                        AlertDialog.Builder adb=new AlertDialog.Builder(context);
                                        adb.setTitle("Termin löschen");
                                        adb.setMessage("Sind Sie sicher?");
                                        final int positionToRemove = position;
                                        adb.setNegativeButton("Nein", null);
                                        adb.setPositiveButton("Ja", new AlertDialog.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                try {
                                                    terminen.proOn();
                                                    JSONObject json = new JSONObject();
                                                    json.put("id", "" + terminen.terminAdapter.getItem(position).getTermin_id());
                                                    DatenHochladen t = new DatenHochladen("terminen","delTermin","terminen",terminen);
                                                    t.execute(new JSONObject[]{json});

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
