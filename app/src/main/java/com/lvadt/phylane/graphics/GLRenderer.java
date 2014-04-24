package com.lvadt.phylane.graphics;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

import com.lvadt.phylane.model.Level;
import com.lvadt.phylane.activity.Fly;

public class GLRenderer implements Renderer{

	//Min and max values for what is being drawn
	float drawMax, drawMin;

	//Last not-rendered texture (It is offscreen behind player)
	int lastRendered = 0;

	//Draw the plane at this location on the screen
	private final float planeDrawAt = 100.0f;
	private Context context;
	private GLSurfaceView surfaceView;

    //The needed sprites, and level object
	Level level;
	Sprite background;
	Sprite parallax = null;
	Sprite plane;
	List<Sprite> objects = new ArrayList<Sprite>();
	
	//Context, SurfaceView, Plane, Background, Objects
	public GLRenderer(Context c, GLSurfaceView sv, Level l){
		context = c;
		surfaceView = sv;
		level = l;
	}
	
	public Sprite getPlaneSprite(){
		return plane;
	}
	
	//Load New static sprite's
	private void addSprite(GL10 gl){
		for(int i = 0; i < level.filenames.length; i++){
			objects.add(new Sprite(context, gl, level.filenames[i], level.objX[i], level.objY[i]));
		}
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		initGLSettings(gl, config);
		//Create sprites then send them to physics engine
		addSprite(gl);
		Fly.getEngine().setObjects(objects);

		plane = new Sprite(context, gl, "plane.png", 0.0f, surfaceView.getHeight()/2);
		background = new Sprite(context, gl, level.background, 0.0f, 0.0f);
		if(level.parallax != null){
			parallax = new Sprite(context, gl, level.parallax, 0.0f, 0.0f);
		}
		drawMax = background.width*2;
		drawMin = -background.width/2;
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {

		gl.glDisable(GL10.GL_DITHER);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glDisable(GL10.GL_CULL_FACE);
		GLU.gluOrtho2D(gl, 0.0f, surfaceView.getWidth(), surfaceView.getHeight(), 0.0f);
		
		//Move "Camera" and Background with plane
		background.x = plane.x-planeDrawAt;
		gl.glTranslatef(-(plane.x-planeDrawAt), 0.0f, 0.0f);
		
		//Render background
		background.render(gl);

        //Render parallax effect
		if(parallax != null){
			//Move parallax half the distance of the plane
			parallax.x = (plane.x - plane.prevX)/2;

            //If the parallax goes beyond screen, put it at front
            if((parallax.x + parallax.width) < (plane.x - planeDrawAt)){
                parallax.x = parallax.x + parallax.width;
            }

            //Draw a second time to the right of the first
			parallax.render(gl);
            parallax.x += parallax.width;
            parallax.render(gl);
            parallax.x -= parallax.width;

		}

		//Draw objects in render distance
		for(int i = lastRendered; i < objects.size(); i++){
			if(objects.get(i).x < drawMin+plane.x){
				lastRendered = i;
			}
			else if(objects.get(i).x > drawMax + plane.x){
				i = objects.size();
			}
			else{
				objects.get(i).render(gl);
			}
		}
		plane.render(gl);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		float ratio = (float) width/height;
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 25);
	}
	
	private void initGLSettings(GL10 gl, EGLConfig config){
		gl.glClearColor(0, 0, 0, 0);
		
		gl.glClearDepthf(1.0f);
		gl.glDisable(GL10.GL_DEPTH_TEST);
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}
	
	public void destroy(){
		plane.destroy();
		background.destroy();
		parallax.destroy();
		for(int i = 0; i < objects.size(); i++){
			objects.get(i).destroy();
		}
		//Stop thread from continuing
	}
	
	

}
