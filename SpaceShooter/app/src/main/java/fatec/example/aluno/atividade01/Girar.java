package fatec.example.aluno.atividade01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class Girar {

    public static Bitmap RotateBitmap(Bitmap imagem, float angle, Context context)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(imagem, 0, 0, imagem.getWidth(), imagem.getHeight(), matrix, true);
    }
}
