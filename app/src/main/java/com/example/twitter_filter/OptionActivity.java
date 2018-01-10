package com.example.twitter_filter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class OptionActivity extends AppCompatActivity {

    TextView favNumber;
    TextView textSize;
    RadioGroup radioGroup;
    Button okButton;
    int checkedRadioButton = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        final SeekBar favSeekBar = (SeekBar) findViewById(R.id.favSeekBar);
        final SeekBar textSeekBar = (SeekBar) findViewById(R.id.textSeekBar);
        favNumber = (TextView) findViewById(R.id.favNumber);
        textSize = (TextView) findViewById(R.id.textSize);
        radioGroup = (RadioGroup) findViewById(R.id.radioButtonGroup);
        okButton = (Button) findViewById(R.id.okButton);

        favSeekBar.setMax(1000);
        textSeekBar.setMax(144);

        favSeekBar.setProgress(-1);
        textSeekBar.setProgress(-1);

        favNumber.setText(String.valueOf(favSeekBar.getProgress()));
        textSize.setText(String.valueOf(textSeekBar.getProgress()));

        favSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        // ツマミをドラッグしたときに呼ばれる
                        favNumber.setText(String.valueOf(favSeekBar.getProgress()));
                    }
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // ツマミに触れたときに呼ばれる
                    }
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // ツマミを離したときに呼ばれる
                    }
                }
        );

        textSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        // ツマミをドラッグしたときに呼ばれる
                        textSize.setText(String.valueOf(textSeekBar.getProgress()));
                    }
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // ツマミに触れたときに呼ばれる
                    }
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // ツマミを離したときに呼ばれる
                    }
                }
        );

        radioGroup.check(R.id.radioButtonBoth);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radio = (RadioButton)findViewById(checkedId);
                if(radio.isChecked()) {
                    System.out.println(group.indexOfChild(radio));
                    checkedRadioButton = group.indexOfChild(radio);
                }
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FilteringParameter filteringParameter = new FilteringParameter(textSeekBar.getProgress(), favSeekBar.getProgress(), checkedRadioButton);

                Intent intent = new Intent();
                intent.putExtra("key", filteringParameter);
                setResult(Activity.RESULT_OK, intent );
                finish();
            }
        });
    }
}