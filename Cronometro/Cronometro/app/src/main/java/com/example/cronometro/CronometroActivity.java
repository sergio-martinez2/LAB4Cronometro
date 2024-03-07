package com.example.cronometro;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CronometroActivity extends AppCompatActivity {

    private TextView textViewCronometro, textViewLapTime;
    private Button buttonIniciar, buttonDetener, buttonVuelta, buttonReiniciar;
    private boolean running;
    private int seconds = 0;
    private int lapCount = 1;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);

        textViewCronometro = findViewById(R.id.textViewCronometro);
        textViewLapTime = findViewById(R.id.textViewLapTime);
        buttonIniciar = findViewById(R.id.buttonIniciar);
        buttonDetener = findViewById(R.id.buttonDetener);
        buttonVuelta = findViewById(R.id.buttonVuelta);
        buttonReiniciar = findViewById(R.id.buttonReiniciar);

        handler = new Handler();

        buttonIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = true;
                buttonIniciar.setEnabled(false);
                buttonDetener.setEnabled(true);
                buttonVuelta.setEnabled(true);
                runTimer();
            }
        });

        buttonDetener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;
                buttonIniciar.setEnabled(true);
                buttonDetener.setEnabled(false);
                buttonVuelta.setEnabled(false);
            }
        });

        buttonReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarVuelta();
            }
        });

        buttonVuelta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerLapTime();
            }
        });
    }

    private void runTimer() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%02d:%02d:%02d", hours, minutes, secs);
                textViewCronometro.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void registerLapTime() {
        // Registro de la primera vuelta y actualización de la visibilidad del botón de reinicio
        if (lapCount == 1) {
            Button buttonReiniciar = findViewById(R.id.buttonReiniciar);
            buttonReiniciar.setVisibility(View.VISIBLE);
        }

        // Resto del código para registrar las vueltas

        TextView lapTimeView = findViewById(R.id.textViewLapTime);
        String lapTime = textViewCronometro.getText().toString();
        String lapText = "Vuelta " + lapCount + ": " + lapTime + "\n";
        lapTimeView.append(lapText);
        lapCount++;
    }

    private void reiniciarVuelta() {
        lapCount = 1;
        textViewLapTime.setText("");
        seconds = 0;
        running = false;
        updateTimerText();
        buttonIniciar.setEnabled(true);
        buttonDetener.setEnabled(false);
        buttonVuelta.setEnabled(false);
        buttonReiniciar.setVisibility(View.GONE);
    }

    private void updateTimerText() {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        String time = String.format("%02d:%02d:%02d", hours, minutes, secs);
        textViewCronometro.setText(time);
    }
}
