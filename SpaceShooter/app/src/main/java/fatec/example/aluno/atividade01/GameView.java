package fatec.example.aluno.atividade01;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameView extends SurfaceView implements Runnable {

    private int larguraTela;
    private int alturaTela;

    volatile boolean playing;
    private final Joystick joystick;
    private  Thread gameThread = null;
    private Player player;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    static MediaPlayer mediaPlayer;


    private List<Tiro> tiros = new ArrayList<Tiro>();
    private List<Enemy> enemies = new ArrayList<Enemy>();
    private List<Boom> explosoes = new ArrayList<Boom>();

    private ArrayList<Star> stars = new ArrayList<Star>();
    private int joystickPointerId = 0;
    private int numeroTiros;
    private GameOver gameOver;
    private Pause pausar;
    private Score score;

    public GameView(Context context, int x, int y) {
        super(context);
        player = new Player(context, x, y);
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.shoot2);
        surfaceHolder = getHolder();
        paint = new Paint();
        larguraTela = x;
        alturaTela = y;

        joystick = new Joystick(300, alturaTela-250, 150, 70);
        gameOver = new GameOver(getContext());
        pausar = new Pause(getContext(), larguraTela, alturaTela);
        score = new Score(getContext());

        int nEstrelas = 100;
        for (int i = 0; i < nEstrelas; i++){
            Star s = new Star(x, y);
            stars.add(s);
        }
    }

    @Override
    public void run() {
        while (playing){
            update();
            draw();
            control();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if(joystickPointerId == event.getPointerId(event.getActionIndex())){
                    joystick.setIsPressed(false);
                    joystick.resetAtor();
                }
                break;

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if(pausar.isPressed((double)event.getX(), (double)event.getY()) && player.getIsAlive()){
                    if (!pausar.getIsPaused()){
                        pausar.changeIsPaused();
                        draw();
                        pause();
                    }
                    else {
                        resume();
                        pausar.changeIsPaused();
                    }
                }
                else if(joystick.getIsPressed()) {
                    numeroTiros++;
                }
                else if(joystick.isPressed((double)event.getX(), (double)event.getY())){
                    joystickPointerId = event.getPointerId(event.getActionIndex());
                    joystick.setIsPressed(true);
                }
                else{
                    numeroTiros++;
                }

                break;

            case MotionEvent.ACTION_MOVE:
                if (joystick.getIsPressed()){
                    joystick.setAtor((double)event.getX(), (double)event.getY());
                }
                break;
        }
        return true;
    }

    private boolean colissionBulletEnemy(Tiro tiro, Enemy enemy){
        if (Rect.intersects(tiro.getDetectCollision(),
                enemy.getDetectCollision())) {
            explosoes.add(new Boom(getContext(), enemy.getX(), enemy.getY()));
            score.aumentarScore(100);

            return true;
        }
        return false;
    }
    private boolean colissionEnemyPlayer(Player player, Enemy enemy){
        if (Rect.intersects(player.getDetectCollision(),
                enemy.getDetectCollision())) {
            explosoes.add(new Boom(getContext(), enemy.getX(), enemy.getY()));

            return true;
        }
        return false;
    }

    private void update(){
        if(!player.getIsAlive()){
            return;
        }

        joystick.update();
        player.update(joystick);

        Enemy.atualizarMaisInimigos();

        if(Enemy.podeSpawnar() && enemies.size() <= Enemy.getMax_Enemies()){
            enemies.add(new Enemy(getContext(), larguraTela, alturaTela));
        }

        while (numeroTiros > 0){
            tiros.add(new Tiro(player, getContext()));
            mediaPlayer.start();
            numeroTiros--;
        }

        for (Enemy enemy : enemies){
            enemy.update(player.getSpeedX());
        }

        for (Tiro tiro : tiros){
            tiro.update();
        }

        Iterator<Enemy> iteratorEnemy = enemies.iterator();
        while (iteratorEnemy.hasNext()){
            Enemy enemy = iteratorEnemy.next();

            if(colissionEnemyPlayer(player, enemy)){
                iteratorEnemy.remove();
                player.Kill();
                continue;
            }

            Iterator<Tiro> iteratorTiro = tiros.iterator();
            while (iteratorTiro.hasNext()){
                Tiro tiro = iteratorTiro.next();

                if(colissionBulletEnemy(tiro, enemy)){
                    iteratorTiro.remove();
                    iteratorEnemy.remove();
                    break;
                }
            }
        }

        for (Star s : stars){
            s.update(player.getSpeedX());
        }

        Iterator<Boom> iteratorBoom = explosoes.iterator();
        while (iteratorBoom.hasNext()){
            Boom boom = iteratorBoom.next();
            if (boom.explosaoContinua()){
                iteratorBoom.remove();
            }
        }
    }

    private void draw(){
        if (surfaceHolder.getSurface().isValid()){
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            paint.setColor(Color.WHITE);

            for (Star s : stars) {
                paint.setStrokeWidth(s.getStarWidth());
                canvas.drawPoint(s.getX(), s.getY(), paint);
            }

            for (Enemy enemy : enemies) {
                canvas.drawBitmap(
                        enemy.getBitmap(),
                        enemy.getX(),
                        enemy.getY(),
                        paint
                );
            }

            for (Tiro tiro : tiros) {
                canvas.drawBitmap(
                        tiro.getBitmap(),
                        tiro.getX(),
                        tiro.getY(),
                        paint
                );

            }

            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint
            );

            for (Boom boom : explosoes){
                canvas.drawBitmap(
                        boom.getBitmap(),
                        boom.getX(),
                        boom.getY(),
                        paint
                );
            }

            pausar.drawButton(canvas);
            joystick.draw(canvas);

            score.draw(canvas);

            if (pausar.getIsPaused()){
                pausar.draw(canvas);
            }

            if (!player.getIsAlive()){
                gameOver.draw(canvas);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control(){
        try {
            gameThread.sleep(17);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void pause(){
        playing = false;
        try {
            gameThread.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void resume(){
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}
