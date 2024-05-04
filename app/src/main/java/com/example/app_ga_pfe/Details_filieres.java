package com.example.app_ga_pfe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Details_filieres extends AppCompatActivity {
    TextView obj , formation,title,semestre;
    RadioButton s1,s2,s3,s4;
    RadioGroup scheduleRadioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_filieres);
        title = findViewById(R.id.class_program_textview);
        obj=findViewById(R.id.obj);
        formation=findViewById(R.id.formation);
        s1=findViewById(R.id.radio_button1);
        s2=findViewById(R.id.radio_button2);
        s3=findViewById(R.id.radio_button3);
        s4=findViewById(R.id.radio_button4);
        semestre=findViewById(R.id.semestre);
        scheduleRadioGroup = findViewById(R.id.scheduleRadioGroup); // Initialisation du RadioGroup

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs1", MODE_PRIVATE);
        Long selectedFiliere = sharedPreferences.getLong("idFilieres", -1);
        if (selectedFiliere != -1) {

            Details_filieresBD dfdb = new Details_filieresBD(this);
            String DAWM1 = getString(R.string.DAWM1);
            String DAWM2 = getString(R.string.DAWM2);
            String S1 = getString(R.string.s1);
            String S2 = getString(R.string.s2);
            String S3 = getString(R.string.s3);
            String S4 = getString(R.string.s4);
            String Title =getString(R.string.namefiliere1);
            String FCF1 = getString(R.string.FCF1);
            String FCF2 = getString(R.string.FCF2);
            String Sf1 = getString(R.string.s1f);
            String Sf2 = getString(R.string.s2f);
            String Sf3 = getString(R.string.s3f);
            String Sf4 = getString(R.string.s4f);
            String Title2 =getString(R.string.namefiliere2);
            dfdb.addFiliereDetails(5,Title,DAWM1,DAWM2,S1,S2,S3,S4);
            dfdb.addFiliereDetails(1,Title2,FCF1,FCF2,Sf1,Sf2,Sf3,Sf4);

            Details_filieresClass filiereDetails = dfdb.getFiliereDetailsById(selectedFiliere);

            if (filiereDetails != null) {
                String currentText = title.getText().toString();
                String newText = currentText +" "+ filiereDetails.getTitle();
                title.setText(newText);
                obj.setText("      "+filiereDetails.getDetails1());
                formation.setText("      "+filiereDetails.getDetails2());
                semestre.setText(filiereDetails.getS1());
                // Ajout du listener pour détecter les changements d'état des RadioButtons
                scheduleRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton id = findViewById(checkedId);
                        if(id==s1){
                            semestre.setText(filiereDetails.getS1());
                        }
                        else if(id==s2){
                            semestre.setText(filiereDetails.getS2());
                        }
                        else if(id==s3){
                            semestre.setText(filiereDetails.getS3());
                        }
                        else if(id==s4){
                            semestre.setText(filiereDetails.getS4());
                        }
                    }

                });
            }
        } else {
            Toast.makeText(this, "ID de filière non valide", Toast.LENGTH_SHORT).show();
        }
    }
}