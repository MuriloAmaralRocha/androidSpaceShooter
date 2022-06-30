package fatec.example.aluno.atividade01;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;

public class Score {

    private Context context;
    private int score = 0;

    public Score(Context context){
        this.context = context;
    }

    public void draw(Canvas canvas) {
        String text = "score: " + score;

        float x =  100;
        float y =  120;

        Paint paint = new Paint();

        int color = ContextCompat.getColor(context, R.color.score);
        paint.setColor(color);
        float tamanho = 100;
        paint.setTextSize(tamanho);

        canvas.drawText(text, x, y, paint);
    }

    public void aumentarScore(int score){
        this.score += score;
    }
}
