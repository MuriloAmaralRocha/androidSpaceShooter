package fatec.example.aluno.atividade01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Boom {
    private Bitmap bitmap;
    private final double UPDATES = 10;
    private double updatesParaSair = UPDATES;

    private int x;
    private int y;

    public Boom(Context context, int posicaoX, int posicaoY){
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.boom);

        x = posicaoX;
        y = posicaoY;
    }

    public boolean explosaoContinua() {
        if(updatesParaSair <= 0){
            updatesParaSair += UPDATES;
            return true;
        }

        updatesParaSair --;
        return false;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
