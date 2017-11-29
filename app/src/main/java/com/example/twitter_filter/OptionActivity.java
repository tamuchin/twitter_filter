package com.example.twitter_filter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/*
返却を意識してActivityを起動する

// 遷移先のactivityを指定してintentを作成
Intent intent = new Intent( this, MyActivity.class );

// 遷移先から返却されてくる際の識別コード
int requestCode = 1001;

// 返却値を考慮したActivityの起動を行う
startActivityForResult( intent, requestCode );


public void onActivityResult( int requestCode, int resultCode, Intent intent )
{
 // startActivityForResult()の際に指定した識別コードとの比較
 if( requestCode == 1001 ){

  // 返却結果ステータスとの比較
  if( resultCode == Activity.RESULT_OK ){

   // 返却されてきたintentから値を取り出す
   String str = intent.getStringExtra( "key" );
  }
 }
}
*/

public class OptionActivity extends AppCompatActivity {

    TextView favNumber;
    TextView textSize;
    Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        final SeekBar favSeekBar = (SeekBar) findViewById(R.id.favSeekBar);
        final SeekBar textSeekBar = (SeekBar) findViewById(R.id.textSeekBar);
        favNumber = (TextView) findViewById(R.id.favNumber);
        textSize = (TextView) findViewById(R.id.textSize);
        okButton = (Button) findViewById(R.id.okButton);

        favSeekBar.setProgress(-1);
        textSeekBar.setProgress(-1);

        favNumber.setText(favSeekBar.getProgress());
        textSize.setText(textSeekBar.getProgress());

        favSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        // ツマミをドラッグしたときに呼ばれる
                        favNumber.setText(favSeekBar.getProgress());
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
                        textSize.setText(textSeekBar.getProgress());
                    }
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // ツマミに触れたときに呼ばれる
                    }
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // ツマミを離したときに呼ばれる
                    }
                }
        );

        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FilteringParameter filteringParameter = new FilteringParameter(textSeekBar.getProgress(), favSeekBar.getProgress());

                Intent intent = new Intent();
                intent.putExtra("key", filteringParameter);
                setResult(Activity.RESULT_OK, intent );
                finish();
            }
        });
    }


}
