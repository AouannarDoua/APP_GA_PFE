package com.example.app_ga_pfe;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import java.util.List;
public class EmploiTempsFragment extends Fragment {
    TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22, t23, t24, textViewDate;
    private Handler handler = new Handler();
    private final int INTERVAL = 30000;
    private Runnable updateTask = new Runnable() {
        @Override
        public void run() {
            updateTextViews();
            handler.postDelayed(this, INTERVAL);
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emploi_temps, container, false);

        t1 = view.findViewById(R.id.t1);
        t2 = view.findViewById(R.id.t2);
        t3 = view.findViewById(R.id.t3);
        t4 = view.findViewById(R.id.t4);
        t5 = view.findViewById(R.id.t5);
        t6 = view.findViewById(R.id.t6);
        t7 = view.findViewById(R.id.t7);
        t8 = view.findViewById(R.id.t8);
        t9 = view.findViewById(R.id.t9);
        t10 = view.findViewById(R.id.t10);
        t11 = view.findViewById(R.id.t11);
        t12 = view.findViewById(R.id.t12);
        t13 = view.findViewById(R.id.t13);
        t14 = view.findViewById(R.id.t14);
        t15 = view.findViewById(R.id.t15);
        t16 = view.findViewById(R.id.t16);
        t17 = view.findViewById(R.id.t17);
        t18 = view.findViewById(R.id.t18);
        t19 = view.findViewById(R.id.t19);
        t20 = view.findViewById(R.id.t20);
        t21 = view.findViewById(R.id.t21);
        t22 = view.findViewById(R.id.t22);
        t23 = view.findViewById(R.id.t23);
        t24 = view.findViewById(R.id.t24);


        textViewDate = view.findViewById(R.id.text_view_date);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(calendar.getTime());
        textViewDate.setText(currentDate);


        Bundle bundle = getArguments();
        if (bundle != null) {
            long selectedFiliereId = bundle.getLong("idFilieres", -1);
            String selectedRadioButtonId = bundle.getString("Semester");

            // Utilisez ces données pour afficher dans le tableau
            getDataFromDatabase(selectedFiliereId, selectedRadioButtonId);

            Toast.makeText(getActivity(), ", selectedRadioButtonId : " + String.valueOf(selectedRadioButtonId), Toast.LENGTH_SHORT).show();
        }

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            long selectedFiliereId = intent.getLongExtra("idFilieres", -1);
            String selectedRadioButtonId = intent.getStringExtra("Semester");

            // Récupérer les données de la base de données en fonction des sélections de l'utilisateur
            getDataFromDatabase(selectedFiliereId, selectedRadioButtonId);
        }

        updateTextViews();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(updateTask, INTERVAL);
        updateTextViews();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(updateTask);
    }

    private void updateTextViews() {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        int currentMinute = calendar.get(Calendar.MINUTE);
        TextView[] textViews = {t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22, t23, t24};
        boolean[] enabledStates = new boolean[24];
        Arrays.fill(enabledStates, false);
        // Vérifier les conditions pour activer ou désactiver les TextView
        if (((currentHour == 21 && currentMinute >= 0 && currentMinute <= 59) || (currentHour == 22 && currentMinute >= 0 &&currentMinute <= 30)) && (currentDay == Calendar.MONDAY)) {
            enabledStates[0] = true;
        } else if (((currentHour == 9 && currentMinute >= 0 && currentMinute <= 59) || (currentHour == 10 && currentMinute >= 0 &&currentMinute <= 30)) && (currentDay == Calendar.TUESDAY)) {
            enabledStates[4] = true;

        } else if (((currentHour == 9 && currentMinute >= 0 && currentMinute <= 59) || (currentHour == 10 && currentMinute >= 0 &&currentMinute <= 30)) && (currentDay == Calendar.WEDNESDAY)) {
            enabledStates[8] = true;

        } else if (((currentHour == 9 && currentMinute >= 0 && currentMinute <= 59) || (currentHour == 10 && currentMinute >= 0 &&currentMinute <= 30)) && (currentDay == Calendar.THURSDAY)) {
            enabledStates[12] = true;
        }
        else if (((currentHour == 9 && currentMinute >= 0 && currentMinute <= 59) || (currentHour == 10 && currentMinute >= 0 &&currentMinute <= 30)) && (currentDay == Calendar.FRIDAY)) {
            enabledStates[16] = true;

        }
        else if (((currentHour == 9 && currentMinute >= 0 && currentMinute <= 59) || (currentHour == 10 && currentMinute >= 0 &&currentMinute <= 30)) && (currentDay == Calendar.SATURDAY)) {
            enabledStates[20] = true;
        }
        else if (((currentHour == 10 && currentMinute >= 45 &&currentMinute<=59) ||(currentHour == 11) || (currentHour == 12 && currentMinute >= 0 &&    currentMinute <= 15)) && (currentDay == Calendar.MONDAY)) {
            enabledStates[1] = true;
        }
        else if (((currentHour == 10 && currentMinute >= 45 &&currentMinute<=59) ||(currentHour == 11) || (currentHour == 12 && currentMinute >= 0 &&    currentMinute <= 15)) && (currentDay == Calendar.TUESDAY)) {
            enabledStates[5] = true;
        }
        else if (((currentHour == 10 && currentMinute >= 45 &&currentMinute<=59) ||(currentHour == 11) || (currentHour == 12 && currentMinute >= 0 &&    currentMinute <= 15)) && (currentDay == Calendar.WEDNESDAY)) {
            enabledStates[9] = true;
        }
        else if (((currentHour == 10 && currentMinute >= 45 &&currentMinute<=59) ||(currentHour == 11) || (currentHour == 12 && currentMinute >= 0 &&    currentMinute <= 15)) && (currentDay == Calendar.THURSDAY)) {
            enabledStates[13] = true;
        }
        else if (((currentHour == 10 && currentMinute >= 45 &&currentMinute<=59) ||(currentHour == 11) || (currentHour == 12 && currentMinute >= 0 &&    currentMinute <= 15)) && (currentDay == Calendar.FRIDAY)) {
            enabledStates[17] = true;
        }
        else if (((currentHour == 10 && currentMinute >= 45 &&currentMinute<=59) ||(currentHour == 11) || (currentHour == 12 && currentMinute >= 0 &&    currentMinute <= 15)) && (currentDay == Calendar.SATURDAY)) {
            enabledStates[21] = true;
        }

        else if (((currentHour == 14 && currentMinute >= 0 && currentMinute <= 59) || (currentHour == 15 && currentMinute >= 0 &&currentMinute <= 30)) && (currentDay == Calendar.MONDAY)) {
            enabledStates[2] = true;
        }
        else if (((currentHour == 14 && currentMinute >= 0 && currentMinute <= 59) || (currentHour == 15 && currentMinute >= 0 &&currentMinute <= 30)) && (currentDay == Calendar.TUESDAY)) {
            enabledStates[6] = true;
        }
        else if (((currentHour == 14 && currentMinute >= 0 && currentMinute <= 59) || (currentHour == 15 && currentMinute >= 0 &&currentMinute <= 30)) && (currentDay == Calendar.WEDNESDAY)) {
            enabledStates[10] = true;
        }
        else if (((currentHour == 14 && currentMinute >= 0 && currentMinute <= 59) || (currentHour == 15 && currentMinute >= 0 &&currentMinute <= 30)) && (currentDay == Calendar.THURSDAY)) {
            enabledStates[14] = true;
        }
        else if (((currentHour == 14 && currentMinute >= 0 && currentMinute <= 59) || (currentHour == 15 && currentMinute >= 0 &&currentMinute <= 30)) && (currentDay == Calendar.FRIDAY)) {
            enabledStates[18] = true;
        }
        else if (((currentHour == 14 && currentMinute >= 0 && currentMinute <= 59) || (currentHour == 15 && currentMinute >= 0 &&currentMinute <= 30)) && (currentDay == Calendar.SATURDAY)) {
            enabledStates[22] = true;
        }
        else if (((currentHour == 15 && currentMinute >= 45 && currentMinute >= 59)||(currentHour == 16) || (currentHour == 17 && currentMinute >=0 &&currentMinute <= 15)) && (currentDay == Calendar.MONDAY)) {
            enabledStates[3] = true;
        }
        else if (((currentHour == 15 && currentMinute >= 45 && currentMinute >= 59)||(currentHour == 16) || (currentHour == 17 && currentMinute >=0 &&currentMinute <= 15)) && (currentDay == Calendar.TUESDAY)) {
            enabledStates[7] = true;
        }
        else if (((currentHour == 15 && currentMinute >= 45 && currentMinute >= 59)||(currentHour == 16) || (currentHour == 17 && currentMinute >=0 &&currentMinute <= 15)) && (currentDay == Calendar.WEDNESDAY)) {
            enabledStates[11] = true;
        }
        else if (((currentHour == 15 && currentMinute >= 45 && currentMinute >= 59)||(currentHour == 16) || (currentHour == 17 && currentMinute >=0 &&currentMinute <= 15)) && (currentDay == Calendar.THURSDAY)) {
            enabledStates[15] = true;
        }
        else if (((currentHour == 15 && currentMinute >= 45 && currentMinute >= 59)||(currentHour == 16) || (currentHour == 17 && currentMinute >=0 &&currentMinute <= 15)) && (currentDay == Calendar.THURSDAY)) {
            enabledStates[19] = true;
        }
        else if (((currentHour == 15 && currentMinute >= 45 && currentMinute >= 59)||(currentHour == 16) || (currentHour == 17 && currentMinute >=0 &&currentMinute <= 15)) && (currentDay == Calendar.THURSDAY)) {
            enabledStates[23] = true;
        }
        else {
            t1.setEnabled(false);
            t1.setAlpha(0.5f);
            t2.setEnabled(false);
            t2.setAlpha(0.5f);
            t3.setEnabled(false);
            t3.setAlpha(0.5f);
            t4.setEnabled(false);
            t4.setAlpha(0.5f);
            t5.setEnabled(false);
            t5.setAlpha(0.5f);
            t6.setEnabled(false);
            t6.setAlpha(0.5f);
            t7.setEnabled(false);
            t7.setAlpha(0.5f);
            t8.setEnabled(false);
            t8.setAlpha(0.5f);
            t9.setEnabled(false);
            t9.setAlpha(0.5f);
            t10.setEnabled(false);
            t10.setAlpha(0.5f);
            t11.setEnabled(false);
            t11.setAlpha(0.5f);
            t12.setEnabled(false);
            t12.setAlpha(0.5f);
            t13.setEnabled(false);
            t13.setAlpha(0.5f);
            t14.setEnabled(false);
            t14.setAlpha(0.5f);
            t15.setEnabled(false);
            t15.setAlpha(0.5f);
            t16.setEnabled(false);
            t16.setAlpha(0.5f);
            t17.setEnabled(false);
            t17.setAlpha(0.5f);
            t18.setEnabled(false);
            t18.setAlpha(0.5f);
            t19.setEnabled(false);
            t19.setAlpha(0.5f);
            t20.setEnabled(false);
            t20.setAlpha(0.5f);
            t21.setEnabled(false);
            t21.setAlpha(0.5f);
            t22.setEnabled(false);
            t22.setAlpha(0.5f);
            t23.setEnabled(false);
            t23.setAlpha(0.5f);
            t24.setEnabled(false);
            t24.setAlpha(0.5f);
        }
        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setEnabled(enabledStates[i]);
            if (enabledStates[i]) {
                textViews[i].setBackgroundResource(R.color.app_color);
                textViews[i].setTextColor(Color.WHITE);
                textViews[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getActivity(),Scanne_Code_Student.class));
                    }
                });
            } else {
                textViews[i].setBackgroundResource(R.color.back_color);
                textViews[i].setTextColor(ContextCompat.getColor(getContext(), R.color.button_color));
            }
            textViews[i].setAlpha(enabledStates[i] ? 1.0f : 0.5f);
        }
    }
    private void getDataFromDatabase(long filiereId, String radio) {
        Emploi_TempsBD emploiTempsBD = new Emploi_TempsBD(getActivity());
        List<EmploiTempsClass> emploiDataList = emploiTempsBD.getEmploiData(filiereId, radio);

        // Afficher les données récupérées dans les TextViews correspondants
        if (emploiDataList != null && !emploiDataList.isEmpty()) {
            EmploiTempsClass emploiData = emploiDataList.get(0);
            t1.setText(emploiData.getMatiere1());
            t2.setText(emploiData.getMatiere2());
            t3.setText(emploiData.getMatiere3());
            t4.setText(emploiData.getMatiere4());
            t5.setText(emploiData.getMatiere5());
            t6.setText(emploiData.getMatiere6());
            t7.setText(emploiData.getMatiere7());
            t8.setText(emploiData.getMatiere8());
            t9.setText(emploiData.getMatiere9());
            t10.setText(emploiData.getMatiere10());
            t11.setText(emploiData.getMatiere11());
            t12.setText(emploiData.getMatiere12());
            t13.setText(emploiData.getMatiere13());
            t14.setText(emploiData.getMatiere14());
            t15.setText(emploiData.getMatiere15());
            t16.setText(emploiData.getMatiere16());
            t17.setText(emploiData.getMatiere17());
            t18.setText(emploiData.getMatiere18());
            t19.setText(emploiData.getMatiere19());
            t20.setText(emploiData.getMatiere20());
            t21.setText(emploiData.getMatiere21());
            t22.setText(emploiData.getMatiere22());
            t23.setText(emploiData.getMatiere23());
            t24.setText(emploiData.getMatiere24());
        }
    }


}