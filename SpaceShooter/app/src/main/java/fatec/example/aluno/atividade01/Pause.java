package fatec.example.aluno.atividade01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;

public class Pause {
    private Context context;

    private int x;
    private int y;
    private Bitmap bitmap;
    private Paint paint;
    private boolean isPaused;

    public Pause(Context context, int larguraTela, int alturaTela){
        this.context = context;

        bitmap = BitmapFactory.decodeResource(context.getResources(),  R.drawable.pause);
        paint = new Paint();
        paint.setColor(Color.WHITE);

        x = larguraTela - bitmap.getWidth();
        y = 0;
    }

    public void drawButton(Canvas canvas){
        canvas.drawBitmap(
                bitmap,
                x,
                y,
                paint
        );
    }

    public void draw(Canvas canvas) {
        String text = "Pause";

        float x = 800;
        float y = 200;

        Paint paint = new Paint();

        int color = ContextCompat.getColor(context, R.color.pause);
        paint.setColor(color);
        float tamanho = 150;
        paint.setTextSize(tamanho);

        canvas.drawText(text, x, y, paint);
    }

    public boolean isPressed(double posicaoToqueX, double posicaoToqueY){
        if (posicaoToqueX > x && posicaoToqueX < x + bitmap.getWidth() &&
        posicaoToqueY > y && posicaoToqueY < y + bitmap.getHeight()){
            return true;
        }

        return false;
    }

    public boolean getIsPaused() {
        return isPaused;
    }

    public void changeIsPaused(){
        isPaused = !isPaused;
    }
}
