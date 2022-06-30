package fatec.example.aluno.atividade01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.MediaPlayer;

public class Tiro {
    protected static final int VELOCIDADE_TIRO =40;
    private int velocidadeX;
    private Bitmap bitmap;
    private int x=75;
    private int y=50;
    private int maxX;
    private int minX;
    private int speed = 2;
    private Rect detectCollision;
    private Player player;

    public Tiro(Player player, Context context) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tiro2);
        bitmap =  Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/3, bitmap.getHeight()/3, false);
        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
        this.player = player;

        x = player.getX() + player.getBitmap().getWidth() - bitmap.getWidth();
        y = player.getY() + (player.getBitmap().getHeight()/2) - (bitmap.getHeight()/2);

        velocidadeX = player.getDirecaoX()*VELOCIDADE_TIRO;

        if (velocidadeX < 0){
            bitmap = Girar.RotateBitmap(bitmap, 180, context);
        }
    }

    public void update(){
        x += velocidadeX;

       detectCollision.left = x;
       detectCollision.top = y;
       detectCollision.right = x + bitmap.getWidth();
       detectCollision.bottom = y + bitmap.getHeight();
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

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public void setX(int i) {
        this.x = i;
    }
    public void setY(int i) {
        this.y = i;
    }
}
