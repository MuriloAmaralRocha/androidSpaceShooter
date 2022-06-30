package fatec.example.aluno.atividade01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Player {
    private Bitmap bitmap;

    private int x;
    private int y;
    private int speedX;
    private int speedY;
    private boolean isAlive = true;

    private Rect detectCollision;
    private Context context;

    private int maxY;
    private int minY;
    private int maxX;
    private int minX;

    private final int MIN_SPEED = 2;
    private final int MAX_SPEED = 20;
    private boolean turnedRight = true;

    public Player(Context context, int screenX, int screenY)
    {
        x = 75;
        y = 50;
        speedX = 0;
        bitmap = BitmapFactory.decodeResource(context.getResources(),  R.drawable.player);

        maxY = screenY - bitmap.getHeight();
        minY = 0;

        maxX = screenX - bitmap.getWidth();
        minX = 0;

        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());

        this.context = context;
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

    public int getSpeedX() {
        if (speedX < MIN_SPEED) return MIN_SPEED;

        return speedX;
    }

    public void update(Joystick joystick) {
        speedX = (int)(joystick.getAtorX() * MAX_SPEED);
        speedY = (int)(joystick.getAtorY() * MAX_SPEED);

        if (speedX > 0 && !turnedRight) {
            bitmap = Girar.RotateBitmap(bitmap, 180, context);
            turnedRight = true;
        }
        else if (speedX < 0 && turnedRight) {
            bitmap = Girar.RotateBitmap(bitmap, 180, context);
            turnedRight = false;
        }

        x += speedX;
        y += speedY;

        if (y < minY){
            y = minY;
        }
        if (y > maxY){
            y = maxY;
        }
        if (x < minX){
            x = minX;
        }
        if (x > maxX){
            x = maxX;
        }

        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public int getDirecaoX() {
        if (turnedRight) return 1;

        return -1;
    }
    public boolean getIsAlive(){
        return isAlive;
    }
    public void Kill(){
        isAlive = false;
    }
}
