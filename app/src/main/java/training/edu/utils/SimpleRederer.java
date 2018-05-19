package training.edu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import training.edu.droidbountyhunter.ActivityOpenGLFugitivos;
import training.edu.droidbountyhunter.R;

public class SimpleRederer implements GLSurfaceView.Renderer {
    Context context;
    FloatBuffer vertextBuffer;
    FloatBuffer texturaBuffer;
    ShortBuffer indexBuffer;
    int carasLength;

    public SimpleRederer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        // NOTHING HAPPENS...
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d("AR", "superficie modificada: " + width + "x" + height);

        float positivo = ActivityOpenGLFugitivos.distorcion;
        float negativo = ActivityOpenGLFugitivos.distorcion * (-1.0f);

        float vertices[] = {
                negativo, 1f, 0f,
                -1f, -1f, 0f,
                0f, -1f, 0f,
                1f, -1f, 0f,
                positivo, 1f, 0f
        };

        short caras[] = {
                0,1,2,  // primer triángulo formado
                0,2,4,  // segundo triángulo formado
                2,3,4   // tercer triángulo formado
        };

        carasLength = caras.length;

        float textura[] = {
            0f, 0f,
            0f, 1f,
            0.5f, 1f,
            1f, 1f,
            1f, 0f
        };

        // Buffer para los vértices del cuadrado.
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertextBuffer = vbb.asFloatBuffer();
        vertextBuffer.put(vertices);
        vertextBuffer.position(0);

        // Buffer para los vértices del orden de los triángulos (caras)
        ByteBuffer ibb = ByteBuffer.allocateDirect(caras.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();
        indexBuffer.put(caras);
        indexBuffer.position(0);

        // Buffer para la textura
        ByteBuffer tbb = ByteBuffer.allocateDirect(textura.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        texturaBuffer = tbb.asFloatBuffer();
        texturaBuffer.put(textura);
        texturaBuffer.position(0);

        // Establece el puerto de vista actual al nuevo tamaño
        gl.glViewport(0, 0, width, height);
        // Selecciona la matriz de proyección
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // Reinicia la matriz de proyección
        gl.glLoadIdentity();
        // Calcula la proporción del aspecto de la ventana
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);
        // Selecciona la matriz de la vista del modelo
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // Reinicia la matriz de la ventana del modelo
        gl.glLoadIdentity();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Limpia la pantalla y el buffer de profundidad
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // Reemplaza la matriz actual con la matriz identidad
        gl.glLoadIdentity();
        // Traslada 4 unidades en el eje Z.
        gl.glTranslatef(0,0,-7);
        // Dibuja nuestra pirámide
        draw(gl);
    }

    public void cargarTextura(GL10 gl) {
        Bitmap bitmap;
        if(ActivityOpenGLFugitivos.fotoDefault.equalsIgnoreCase("0")) {
            bitmap = PictureTools.decodeSampledBitmapFromUri(ActivityOpenGLFugitivos.foto, 200, 200);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        }
        // creamos un array de texturas y le solicitamos a OpenGL que asigne un id de textura.
        int textureIds[] = new int[1];
        gl.glGenTextures(1, textureIds, 0);
        int textureId = textureIds[0];
        // se habilita l uso de textura y se llena la textura con el id que recién creamos.
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
        // Indicamos que cuando la imagen sea más grande que la superficia, que se pinte un pixel
        // que tenga un color promedio entre los pixeles que más se aproximan
        // al de la superficie.
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        // Indicamos que cuando la imagen sea más pequeña que la superficie, que se pinte un pixel
        // que tenga un color promedio entre los pixeles que más se aproximan
        // al de la superficie.
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        // Por medio de las utilidades de OpenGL cargamos nuestra textura.
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
    }

    public void draw(GL10 gl) {
        // Como primera instancia cargamos la textura a mostrar en nuestra figura
        cargarTextura(gl);

        // Contra las agujas del reloj
        gl.glFrontFace(GL10.GL_CCW);
        // Habilitar el buffer de vértices para la escritura y cuales se usarán para el renderizado
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // Especifica la localización y el formato de los datos de un array de vértices a utilizar para el renderizado
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertextBuffer);
        // Habilita el buffer par ala textura del grafico
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        // Señala dónde se ecuentra el buffer del color
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texturaBuffer);
        // Dibujamos las superficies
        gl.glDrawElements(GL10.GL_TRIANGLES, carasLength, GL10.GL_UNSIGNED_SHORT, indexBuffer);
        // Desactiva el buffer de los vértices
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        // Deshabilita el buffer del color
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    }
}
