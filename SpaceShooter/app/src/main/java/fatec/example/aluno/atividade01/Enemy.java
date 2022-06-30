package fatec.example.aluno.atividade01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Enemy {
    private static final double UPDATES_POR_SPAWN = 20;
    private static double updatesParaSpawnar = UPDATES_POR_SPAWN;
    private static final double UPDATES_PARA_INIMIGOS = 20;
    private static double updatesParaAumentarInimigos = UPDATES_PARA_INIMIGOS;
    private static int Max_Enemies = 2;
    public Bitmap bitmap;
    private int x;
    private int y;
    private int speed;
    private int maxX;
    private int minX;
    private int maxY;
    private int minY;
    private boolean isAlive;

    private Rect detectCollision;

    public Enemy(Context context, int screenX, int screenY ){
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);
        maxX = screenX; maxY = screenY;
        minX = 0; minY = 0;
        isAlive = true;

        Random generator = new Random();

        speed = generator.nextInt(6)+10;
        x = maxX;
        y = generator.nextInt(maxY) - bitmap.getHeight();

        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public static boolean podeSpawnar() {
        if(updatesParaSpawnar <= 0){
            updatesParaSpawnar += UPDATES_POR_SPAWN;
            return true;
        }

        updatesParaSpawnar --;
        return false;
    }

    public static void atualizarMaisInimigos() {
        if(updatesParaAumentarInimigos <= 0){
            Enemy.Max_Enemies++;
            updatesParaAumentarInimigos += UPDATES_PARA_INIMIGOS;
            return;
        }

        updatesParaSpawnar --;
    }

    public  void update(int playerSpeed){
        x -= playerSpeed;
        x -= speed;

        if (!isAlive){
            this.x = -230;
            isAlive = true;
        }
        if(x < minX - bitmap.getWidth()){
            Random generator = new Random();

            speed = generator.nextInt(6)+10;
            x = maxX;
            y = generator.nextInt(maxY) - bitmap.getHeight();
        }

        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    public void setX(int x){
        this.x = x;
    }
    public Rect getDetectCollision() {
        return detectCollision;
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
    public int getSpeed() {
        return speed;
    }
    public static int getMax_Enemies() {
        return Max_Enemies;
    }
    public void kill(){ isAlive = false; }
}
