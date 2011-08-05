/*
 * Copyright (c) 2003-2009 jMonkeyEngine
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package org.temporary.tests.threeD;



import com.jme.app.SimpleGame;
import com.jme.bounding.OrientedBoundingBox;
import com.jme.image.Texture;
import com.jme.input.InputHandler;
import com.jme.input.action.InputAction;
import com.jme.input.action.InputActionEvent;
import com.jme.intersection.BoundingCollisionResults;
import com.jme.intersection.CollisionResults;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Text;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Sphere;
import com.jme.scene.state.TextureState;
import com.jme.util.TextureManager;
import com.jme.util.Timer;



/**
 * <code>TestCollision</code>
 * 
 * @author Mark Powell
 * @version $Id: TestCollision.java 4336 2009-05-03 20:57:01Z christoph.luder $
 */
public class TestCollision
		extends
		SimpleGame
{
	
	private TriMesh				t;
	
	private TriMesh				t2;
	
	Text						text;
	
	private Node0				scene;
	
	private final Quaternion	rotQuat	= new Quaternion();
	
	private float				angle	= 0;
	
	private final Vector3f		axis	= new Vector3f(
														1,
														0,
														0 );
	
	private float				tInc	= -40.0f;
	
	private float				t2Inc	= -10.0f;
	
	private CollisionResults	results;
	
	private Node0				n1, n2;
	
	
	/**
	 * Entry point for the test,
	 * 
	 * @param args
	 */
	public static
			void
			main(
					String[] args )
	{
		TestCollision app = new TestCollision();
		app.setConfigShowMode( ConfigShowMode.ShowIfNoConfig );
		app.start();
	}
	

	@Override
	protected
			void
			simpleUpdate()
	{
		if ( timer.getTimePerFrame() < 1 )
		{
			angle = angle
					+ ( timer.getTimePerFrame() * 1 );
			if ( angle > 360 )
			{
				angle = 0;
			}
		}
		
		rotQuat.fromAngleAxis(
								angle,
								axis );
		
		t.setLocalRotation( rotQuat );
		
		t.getLocalTranslation().y += tInc
										* timer.getTimePerFrame();
		t2.getLocalTranslation().x += t2Inc
										* timer.getTimePerFrame();
		
		if ( t.getLocalTranslation().y > 40 )
		{
			t.getLocalTranslation().y = 40;
			tInc *= -1;
		}
		else
			if ( t.getLocalTranslation().y < -40 )
			{
				t.getLocalTranslation().y = -40;
				tInc *= -1;
			}
		
		if ( t2.getLocalTranslation().x > 40 )
		{
			t2.getLocalTranslation().x = 40;
			t2Inc *= -1;
		}
		else
			if ( t2.getLocalTranslation().x < -40 )
			{
				t2.getLocalTranslation().x = -40;
				t2Inc *= -1;
			}
		

		results.removeAll();
		n1.calculateCollisions(
								scene,
								results );
		
		if ( n1.hasCollision(
								scene,
								false ) )
		{
			// logger.info("hasCollision also reports true");
		}
	}
	

	public
			Timer
			getTimer()
	{
		return timer;
	}
	

	public
			void
			setPause(
						boolean newPause )
	{
		pause = newPause;
	}
	

	/**
	 * builds the trimesh.
	 * 
	 * @see com.jme.app.SimpleGame#initGame()
	 */
	@Override
	protected
			void
			simpleInitGame()
	{
		results = new BoundingCollisionResults()
		{
			
			@Override
			public
					void
					processCollisions()
			{
				if ( this.getNumber() > 0 )
				{
					text.print( "Collision: YES" );
					TestCollision.this.setPause( true );
				}
				else
				{
					text.print( "Collision: NO" );
				}
			}
		};
		
		InputAction buttonAction = new InputAction()
		{
			
			@Override
			public
					void
					performAction(
									InputActionEvent evt )
			{
				if ( TestCollision.this.getTimer().getTime() % 7 == 0 )
				{
					TestCollision.this.setPause( false );
				}
			}
		};
		input.addAction(
							buttonAction,
							InputHandler.DEVICE_ALL,
							1/* right click */,
							InputHandler.AXIS_NONE,
							true );
		
		display.setTitle( "Collision Detection" );
		cam.setLocation( new Vector3f(
										0.0f,
										0.0f,
										75.0f ) );
		cam.update();
		
		text = Text.createDefaultTextLabel(
											"Text Label",
											"Collision: No" );
		text.setLocalTranslation( new Vector3f(
												1,
												60,
												0 ) );
		statNode.attachChild( text );
		
		scene = new Node0(
							"3D Scene Root" );
		
		Vector3f max = new Vector3f(
										5,
										5,
										5 );
		Vector3f min = new Vector3f(
										-5,
										-5,
										-5 );
		
		n1 = new Node0(
						"Node 1" );
		n2 = new Node0(
						"Node 2" );
		
		t = new Sphere(
						"my sphere",
						15,
						15,
						1 );
		

		Vector3f trans1 = new Vector3f(
										1.5345f,
										-0.0397f,
										0.0f );
		t.setLocalTranslation( trans1 );
		Quaternion q1 = new Quaternion(
										0.1288f,
										-0.3779f,
										-0.8533f,
										0.3353f );
		t.setLocalRotation( q1 );
		Vector3f scale1 = new Vector3f(
										3.76f,
										1.41f,
										0.84f );
		t.setLocalScale( scale1 );
		// BoundingSphere x = new BoundingSphere();
		// BoundingVolume y = new BoundingSphere();
		// y = x.transform( q1.clone(), trans1.clone(), scale1.clone() );
		
		// target.setModelBound( new OrientedBoundingBox() );
		t.setModelBound( new OrientedBoundingBox() );
		t.updateModelBound();
		
		// t = new Box("Box 1", min, max);
		// t.setModelBound(new OrientedBoundingBox());
		// t.updateModelBound();
		// t.setLocalTranslation(new Vector3f(0, 30, 0));
		// t.setLocalScale(new Vector3f(1,2,3));
		
		t2 = new Box(
						"Box 2",
						min,
						max );
		t2.setModelBound( new OrientedBoundingBox() );
		t2.updateModelBound();
		t2.setLocalTranslation( new Vector3f(
												30,
												0,
												0 ) );
		n1.attachChild( t );
		n2.attachChild( t2 );
		scene.attachChild( n1 );
		scene.attachChild( n2 );
		
		TextureState ts = display.getRenderer().createTextureState();
		ts.setEnabled( true );
		ts.setTexture( TextureManager.loadTexture(
													TestCollision.class
															.getClassLoader()
															.getResource(
																			"jmetest/data/images/Monkey.jpg" ),
													Texture.MinificationFilter.BilinearNearestMipMap,
													Texture.MagnificationFilter.Bilinear ) );
		
		scene.setRenderState( ts );
		rootNode.attachChild( scene );
	}
}