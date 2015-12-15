package simple.com.car10;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Giovanni on 01/07/2014.
 */
public class ComentarioAdapter extends ArrayAdapter<Comentario> {


    Context context;
    int layoutResourceId;
    Comentario data[] = null;

    public ComentarioAdapter(Context context, int layoutResourceId, Comentario[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ComentarioHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ComentarioHolder();
            holder.txtDescricao = (TextView)row.findViewById(R.id.txtDescricao);
            holder.imgRating = (ImageView)row.findViewById(R.id.imgRating);
            row.setTag(holder);
        }
        else
        {
            holder = (ComentarioHolder)row.getTag();
        }

        Comentario orc = data[position];
        if(orc != null) {
            holder.txtDescricao.setText(orc.descricao);
            float media = Math.round(Float.parseFloat(orc.nota));
            if (media == 0.0) {
                holder.imgRating.setImageDrawable(context.getResources().getDrawable(R.drawable.s_1_star_rating));
            } else if (media == 1.0) {
                holder.imgRating.setImageDrawable(context.getResources().getDrawable(R.drawable.s_1_star_rating));
            } else if (media == 2.0) {
                holder.imgRating.setImageDrawable(context.getResources().getDrawable(R.drawable.s_2_star_rating));
            } else if (media == 3.0) {
                holder.imgRating.setImageDrawable(context.getResources().getDrawable(R.drawable.s_3_star_rating));
            } else if (media == 4.0) {
                holder.imgRating.setImageDrawable(context.getResources().getDrawable(R.drawable.s_4_star_rating));
            } else if (media == 5.0) {
                holder.imgRating.setImageDrawable(context.getResources().getDrawable(R.drawable.s_5_star_rating));
            }
        }

        return row;
    }

    static class ComentarioHolder
    {
        TextView txtDescricao;
        ImageView imgRating;



    }
}