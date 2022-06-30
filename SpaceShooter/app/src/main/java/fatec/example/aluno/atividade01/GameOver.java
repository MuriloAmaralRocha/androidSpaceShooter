package fatec.example.aluno.atividade01;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;

public class GameOver {

    private Context context;

    public GameOver(Context context){
        this.context = context;
    }

    public void draw(Canvas canvas) {
        String text = "Game Over";

        float x =  800;
        float y =  200;

        Paint paint = new Paint();

        int color = ContextCompat.getColor(context, R.color.gameOver);
        paint.setColor(color);
        float tamanho = 150;
        paint.setTextSize(tamanho);

        canvas.drawText(text, x, y, paint);
    }
}