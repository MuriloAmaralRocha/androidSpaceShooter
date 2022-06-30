package fatec.example.aluno.atividade01;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Joystick {

    private int posicaoCentroCirculoExteriorX;
    private int posicaoCentroCirculoExteriorY;
    private int posicaoCentroCirculoInteriorX;
    private int posicaoCentroCirculoInteriorY;

    private int raioCirculoExterior;
    private int raioCirculoInterior;

    private Paint corCirculoInterior;
    private Paint corCirculoExterior;
    private boolean isPressed = false;
    private double joystickCenterToTouchDistance;
    private double atorX;
    private double atorY;

    public Joystick(int posicaoCentroX, int posicaoCentroY, int raioCirculoExterior, int raioCirculoInterior) {

        // Outer and inner circle make up the joystick
        posicaoCentroCirculoExteriorX = posicaoCentroX;
        posicaoCentroCirculoExteriorY = posicaoCentroY;
        posicaoCentroCirculoInteriorX = posicaoCentroX;
        posicaoCentroCirculoInteriorY = posicaoCentroY;

        // Radii of circles
        this.raioCirculoExterior = raioCirculoExterior;
        this.raioCirculoInterior = raioCirculoInterior;

        // paint of circles
        corCirculoExterior = new Paint();
        corCirculoExterior.setColor(Color.GRAY);
        corCirculoExterior.setStyle(Paint.Style.FILL_AND_STROKE);

        corCirculoInterior = new Paint();
        corCirculoInterior.setColor(Color.BLUE);
        corCirculoInterior.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void draw(Canvas canvas) {
        // Draw outer circle
        canvas.drawCircle(
                posicaoCentroCirculoExteriorX,
                posicaoCentroCirculoExteriorY,
                raioCirculoExterior,
                corCirculoExterior
        );

        // Draw inner circle
        canvas.drawCircle(
                posicaoCentroCirculoInteriorX,
                posicaoCentroCirculoInteriorY,
                raioCirculoInterior,
                corCirculoInterior
        );
    }

    public void update() {
        updatePosicaoCirculo();
    }

    private void updatePosicaoCirculo() {
        posicaoCentroCirculoInteriorX = (int) (posicaoCentroCirculoExteriorX + atorX * raioCirculoExterior);
        posicaoCentroCirculoInteriorY = (int) (posicaoCentroCirculoExteriorY + atorY * raioCirculoExterior);
    }

    public void setAtor(double touchPositionX, double touchPositionY) {
        double deltaX = touchPositionX - posicaoCentroCirculoExteriorX;
        double deltaY = touchPositionY - posicaoCentroCirculoExteriorY;
        double deltaDistance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        if(deltaDistance < raioCirculoExterior) {
            atorX = deltaX/ raioCirculoExterior;
            atorY = deltaY/ raioCirculoExterior;
        } else {
            atorX = deltaX/deltaDistance;
            atorY = deltaY/deltaDistance;
        }
    }

    public boolean isPressed(double posicaoToqueX, double posicaoToqueY) {
        joystickCenterToTouchDistance = Math.sqrt(
                Math.pow(posicaoCentroCirculoExteriorX - posicaoToqueX, 2) +
                Math.pow(posicaoCentroCirculoExteriorY - posicaoToqueY, 2)
        );
        return joystickCenterToTouchDistance < raioCirculoExterior;
    }

    public boolean getIsPressed() {
        return isPressed;
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public double getAtorX() {
        return atorX;
    }

    public double getAtorY() {
        return atorY;
    }

    public void resetAtor() {
        atorX = 0;
        atorY = 0;
    }
}
