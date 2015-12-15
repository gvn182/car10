package simple.com.car10;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by GiovanniGomes on 15/01/2015.
 */
public class DatePickerDialogFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    String SelDate = "";
    StringBuilder sb;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of TimePickerDialog and return it
        return new DatePickerDialog(getActivity(),this,year,month,day);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        String Mes = String.valueOf((month+1));
        sb = new StringBuilder();
        sb.append(day);
        sb.append('/');
        sb.append(Mes.length() == 1 ? "0" + Mes : Mes);
        sb.append('/');
        sb.append(year);

        SelDate = sb.toString();

        TextView txtDataNascimento = (TextView)getActivity().findViewById(R.id.txtDataNascimento);
        txtDataNascimento.setText (SelDate);
    }


}
