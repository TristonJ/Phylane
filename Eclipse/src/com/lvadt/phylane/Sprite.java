package com.lvadt.phylane;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

public class Sprite {
	private static final String TAG = "Sprite";
	Resources res;
	Bitmap bmp;
	private Context context;
	float width, height;
	float x, y;
	float prevX;
	float rot = 0;
	Boolean bRot = false;;
	int texId;
	
	public Sprite(Context c, GL10 gl, String fileName, float posx, float posy){
		context = c;
		res = c.getResources();
		loadTexture(gl, fileName);
		x = posx;
		y = posy;
	}
	
	public void setRotation(float rotation){
		rot = rotation;
		bRot = true;
	}
	
	private int newTextureId(GL10 gl){
		int[] temp = new int[1];
		gl.glGenTextures(1, temp, 0);
		return temp[0];
	}
	
	private void loadTexture(GL10 gl, String fileName){
		texId = newTextureId(gl);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);
		
		//Use assets and input stream to load bitmap
		AssetManager assets = context.getAssets();
		InputStream is = null;
		try{
			is = assets.open(fileName);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = true;
		
		if(is != null){
			try{
				bmp = BitmapFactory.decodeStream(is, null, options);
				is.close();
			}
			catch(IOException e){
				e.printStackTrace();
			}
			
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
			width = bmp.getWidth();
			height = bmp.getHeight();
		}
		else{
			Log.i(TAG, "Error Loading " + fileName);
		}
		
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_REPLACE);
		gl.glTexEnvx(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);
		
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
	}
	
	public void setPos(float posx, float posy){
		prevX = x;
		x = posx;
		y = posy;
	}
	
	//Render by creating a "square" to display the bitmap on
	void render(GL10 gl){
		float ver[] = new float[12];
		float tex[] = new float[12];
		
		ver[0] = x;				ver[1] = y;
		ver[2] = x;				ver[3] = y + height;
		ver[4] = x + width;		ver[5] = y + height;
		ver[6] = x + width;		ver[7] = y;
		ver[8] = x;				ver[9] = y;
		ver[10] = x + width;	ver[11] = y + height;
		
		float u1 = 0.0f;
		float u2 = 1.0f;
		float v1 = 0.0f;
		float v2 = 1.0f;
		
		tex[0] = u1;			tex[1] = v1;
		tex[2] = u1;			tex[3] = v2;
		tex[4] = u2;			tex[5] = v2;
		tex[6] = u2;			tex[7] = v1;
		tex[8] = u1;			tex[9] = v1;
		tex[10] = u2;			tex[11] = v2;
		
		//Vertex Buffer
		ByteBuffer vbb = ByteBuffer.allocateDirect(ver.length * 6);
		vbb.order(ByteOrder.nativeOrder());
		FloatBuffer vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(ver);
		vertexBuffer.position(0);
		
		//Texture Buffer
		ByteBuffer tbb = ByteBuffer.allocateDirect(tex.length * 6);
		tbb.order(ByteOrder.nativeOrder());
		FloatBuffer textureBuffer = tbb.asFloatBuffer();
		textureBuffer.put(tex);
		textureBuffer.position(0);
		
		//Draw
		gl.glPushMatrix();
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glActiveTexture(GL10.GL_TEXTURE0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 6);
		gl.glPopMatrix();
	}
	
	void destroy(){
		//Recycle the bitmap
		bmp.recycle();
	}
}
