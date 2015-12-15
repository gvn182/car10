package simple.com.car10;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
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
public class OrcamentoAdpter extends ArrayAdapter<Orcamento> {


    Context context;
    int layoutResourceId;
    Orcamento data[] = null;

    public OrcamentoAdpter(Context context, int layoutResourceId, Orcamento[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        OrcamentoHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new OrcamentoHolder();
            holder.txtOficina = (TextView)row.findViewById(R.id.txtOficina);
            holder.imgNovo = (ImageView)row.findViewById(R.id.imgNovo);
            holder.imgOficina = (ImageView)row.findViewById(R.id.imgOficina);
            holder.txtTempo = (TextView)row.findViewById(R.id.txtTempo);
            holder.txtValidade = (TextView)row.findViewById(R.id.txtValidade);
            holder.txtValor = (TextView)row.findViewById(R.id.txtValor);
            holder.imgRating = (ImageView)row.findViewById(R.id.imgRating);
            row.setTag(holder);
        }
        else
        {
            holder = (OrcamentoHolder)row.getTag();
        }

        Orcamento orc = data[position];
        if(orc != null) {
            holder.txtOficina.setText(orc.NomeOficina);
            if(!orc.naolido)
            holder.imgNovo.setVisibility(View.VISIBLE);
            else
            holder.imgNovo.setVisibility(View.INVISIBLE);

            if(orc.imgNota == null)
            {
                holder.imgRating.setVisibility(View.INVISIBLE);
            }
            else
            {
                holder.imgRating.setVisibility(View.VISIBLE);
                holder.imgRating.setImageDrawable(orc.imgNota);
            }
            holder.imgOficina.setImageDrawable(orc.imagemOficina);
            holder.txtTempo.setText(orc.TempoConserto);
            holder.txtValidade.setText(orc.Validade);
            holder.txtValor.setText(orc.Valor);
        }

        return row;
    }

    static class OrcamentoHolder
    {
        TextView txtOficina;
        TextView txtTempo;
        TextView txtValidade;
        ImageView imgOficina;
        ImageView imgNovo;
        TextView txtValor;
        ImageView imgRating;



    }
}