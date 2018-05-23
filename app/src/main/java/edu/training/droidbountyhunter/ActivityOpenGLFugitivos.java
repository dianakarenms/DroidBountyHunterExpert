package edu.training.droidbountyhunter;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import edu.training.utils.SimpleRederer;

public class ActivityOpenGLFugitivos extends AppCompatActivity {

    private static final String EXTRA_FOTO = "foto";
    private static final String EXTRA_DISTORCIONSUP = "distorcionSup";
    private static final String EXTRA_DISTORCIONINF = "distorcionInf";
    private static final String EXTRA_DEFAULT = "default";

    GLSurfaceView glView;
    public static String foto;
    public static float distorcion, distorcion2;
    public static String fotoDefault;

    public static Intent newIntent(Context packageContext, String pathImage, String texto, String texto2, String cbDefault) {
        Intent intentOpenGL = new Intent();
        intentOpenGL.setClass(packageContext, ActivityOpenGLFugitivos.class);
        intentOpenGL.putExtra(EXTRA_FOTO, pathImage);
        intentOpenGL.putExtra(EXTRA_DISTORCIONSUP, texto);
        intentOpenGL.putExtra(EXTRA_DISTORCIONINF, texto2);
        intentOpenGL.putExtra(EXTRA_DEFAULT, cbDefault);
        return intentOpenGL;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle oExt = this.getIntent().getExtras();
        foto = oExt.getString(EXTRA_FOTO);
        distorcion = Float.parseFloat(oExt.getString(EXTRA_DISTORCIONSUP));
        distorcion2 = Float.parseFloat(oExt.getString(EXTRA_DISTORCIONINF));
        fotoDefault = oExt.getString(EXTRA_DEFAULT);
        glView = new GLSurfaceView(this);
        glView.setRenderer(new SimpleRederer(this));
        setContentView(glView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        glView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glView.onPause();
    }
}
