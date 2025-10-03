package UPsay.decouverteAndroid.android_base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Graphic extends View {
    public Graphic(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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
        canvas.drawText(texte, (float) getWidth() /2, (float) getHeight() /2, p);
    }
}
