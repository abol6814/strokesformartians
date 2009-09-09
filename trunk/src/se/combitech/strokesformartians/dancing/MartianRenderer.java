package se.combitech.strokesformartians.dancing;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Render a pair of tumbling cubes.
 */

class MartianRenderer implements GLSurfaceView.Renderer {
    private boolean mTranslucentBackground;
    private MartianModel m_martian;
    private float mAngle;
	
	public MartianRenderer( Context context, boolean useTranslucentBackground, boolean debugFlag ) {
        mTranslucentBackground = useTranslucentBackground;
        m_martian = new MartianModel( context, debugFlag );
    }

	
    public void onDrawFrame(GL10 gl) {
        /*
         * Usually, the first thing one might want to do is to clear
         * the screen. The most efficient way of doing this is to use
         * glClear().
         */

    	gl.glClearColor( 0.95f, 0.95f, 0.95f, 1.0f );
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        /*
         * Now we're ready to draw some 3D objects
         */

        gl.glMatrixMode( GL10.GL_MODELVIEW );
        gl.glLoadIdentity();
        gl.glTranslatef( 0, 0, -3.0f );
//        gl.glRotatef( mAngle, 0, 1, 0 );
//        gl.glRotatef(mAngle*0.25f,  1, 0, 0);

        gl.glEnableClientState( GL10.GL_VERTEX_ARRAY );
        gl.glEnableClientState( GL10.GL_TEXTURE_COORD_ARRAY );
//        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        m_martian.draw( gl );

//        gl.glRotatef(mAngle*2.0f, 0, 1, 1);
        mAngle++;
        
//        gl.glMatrixMode(GL10.GL_MODELVIEW);
//        gl.glLoadIdentity();
//        gl.glTranslatef(0, 0, -3.0f);
//        gl.glRotatef(mAngle,        0, 1, 0);
//        gl.glRotatef(mAngle*0.25f,  1, 0, 0);
//
//        
//        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
//
//        mCube.draw(gl);
//
//        
//        mAngle += 1.2f;

    }

    public int[] getConfigSpec() {
        if (mTranslucentBackground) {
                // We want a depth buffer and an alpha buffer
                int[] configSpec = {
                        EGL10.EGL_RED_SIZE,      8,
                        EGL10.EGL_GREEN_SIZE,    8,
                        EGL10.EGL_BLUE_SIZE,     8,
                        EGL10.EGL_ALPHA_SIZE,    8,
                        EGL10.EGL_DEPTH_SIZE,   16,
                        EGL10.EGL_NONE
                };
                return configSpec;
            } else {
                // We want a depth buffer, don't care about the
                // details of the color buffer.
                int[] configSpec = {
                        EGL10.EGL_DEPTH_SIZE,   16,
                        EGL10.EGL_NONE
                };
                return configSpec;
            }
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
         gl.glViewport(0, 0, width, height);

         /*
          * Set our projection matrix. This doesn't have to be done
          * each time we draw, but usually a new projection needs to
          * be set when the viewport is resized.
          */

         float ratio = (float) width / height;
         gl.glMatrixMode(GL10.GL_PROJECTION);
         gl.glLoadIdentity();
         gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10 );
//         gl.glOrthof(-1, 1, -1, 1, 1, 10);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        /*
         * By default, OpenGL enables features that improve quality
         * but reduce performance. One might want to tweak that
         * especially on software renderer.
         */
        gl.glDisable(GL10.GL_DITHER);

        /*
         * Some one-time OpenGL initialization can be made here
         * probably based on features of this particular context
         */
         gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                 GL10.GL_FASTEST);

         if (mTranslucentBackground) {
             gl.glClearColor(0,0,0,0);
         } else {
             gl.glClearColor(1,1,1,1);
         }
         gl.glEnable(GL10.GL_CULL_FACE);
         gl.glShadeModel(GL10.GL_SMOOTH);
         gl.glEnable(GL10.GL_DEPTH_TEST);
    }

}