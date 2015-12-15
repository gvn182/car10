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
public class WeatherAdapter extends ArrayAdapter<Weather> {


    Context context;
    int layoutResourceId;
    Weather data[] = null;

    public WeatherAdapter(Context context, int layoutResourceId, Weather[] data) {
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
            holder.txtData = (TextView)row.findViewById(R.id.txtData);
            holder.txtOcorrencia = (TextView)row.findViewById(R.id.txtOcorrencia);
            holder.txtTiulo = (TextView)row.findViewById(R.id.txtTiulo);
            holder.txtPlaca = (TextView)row.findViewById(R.id.txtPlaca);
            holder.imgOrc = (ImageView)row.findViewById(R.id.imgOrc);
            row.setTag(holder);
        }
        else
        {
            holder = (WeatherHolder)row.getTag();
        }

        Weather weather = data[position];
        if(weather != null) {
            holder.txtData.setText(weather.data);
            holder.txtPlaca.setText(weather.placa);
            holder.txtOcorrencia.setText(weather.ocorrencia);
            holder.txtTiulo.setText(weather.titulo);
            holder.txtTiulo.setTextColor(Color.parseColor(weather.textColorHex));
            holder.ID = weather.id;
            int ResID = context.getResources().getIdentifier(weather.img, "drawable", context.getPackageName());
            holder.imgOrc.setImageDrawable(context.getResources().getDrawable(ResID));
        }

        return row;
    }

    static class WeatherHolder
    {
        TextView txtOcorrencia;
        TextView txtData;
        TextView txtPlaca;
        ImageView imgOrc;
        TextView txtTiulo;
        String ID;
    }
}