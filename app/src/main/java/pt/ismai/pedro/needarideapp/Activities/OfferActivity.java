package pt.ismai.pedro.needarideapp.Activities;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import pt.ismai.pedro.needarideapp.R;
import ru.slybeaver.slycalendarview.SlyCalendarDialog;
import ru.slybeaver.slycalendarview.SlyCalendarView;

public class OfferActivity extends AppCompatActivity implements SlyCalendarDialog.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
    }


    @Override
    public void onCancelled() {

    }

    @Override
    public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {
        if (firstDate != null) {

            Toast.makeText(this, (CharSequence) firstDate, Toast.LENGTH_SHORT).show();

            if (secondDate == null) {
                Toast.makeText(this, (CharSequence) secondDate, Toast.LENGTH_SHORT).show();
                firstDate.set(Calendar.HOUR_OF_DAY, hours);
                firstDate.set(Calendar.MINUTE, minutes);

            }
        }
    }
}
