package com.lvadt.phylane.graphics;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

import com.lvadt.phylane.model.Level;
import com.lvadt.phylane.activity.Fly;
import com.lvadt.phylane.model.WorldObject;

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
	List<Sprite> objSprites = new ArrayList<Sprite>();
    List<WorldObject> objects = new ArrayList<WorldObject>();

    /**
     * Creates a new GLRenderer object
     * @param c the context
     * @param sv the GLSurfaceView
     * @param l the level to be drawn
     */
	public GLRenderer(Context c, GLSurfaceView sv, Level l){
		context = c;
		surfaceView = sv;
		level = l;
	}
	
	public Sprite getPlaneSprite(){
		return plane;
	}

    /**
     * Loads all of the static world sprites
     * @param gl the GL10 object
     */
	private void addSprite(GL10 gl){
        List<String> names = new ArrayList<String>();
		for(int i = 0; i < level.filenames.length; i++){
			if(names.contains(level.filenames[i]) && !names.isEmpty()){
                //Add a new world object
                objects.add(new WorldObject(level.objX[i], level.objY[i],
                        objSprites.get(names.indexOf(level.filenames[i])).width,
                        objSprites.get(names.indexOf(level.filenames[i])).height,
                        names.indexOf(level.filenames[i])));
            }
            else{
                Log.i("Test", "New Sprite");
                //Add the new file
                names.add(level.filenames[i]);
                //Add the new sprite
                objSprites.add(new Sprite(context, gl, level.filenames[i], level.objX[i], level.objY[i]));
                //Add the new world object
                objects.add(new WorldObject(level.objX[i], level.objY[i],
                        objSprites.get(names.indexOf(level.filenames[i])).width,
                        objSprites.get(names.indexOf(level.filenames[i])).height,
                        names.indexOf(level.filenames[i])));
            }
		}
	}

    /**
     * When the surface is first created load all of the settings and sprites
     * @param gl the GL10 object
     * @param config the EGLConfigs
     */
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		initGLSettings(gl, config);
		//Create sprites then send them to physics engine
		addSprite(gl);
		Fly.getEngine().setObjects(objSprites, objects);

		plane = new Sprite(context, gl, "plane.png", 0.0f, surfaceView.getHeight()/2);
		background = new Sprite(context, gl, level.background, 0.0f, 0.0f);
		if(level.parallax != null){
			parallax = new Sprite(context, gl, level.parallax, 0.0f, 0.0f);
		}
		drawMax = background.width*2;
		drawMin = -background.width/2;
	}

    /**
     * When a frame is being drawn, draw everything in the world,
     * move the parallax effect, and set all of the need view stuff for drawing
     * @param gl the GL10 object
     */
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
				break;
			}
			else{
                //Get the objects reference to a sprite, and then draw
                //Said sprite
                int ref = (objects.get(i).ref);
                objSprites.get(ref).setPos(objects.get(i).x, objects.get(i).y);
				objSprites.get(ref).render(gl);
			}
		}
		plane.render(gl);
	}

    /**
     * When the users screen is rotated or something similar
     * @param gl the GL10 object
     * @param width the new screen width
     * @param height the new screen height
     */
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		float ratio = (float) width/height;
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 25);
	}

    /**
     * Is called when the object is first created. Loads all of the needed
     * openGL settings
     * @param gl the GL10 object
     * @param config the EGLConfigs
     */
	private void initGLSettings(GL10 gl, EGLConfig config){
		gl.glClearColor(0, 0, 0, 0);
		
		gl.glClearDepthf(1.0f);
		gl.glDisable(GL10.GL_DEPTH_TEST);
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}

    /**
     * Destroys this object to make sure sprites are properly disposed of
     */
	public void destroy(){
		plane.destroy();
		background.destroy();
		parallax.destroy();
		for(int i = 0; i < objSprites.size(); i++){
			objSprites.get(i).destroy();
		}
		//Stop thread from continuing
	}
	
	

}
