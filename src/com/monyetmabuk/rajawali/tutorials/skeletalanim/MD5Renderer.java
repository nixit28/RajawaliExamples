package com.monyetmabuk.rajawali.tutorials.skeletalanim;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.animation.mesh.SkeletalAnimationObject3D;
import rajawali.animation.mesh.SkeletalAnimationSequence;
import rajawali.lights.DirectionalLight;
import rajawali.parser.AParser.ParsingException;
import rajawali.parser.md5.MD5AnimParser;
import rajawali.parser.md5.MD5MeshParser;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

import com.monyetmabuk.rajawali.tutorials.R;
import com.monyetmabuk.rajawali.tutorials.RajawaliExampleActivity;

public class MD5Renderer extends RajawaliRenderer {
	private DirectionalLight mLight;
	private SkeletalAnimationObject3D mObject;
	
	public MD5Renderer(Context context)
	{
		super(context);
		setFrameRate(60);
	}
	
	protected void initScene()
	{
		mLight = new DirectionalLight(1f, -0.2f, -1.0f); // set the direction
		mLight.setColor(1.0f, 1.0f, 1.0f);
		mLight.setPower(2);

		getCurrentCamera().setY(1);
		getCurrentCamera().setZ(6);
		
		try {
			MD5MeshParser meshParser = new MD5MeshParser(this, R.raw.boblampclean_mesh);
			meshParser.parse();
			
			MD5AnimParser animParser = new MD5AnimParser("attack2", this, R.raw.boblampclean_anim);
			animParser.parse();
			
			SkeletalAnimationSequence sequence = (SkeletalAnimationSequence)animParser.getParsedAnimationSequence();
			
			mObject = (SkeletalAnimationObject3D)meshParser.getParsedAnimationObject();
			mObject.setAnimationSequence(sequence);
			mObject.addLight(mLight);
			mObject.setScale(.04f);
			mObject.setRotY(180);
			mObject.play();
			
			addChild(mObject);
		} catch(ParsingException e) {
			e.printStackTrace();
		}
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
	}
}
