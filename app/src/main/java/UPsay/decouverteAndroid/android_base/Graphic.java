package UPsay.decouverteAndroid.android_base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Graphic extends View {
    float x1, x2, y1, y2;
    int penche, pencheV, pencheH;
    float xText, yText;
    public Graphic(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setXYText (600,600);
        setOnTouchListener(onTouchListener);
        Sensor accelerometre;
        SensorManager m = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        accelerometre = m.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        m.registerListener(mSensorEventListener, accelerometre, SensorManager.SENSOR_DELAY_UI);
    }

    public void setXYText (float x, float y){
        xText = x;
        yText = y;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, getWidth(), getHeight(), p);
        p.setColor(Color.GREEN);
        p.setTextSize(100);
        p.setTextAlign(Paint.Align.CENTER);
        String texte = "Bonjour MONDE";
        //canvas.drawText(texte, (float) getWidth() /2, (float) getHeight() /2, p);
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.image1);
        int height = (int)((float)canvas.getWidth()/b.getWidth()*b.getHeight());
        b = Bitmap.createScaledBitmap(b, canvas.getWidth(), height, false);
        //canvas.drawBitmap(b, 0, 0, p);
        Toast.makeText(getContext(), "Il faut saisir une heure", Toast.LENGTH_SHORT).show();
        canvas.drawText(texte, xText, yText, p);
        timerHandler.postDelayed(updateTimerThread, 10);
    }

    /*public boolean onTouchEvent(MotionEvent event){
        xText = event.getX();
        yText = event.getY();
        invalidate();
        return false;
    }*/

    Handler timerHandler = new Handler();
    Runnable updateTimerThread = new Runnable() {
        public void run() {
            timerHandler.postDelayed(this,100);
            invalidate(); // appel de onDraw pour redessiner
        }
    };

    OnTouchListener onTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //xText = event.getX();
            //yText = event.getY();
            //invalidate();
            //return true;

            float dx, dy;
            String direction;
            switch(event.getAction()) {
                case(MotionEvent.ACTION_DOWN):
                    x1 = event.getX();
                    y1 = event.getY();
                    Log.i("pacman", "appuyé");
                    break;

                case(MotionEvent.ACTION_UP): {
                    x2 = event.getX();
                    y2 = event.getY();
                    dx = x2-x1;
                    dy = y2-y1;
                    // Use dx and dy to determine the direction of the move
                    if(Math.abs(dx) > Math.abs(dy)) {
                        if(dx>0)
                            direction = "right";
                        else
                            direction = "left";
                    } else {
                        if(dy>0)
                            direction = "down";
                        else
                            direction = "up";
                    }
                    Log.i("pacman", "laché " + direction);
                    Log.i("pacman", "dx = " + dx +"; dy = " + dy);
                    break;
                }
            }
            invalidate();
            return true;
        }
    };

    final SensorEventListener mSensorEventListener = new SensorEventListener(){
        @Override
        public void	onAccuracyChanged(Sensor sensor, int accuracy) {
        }
        public void	onSensorChanged(SensorEvent sensorEvent) { // Que faire en cas d'évènements sur le capteur ?
            pencheH	= -(int)(sensorEvent.values[0]);
            pencheV	= (int)(sensorEvent.values[1]);
            penche	= pencheH*pencheH + pencheV*pencheV;
            Log.i("pacman", String.format("pencheHorizontale : %h", pencheH));
            Log.i("pacman", String.format("pencheVerticale : %h", pencheV));
            Log.i("pacman", String.format("penche : %h", penche));
        }
    };

}
