package com.example.app_ga_pfe;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.List;
public class EmploiTempsFragment extends Fragment {
    TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22, t23, t24, textViewDate;

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


        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        int currentMinute = calendar.get(Calendar.MINUTE);
        // Vérifier les conditions pour activer ou désactiver les TextView
        if (((currentHour == 9 && currentMinute >= 0) || (currentHour == 10 && currentMinute <= 30)) && (currentDay == Calendar.MONDAY)) {
            t1.setEnabled(true);
            t2.setAlpha(0.5f);
            t3.setAlpha(0.5f);
            t4.setAlpha(0.5f);
            t5.setAlpha(0.5f);
            t6.setAlpha(0.5f);
            t7.setAlpha(0.5f);
            t8.setAlpha(0.5f);
            t9.setAlpha(0.5f);
            t10.setAlpha(0.5f);
            t11.setAlpha(0.5f);
            t12.setAlpha(0.5f);
            t13.setAlpha(0.5f);
            t14.setAlpha(0.5f);
            t15.setAlpha(0.5f);
            t16.setAlpha(0.5f);
            t17.setAlpha(0.5f);
            t18.setAlpha(0.5f);
            t19.setAlpha(0.5f);
            t20.setAlpha(0.5f);
            t21.setAlpha(0.5f);
            t22.setAlpha(0.5f);
            t23.setAlpha(0.5f);
            t24.setAlpha(0.5f);
        } else if (((currentHour == 9 && currentMinute >= 0) || (currentHour == 10 && currentMinute <= 30)) && (currentDay == Calendar.TUESDAY)) {
            t5.setEnabled(true);
            t5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), Profil_Student.class));
                }
            });
            t2.setAlpha(0.5f);
            t3.setAlpha(0.5f);
            t4.setAlpha(0.5f);
            t1.setAlpha(0.5f);
            t6.setAlpha(0.5f);
            t7.setAlpha(0.5f);
            t8.setAlpha(0.5f);
            t9.setAlpha(0.5f);
            t10.setAlpha(0.5f);
            t11.setAlpha(0.5f);
            t12.setAlpha(0.5f);
            t13.setAlpha(0.5f);
            t14.setAlpha(0.5f);
            t15.setAlpha(0.5f);
            t16.setAlpha(0.5f);
            t17.setAlpha(0.5f);
            t18.setAlpha(0.5f);
            t19.setAlpha(0.5f);
            t20.setAlpha(0.5f);
            t21.setAlpha(0.5f);
            t22.setAlpha(0.5f);
            t23.setAlpha(0.5f);
            t24.setAlpha(0.5f);
        } else if (((currentHour == 12 && currentMinute >= 0) || (currentHour == 13 && currentMinute <= 30)) && (currentDay == Calendar.WEDNESDAY)) {
            t9.setEnabled(true);
            t9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), Profil_Student.class));
                }
            });
            t2.setAlpha(0.5f);
            t3.setAlpha(0.5f);
            t4.setAlpha(0.5f);
            t5.setAlpha(0.5f);
            t6.setAlpha(0.5f);
            t7.setAlpha(0.5f);
            t8.setAlpha(0.5f);
            t1.setAlpha(0.5f);
            t10.setAlpha(0.5f);
            t11.setAlpha(0.5f);
            t12.setAlpha(0.5f);
            t13.setAlpha(0.5f);
            t14.setAlpha(0.5f);
            t15.setAlpha(0.5f);
            t16.setAlpha(0.5f);
            t17.setAlpha(0.5f);
            t18.setAlpha(0.5f);
            t19.setAlpha(0.5f);
            t20.setAlpha(0.5f);
            t21.setAlpha(0.5f);
            t22.setAlpha(0.5f);
            t23.setAlpha(0.5f);
            t24.setAlpha(0.5f);
        } else if (((currentHour == 9 && currentMinute >= 0) || (currentHour == 10 && currentMinute <= 30)) && (currentDay == Calendar.THURSDAY)) {
            t13.setEnabled(true);
            t2.setAlpha(0.5f);
            t3.setAlpha(0.5f);
            t4.setAlpha(0.5f);
            t5.setAlpha(0.5f);
            t6.setAlpha(0.5f);
            t7.setAlpha(0.5f);
            t8.setAlpha(0.5f);
            t9.setAlpha(0.5f);
            t10.setAlpha(0.5f);
            t11.setAlpha(0.5f);
            t12.setAlpha(0.5f);
            t1.setAlpha(0.5f);
            t14.setAlpha(0.5f);
            t15.setAlpha(0.5f);
            t16.setAlpha(0.5f);
            t17.setAlpha(0.5f);
            t18.setAlpha(0.5f);
            t19.setAlpha(0.5f);
            t20.setAlpha(0.5f);
            t21.setAlpha(0.5f);
            t22.setAlpha(0.5f);
            t23.setAlpha(0.5f);
            t24.setAlpha(0.5f);
        }
        else if (((currentHour == 9 && currentMinute >= 0) || (currentHour == 10 && currentMinute <= 30)) && (currentDay == Calendar.FRIDAY)) {
            t17.setEnabled(true);
            t2.setAlpha(0.5f);
            t3.setAlpha(0.5f);
            t4.setAlpha(0.5f);
            t5.setAlpha(0.5f);
            t6.setAlpha(0.5f);
            t7.setAlpha(0.5f);
            t8.setAlpha(0.5f);
            t9.setAlpha(0.5f);
            t10.setAlpha(0.5f);
            t11.setAlpha(0.5f);
            t12.setAlpha(0.5f);
            t13.setAlpha(0.5f);
            t14.setAlpha(0.5f);
            t15.setAlpha(0.5f);
            t16.setAlpha(0.5f);
            t1.setAlpha(0.5f);
            t18.setAlpha(0.5f);
            t19.setAlpha(0.5f);
            t20.setAlpha(0.5f);
            t21.setAlpha(0.5f);
            t22.setAlpha(0.5f);
            t23.setAlpha(0.5f);
            t24.setAlpha(0.5f);
        }
        else if (((currentHour == 9 && currentMinute >= 0) || (currentHour == 10 && currentMinute <= 30)) && (currentDay == Calendar.SATURDAY)) {
            t21.setEnabled(true);
            t2.setAlpha(0.5f);
            t3.setAlpha(0.5f);
            t4.setAlpha(0.5f);
            t5.setAlpha(0.5f);
            t6.setAlpha(0.5f);
            t7.setAlpha(0.5f);
            t8.setAlpha(0.5f);
            t9.setAlpha(0.5f);
            t10.setAlpha(0.5f);
            t11.setAlpha(0.5f);
            t12.setAlpha(0.5f);
            t13.setAlpha(0.5f);
            t14.setAlpha(0.5f);
            t15.setAlpha(0.5f);
            t16.setAlpha(0.5f);
            t17.setAlpha(0.5f);
            t18.setAlpha(0.5f);
            t19.setAlpha(0.5f);
            t20.setAlpha(0.5f);
            t1.setAlpha(0.5f);
            t22.setAlpha(0.5f);
            t23.setAlpha(0.5f);
            t24.setAlpha(0.5f);
        }
        else if (((currentHour == 10 && currentMinute >= 45) ||(currentHour == 11) || (currentHour == 12 && currentMinute <= 15)) && (currentDay == Calendar.MONDAY)) {
            t2.setEnabled(true);
            t1.setAlpha(0.5f);
            t3.setAlpha(0.5f);
            t4.setAlpha(0.5f);
            t5.setAlpha(0.5f);
            t6.setAlpha(0.5f);
            t7.setAlpha(0.5f);
            t8.setAlpha(0.5f);
            t9.setAlpha(0.5f);
            t10.setAlpha(0.5f);
            t11.setAlpha(0.5f);
            t12.setAlpha(0.5f);
            t13.setAlpha(0.5f);
            t14.setAlpha(0.5f);
            t15.setAlpha(0.5f);
            t16.setAlpha(0.5f);
            t17.setAlpha(0.5f);
            t18.setAlpha(0.5f);
            t19.setAlpha(0.5f);
            t20.setAlpha(0.5f);
            t21.setAlpha(0.5f);
            t22.setAlpha(0.5f);
            t23.setAlpha(0.5f);
            t24.setAlpha(0.5f);
        }
        else if (((currentHour == 10 && currentMinute >= 45)||(currentHour == 11) || (currentHour == 12 && currentMinute <= 15)) && (currentDay == Calendar.TUESDAY)) {
            t6.setEnabled(true);
            t2.setAlpha(0.5f);
            t3.setAlpha(0.5f);
            t4.setAlpha(0.5f);
            t5.setAlpha(0.5f);
            t1.setAlpha(0.5f);
            t7.setAlpha(0.5f);
            t8.setAlpha(0.5f);
            t9.setAlpha(0.5f);
            t10.setAlpha(0.5f);
            t11.setAlpha(0.5f);
            t12.setAlpha(0.5f);
            t13.setAlpha(0.5f);
            t14.setAlpha(0.5f);
            t15.setAlpha(0.5f);
            t16.setAlpha(0.5f);
            t17.setAlpha(0.5f);
            t18.setAlpha(0.5f);
            t19.setAlpha(0.5f);
            t20.setAlpha(0.5f);
            t21.setAlpha(0.5f);
            t22.setAlpha(0.5f);
            t23.setAlpha(0.5f);
            t24.setAlpha(0.5f);
        }
        else if (((currentHour == 10 && currentMinute >= 45)||(currentHour == 11) || (currentHour == 12 && currentMinute <= 15)) && (currentDay == Calendar.WEDNESDAY)) {
            t10.setEnabled(true);
            t2.setAlpha(0.5f);
            t3.setAlpha(0.5f);
            t4.setAlpha(0.5f);
            t5.setAlpha(0.5f);
            t6.setAlpha(0.5f);
            t7.setAlpha(0.5f);
            t8.setAlpha(0.5f);
            t9.setAlpha(0.5f);
            t1.setAlpha(0.5f);
            t11.setAlpha(0.5f);
            t12.setAlpha(0.5f);
            t13.setAlpha(0.5f);
            t14.setAlpha(0.5f);
            t15.setAlpha(0.5f);
            t16.setAlpha(0.5f);
            t17.setAlpha(0.5f);
            t18.setAlpha(0.5f);
            t19.setAlpha(0.5f);
            t20.setAlpha(0.5f);
            t21.setAlpha(0.5f);
            t22.setAlpha(0.5f);
            t23.setAlpha(0.5f);
            t24.setAlpha(0.5f);
        }
        else if (((currentHour == 10 && currentMinute >= 45)||(currentHour == 11) || (currentHour == 12 && currentMinute <= 15)) && (currentDay == Calendar.THURSDAY)) {
            t14.setEnabled(true);
            t2.setAlpha(0.5f);
            t3.setAlpha(0.5f);
            t4.setAlpha(0.5f);
            t5.setAlpha(0.5f);
            t6.setAlpha(0.5f);
            t7.setAlpha(0.5f);
            t8.setAlpha(0.5f);
            t9.setAlpha(0.5f);
            t10.setAlpha(0.5f);
            t11.setAlpha(0.5f);
            t12.setAlpha(0.5f);
            t13.setAlpha(0.5f);
            t1.setAlpha(0.5f);
            t15.setAlpha(0.5f);
            t16.setAlpha(0.5f);
            t17.setAlpha(0.5f);
            t18.setAlpha(0.5f);
            t19.setAlpha(0.5f);
            t20.setAlpha(0.5f);
            t21.setAlpha(0.5f);
            t22.setAlpha(0.5f);
            t23.setAlpha(0.5f);
            t24.setAlpha(0.5f);
        }
        else if (((currentHour == 10 && currentMinute >= 45)||(currentHour == 11) || (currentHour == 12 && currentMinute <= 15)) && (currentDay == Calendar.FRIDAY)) {
            t18.setEnabled(true);
            t2.setAlpha(0.5f);
            t3.setAlpha(0.5f);
            t4.setAlpha(0.5f);
            t5.setAlpha(0.5f);
            t6.setAlpha(0.5f);
            t7.setAlpha(0.5f);
            t8.setAlpha(0.5f);
            t9.setAlpha(0.5f);
            t10.setAlpha(0.5f);
            t11.setAlpha(0.5f);
            t12.setAlpha(0.5f);
            t13.setAlpha(0.5f);
            t14.setAlpha(0.5f);
            t15.setAlpha(0.5f);
            t16.setAlpha(0.5f);
            t17.setAlpha(0.5f);
            t1.setAlpha(0.5f);
            t19.setAlpha(0.5f);
            t20.setAlpha(0.5f);
            t21.setAlpha(0.5f);
            t22.setAlpha(0.5f);
            t23.setAlpha(0.5f);
            t24.setAlpha(0.5f);
        }
        else if (((currentHour == 10 && currentMinute >= 45)||(currentHour == 11) || (currentHour == 12 && currentMinute <= 15)) && (currentDay == Calendar.SATURDAY)) {
            t22.setEnabled(true);
            t2.setAlpha(0.5f);
            t3.setAlpha(0.5f);
            t4.setAlpha(0.5f);
            t5.setAlpha(0.5f);
            t6.setAlpha(0.5f);
            t7.setAlpha(0.5f);
            t8.setAlpha(0.5f);
            t9.setAlpha(0.5f);
            t10.setAlpha(0.5f);
            t11.setAlpha(0.5f);
            t12.setAlpha(0.5f);
            t13.setAlpha(0.5f);
            t14.setAlpha(0.5f);
            t15.setAlpha(0.5f);
            t16.setAlpha(0.5f);
            t17.setAlpha(0.5f);
            t18.setAlpha(0.5f);
            t19.setAlpha(0.5f);
            t20.setAlpha(0.5f);
            t21.setAlpha(0.5f);
            t1.setAlpha(0.5f);
            t23.setAlpha(0.5f);
            t24.setAlpha(0.5f);
        }
        else if (((currentHour == 14 && currentMinute >= 0) || (currentHour == 15 && currentMinute <= 30)) && (currentDay == Calendar.MONDAY)) {
            t3.setEnabled(true);
            t2.setAlpha(0.5f);
            t1.setAlpha(0.5f);
            t4.setAlpha(0.5f);
            t5.setAlpha(0.5f);
            t6.setAlpha(0.5f);
            t7.setAlpha(0.5f);
            t8.setAlpha(0.5f);
            t9.setAlpha(0.5f);
            t10.setAlpha(0.5f);
            t11.setAlpha(0.5f);
            t12.setAlpha(0.5f);
            t13.setAlpha(0.5f);
            t14.setAlpha(0.5f);
            t15.setAlpha(0.5f);
            t16.setAlpha(0.5f);
            t17.setAlpha(0.5f);
            t18.setAlpha(0.5f);
            t19.setAlpha(0.5f);
            t20.setAlpha(0.5f);
            t21.setAlpha(0.5f);
            t22.setAlpha(0.5f);
            t23.setAlpha(0.5f);
            t24.setAlpha(0.5f);
        }
        else if (((currentHour == 14 && currentMinute >= 0) || (currentHour == 15 && currentMinute <= 30)) && (currentDay == Calendar.TUESDAY)) {
            t7.setEnabled(true);
            t2.setAlpha(0.5f);
            t3.setAlpha(0.5f);
            t4.setAlpha(0.5f);
            t5.setAlpha(0.5f);
            t6.setAlpha(0.5f);
            t1.setAlpha(0.5f);
            t8.setAlpha(0.5f);
            t9.setAlpha(0.5f);
            t10.setAlpha(0.5f);
            t11.setAlpha(0.5f);
            t12.setAlpha(0.5f);
            t13.setAlpha(0.5f);
            t14.setAlpha(0.5f);
            t15.setAlpha(0.5f);
            t16.setAlpha(0.5f);
            t17.setAlpha(0.5f);
            t18.setAlpha(0.5f);
            t19.setAlpha(0.5f);
            t20.setAlpha(0.5f);
            t21.setAlpha(0.5f);
            t22.setAlpha(0.5f);
            t23.setAlpha(0.5f);
            t24.setAlpha(0.5f);
        }
        else if (((currentHour == 14 && currentMinute >= 0) || (currentHour == 15 && currentMinute <= 30)) && (currentDay == Calendar.WEDNESDAY)) {
            t11.setEnabled(true);
            t2.setAlpha(0.5f);
            t3.setAlpha(0.5f);
            t4.setAlpha(0.5f);
            t5.setAlpha(0.5f);
            t6.setAlpha(0.5f);
            t7.setAlpha(0.5f);
            t8.setAlpha(0.5f);
            t9.setAlpha(0.5f);
            t10.setAlpha(0.5f);
            t1.setAlpha(0.5f);
            t12.setAlpha(0.5f);
            t13.setAlpha(0.5f);
            t14.setAlpha(0.5f);
            t15.setAlpha(0.5f);
            t16.setAlpha(0.5f);
            t17.setAlpha(0.5f);
            t18.setAlpha(0.5f);
            t19.setAlpha(0.5f);
            t20.setAlpha(0.5f);
            t21.setAlpha(0.5f);
            t22.setAlpha(0.5f);
            t23.setAlpha(0.5f);
            t24.setAlpha(0.5f);
        }
        else if (((currentHour == 14 && currentMinute >= 0) || (currentHour == 15 && currentMinute <= 30)) && (currentDay == Calendar.THURSDAY)) {
            t15.setEnabled(true);
            t2.setAlpha(0.5f);
            t3.setAlpha(0.5f);
            t4.setAlpha(0.5f);
            t5.setAlpha(0.5f);
            t6.setAlpha(0.5f);
            t7.setAlpha(0.5f);
            t8.setAlpha(0.5f);
            t9.setAlpha(0.5f);
            t10.setAlpha(0.5f);
            t11.setAlpha(0.5f);
            t12.setAlpha(0.5f);
            t13.setAlpha(0.5f);
            t14.setAlpha(0.5f);
            t1.setAlpha(0.5f);
            t16.setAlpha(0.5f);
            t17.setAlpha(0.5f);
            t18.setAlpha(0.5f);
            t19.setAlpha(0.5f);
            t20.setAlpha(0.5f);
            t21.setAlpha(0.5f);
            t22.setAlpha(0.5f);
            t23.setAlpha(0.5f);
            t24.setAlpha(0.5f);
        }
        else if (((currentHour == 14 && currentMinute >= 0) || (currentHour == 15 && currentMinute <= 30)) && (currentDay == Calendar.FRIDAY)) {
            t19.setEnabled(true);
            t2.setAlpha(0.5f);
            t3.setAlpha(0.5f);
            t4.setAlpha(0.5f);
            t5.setAlpha(0.5f);
            t6.setAlpha(0.5f);
            t7.setAlpha(0.5f);
            t8.setAlpha(0.5f);
            t9.setAlpha(0.5f);
            t10.setAlpha(0.5f);
            t11.setAlpha(0.5f);
            t12.setAlpha(0.5f);
            t13.setAlpha(0.5f);
            t14.setAlpha(0.5f);
            t15.setAlpha(0.5f);
            t16.setAlpha(0.5f);
            t17.setAlpha(0.5f);
            t18.setAlpha(0.5f);
            t1.setAlpha(0.5f);
            t20.setAlpha(0.5f);
            t21.setAlpha(0.5f);
            t22.setAlpha(0.5f);
            t23.setAlpha(0.5f);
            t24.setAlpha(0.5f);
        }
        else if (((currentHour == 14 && currentMinute >= 0) || (currentHour == 15 && currentMinute <= 30)) && (currentDay == Calendar.SATURDAY)) {
            t23.setEnabled(true);
            t2.setAlpha(0.5f);
            t3.setAlpha(0.5f);
            t4.setAlpha(0.5f);
            t5.setAlpha(0.5f);
            t6.setAlpha(0.5f);
            t7.setAlpha(0.5f);
            t8.setAlpha(0.5f);
            t9.setAlpha(0.5f);
            t10.setAlpha(0.5f);
            t11.setAlpha(0.5f);
            t12.setAlpha(0.5f);
            t13.setAlpha(0.5f);
            t14.setAlpha(0.5f);
            t15.setAlpha(0.5f);
            t16.setAlpha(0.5f);
            t17.setAlpha(0.5f);
            t18.setAlpha(0.5f);
            t19.setAlpha(0.5f);
            t20.setAlpha(0.5f);
            t21.setAlpha(0.5f);
            t22.setAlpha(0.5f);
            t1.setAlpha(0.5f);
            t24.setAlpha(0.5f);
        }
        else if (((currentHour == 15 && currentMinute >= 45)||(currentHour == 16) || (currentHour == 17 && currentMinute <= 15)) && (currentDay == Calendar.MONDAY)) {
            t4.setEnabled(true);
            t2.setAlpha(0.5f);
            t3.setAlpha(0.5f);
            t1.setAlpha(0.5f);
            t5.setAlpha(0.5f);
            t6.setAlpha(0.5f);
            t7.setAlpha(0.5f);
            t8.setAlpha(0.5f);
            t9.setAlpha(0.5f);
            t10.setAlpha(0.5f);
            t11.setAlpha(0.5f);
            t12.setAlpha(0.5f);
            t13.setAlpha(0.5f);
            t14.setAlpha(0.5f);
            t15.setAlpha(0.5f);
            t16.setAlpha(0.5f);
            t17.setAlpha(0.5f);
            t18.setAlpha(0.5f);
            t19.setAlpha(0.5f);
            t20.setAlpha(0.5f);
            t21.setAlpha(0.5f);
            t22.setAlpha(0.5f);
            t23.setAlpha(0.5f);
            t24.setAlpha(0.5f);
        }
        else if (((currentHour == 15 && currentMinute >= 45)||(currentHour == 16) || (currentHour == 17 && currentMinute <= 15)) && (currentDay == Calendar.TUESDAY)) {
            t8.setEnabled(true);
            t2.setAlpha(0.5f);
            t3.setAlpha(0.5f);
            t4.setAlpha(0.5f);
            t5.setAlpha(0.5f);
            t6.setAlpha(0.5f);
            t7.setAlpha(0.5f);
            t1.setAlpha(0.5f);
            t9.setAlpha(0.5f);
            t10.setAlpha(0.5f);
            t11.setAlpha(0.5f);
            t12.setAlpha(0.5f);
            t13.setAlpha(0.5f);
            t14.setAlpha(0.5f);
            t15.setAlpha(0.5f);
            t16.setAlpha(0.5f);
            t17.setAlpha(0.5f);
            t18.setAlpha(0.5f);
            t19.setAlpha(0.5f);
            t20.setAlpha(0.5f);
            t21.setAlpha(0.5f);
            t22.setAlpha(0.5f);
            t23.setAlpha(0.5f);
            t24.setAlpha(0.5f);
        }
        else if (((currentHour == 17 && currentMinute >= 0)||(currentHour == 17) || (currentHour == 18 && currentMinute <= 59)) && (currentDay == Calendar.WEDNESDAY)) {
            t12.setEnabled(true);
            t12.setBackgroundResource(R.color.app_color);
            t12.setTextColor(Color.WHITE);
            t2.setAlpha(0.5f);
            t3.setAlpha(0.5f);
            t4.setAlpha(0.5f);
            t5.setAlpha(0.5f);
            t6.setAlpha(0.5f);
            t7.setAlpha(0.5f);
            t8.setAlpha(0.5f);
            t9.setAlpha(0.5f);
            t10.setAlpha(0.5f);
            t11.setAlpha(0.5f);
            t1.setAlpha(0.5f);
            t13.setAlpha(0.5f);
            t14.setAlpha(0.5f);
            t15.setAlpha(0.5f);
            t16.setAlpha(0.5f);
            t17.setAlpha(0.5f);
            t18.setAlpha(0.5f);
            t19.setAlpha(0.5f);
            t20.setAlpha(0.5f);
            t21.setAlpha(0.5f);
            t22.setAlpha(0.5f);
            t23.setAlpha(0.5f);
            t24.setAlpha(0.5f);
        }
        else if (((currentHour == 15 && currentMinute >= 45)||(currentHour == 16) || (currentHour == 17 && currentMinute <= 15)) && (currentDay == Calendar.THURSDAY)) {
            t16.setEnabled(true);
            t2.setAlpha(0.5f);
            t3.setAlpha(0.5f);
            t4.setAlpha(0.5f);
            t5.setAlpha(0.5f);
            t6.setAlpha(0.5f);
            t7.setAlpha(0.5f);
            t8.setAlpha(0.5f);
            t9.setAlpha(0.5f);
            t10.setAlpha(0.5f);
            t11.setAlpha(0.5f);
            t12.setAlpha(0.5f);
            t13.setAlpha(0.5f);
            t14.setAlpha(0.5f);
            t15.setAlpha(0.5f);
            t1.setAlpha(0.5f);
            t17.setAlpha(0.5f);
            t18.setAlpha(0.5f);
            t19.setAlpha(0.5f);
            t20.setAlpha(0.5f);
            t21.setAlpha(0.5f);
            t22.setAlpha(0.5f);
            t23.setAlpha(0.5f);
            t24.setAlpha(0.5f);
        }
        else if (((currentHour == 15 && currentMinute >= 45)||(currentHour == 16) || (currentHour == 17 && currentMinute <= 15)) && (currentDay == Calendar.FRIDAY)) {
            t20.setEnabled(true);
            t2.setAlpha(0.5f);
            t3.setAlpha(0.5f);
            t4.setAlpha(0.5f);
            t5.setAlpha(0.5f);
            t6.setAlpha(0.5f);
            t7.setAlpha(0.5f);
            t8.setAlpha(0.5f);
            t9.setAlpha(0.5f);
            t10.setAlpha(0.5f);
            t11.setAlpha(0.5f);
            t12.setAlpha(0.5f);
            t13.setAlpha(0.5f);
            t14.setAlpha(0.5f);
            t15.setAlpha(0.5f);
            t16.setAlpha(0.5f);
            t17.setAlpha(0.5f);
            t18.setAlpha(0.5f);
            t19.setAlpha(0.5f);
            t1.setAlpha(0.5f);
            t21.setAlpha(0.5f);
            t22.setAlpha(0.5f);
            t23.setAlpha(0.5f);
            t24.setAlpha(0.5f);
        }
        else if (((currentHour == 15 && currentMinute >= 45)||(currentHour == 16) || (currentHour == 17 && currentMinute <= 15)) && (currentDay == Calendar.SATURDAY)) {
            t24.setEnabled(true);
            t2.setAlpha(0.5f);
            t3.setAlpha(0.5f);
            t4.setAlpha(0.5f);
            t5.setAlpha(0.5f);
            t6.setAlpha(0.5f);
            t7.setAlpha(0.5f);
            t8.setAlpha(0.5f);
            t9.setAlpha(0.5f);
            t10.setAlpha(0.5f);
            t11.setAlpha(0.5f);
            t12.setAlpha(0.5f);
            t13.setAlpha(0.5f);
            t14.setAlpha(0.5f);
            t15.setAlpha(0.5f);
            t16.setAlpha(0.5f);
            t17.setAlpha(0.5f);
            t18.setAlpha(0.5f);
            t19.setAlpha(0.5f);
            t20.setAlpha(0.5f);
            t21.setAlpha(0.5f);
            t22.setAlpha(0.5f);
            t23.setAlpha(0.5f);
            t1.setAlpha(0.5f);
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
        Bundle bundle = getArguments();
        if (bundle != null) {
            long selectedFiliereId = bundle.getLong("idFilieres", -1);
            int selectedRadioButtonId = bundle.getInt("radiobutton_id", -1);

            // Utilisez ces données pour afficher dans le tableau
            getDataFromDatabase(selectedFiliereId, selectedRadioButtonId);

            Toast.makeText(getActivity(), ", selectedRadioButtonId : " + String.valueOf(selectedRadioButtonId), Toast.LENGTH_SHORT).show();
        }


        Intent intent = getActivity().getIntent();
        if (intent != null) {
            long selectedFiliereId = intent.getLongExtra("idFilieres", -1);
            int selectedRadioButtonId = intent.getIntExtra("radiobutton_id", -1);

            // Récupérer les données de la base de données en fonction des sélections de l'utilisateur
            getDataFromDatabase(selectedFiliereId, selectedRadioButtonId);
        }


        return view;
    }
    private void getDataFromDatabase(long filiereId, int radio) {
        Emploi_TempsBD emploiTempsBD = new Emploi_TempsBD(getActivity());
        List<EmploiTempsClass> emploiDataList = emploiTempsBD.getEmploiData(filiereId, radio);

        // Afficher les données récupérées dans les TextViews correspondants
        if (emploiDataList != null && !emploiDataList.isEmpty()) {
            EmploiTempsClass emploiData = emploiDataList.get(0);
            t1.setText(emploiData.getMatiere1());
            t2.setText(emploiData.getMatiere2());
            t3.setText(emploiData.getMatiere3());
            t4.setText(emploiData.getMatiere4());
        }

}

    }

