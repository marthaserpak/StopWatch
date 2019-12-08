package hfad.app.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StopWatchActivity extends Activity {

    private int seconds = 0;
    private boolean running;
    //перем для хранения информ о том, работал ли секундомер перед вызов метода onStop();
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

//Восстанавлив. состояние активности по значениям, прочитанным из Bundle
        if(savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }
//Сохраняем состояние переменных в методе onSaveInstanceState()
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(wasRunning){
            running = true;
        }
    }

    @Override
    protected  void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

//Start the stopWatch running when the Start button is clicked.
    public void onClickStart(View view) {
        running = true;
    }

//Stop the stopWatch running when the Stop button is clicked.
    public void onClickStop(View view) {
        running = false;
    }

//Reset the stopWatch running when the Reset button is clicked.
    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    private  void runTimer() {
        final TextView timeView = findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Форматируем значение seconds в часы, минуты, секунды
                int hours = seconds/3600;
                int minutes = (seconds%360)/60;
                int secs = seconds%60;
                String time = String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours, minutes,secs);
                timeView.setText(time);
                if(running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

}
