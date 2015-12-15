package simple.com.car10;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Giovanni on 01/07/2014.
 */
public class PecasAdapter extends ArrayAdapter<Pecas> {


    Context context;
    int layoutResourceId;
    Pecas data[] = null;

    public PecasAdapter(Context context, int layoutResourceId, Pecas[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        WeatherHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new WeatherHolder();
            holder.txtTitulo = (TextView)row.findViewById(R.id.txtTitulo);
            holder.imgImagem = (ImageView)row.findViewById(R.id.imgPeca);
            row.setTag(holder);
        }
        else
        {
            holder = (WeatherHolder)row.getTag();
        }

        Pecas pec = data[position];
        if(pec != null) {
            holder.txtTitulo.setText(pec.Titulo);
            if(pec.Tag.equals("original"))
            {
                    holder.imgImagem.setImageResource(R.drawable.original);
            }
            else if(pec.Tag.equals("recycled"))
            {
                holder.imgImagem.setImageResource(R.drawable.recycled);
            }
            else if(pec.Tag.equals("compatible"))
            {
                holder.imgImagem.setImageResource(R.drawable.compatible);
            }
            else
            {
                holder.imgImagem.setVisibility(View.GONE);
            }
        }

        return row;
    }

    static class WeatherHolder
    {
        TextView txtTitulo;
        ImageView imgImagem;

    }
}