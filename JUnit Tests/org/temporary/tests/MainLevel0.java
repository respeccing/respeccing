/**
 * File creation: Nov 4, 2009 5:59:16 PM
 * 
 * Copyright (C) 2005-2009 AtKaaZ <atkaaz@users.sourceforge.net>
 * Copyright (C) 2005-2009 UnKn <unkn@users.sourceforge.net>
 * 
 * This file and its contents are part of DeMLinks.
 * 
 * DeMLinks is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DeMLinks is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with DeMLinks. If not, see <http://www.gnu.org/licenses/>.
 */


package org.temporary.tests;



import org.dml.tools.RunTime;
import org.dml.tools.StaticInstanceTracker;
import org.references.Reference;
import org.references.method.MethodParams;



/**
 * must not call super() on some methods because remember that the VarLevel is
 * Overridden on the next level<br>
 * 
 * 2. VarLevel field in each subclass(or level) must be declared private and all
 * *VarLevelX* methods must be overridden without calling their super, and they
 * will operate on this private field.<br>
 * 3. there is only one VarLevel instance no matter at which level we are, once
 * the class is instantiated
 * 4. as said in 2. you must override w/o calling super, the following methods:
 * getVarLevelX, checkVarLevelX, newVarLevelX;
 * 5. always call super on setVarLevelX and always override it
 */
public abstract class MainLevel0 extends StaticInstanceTracker {
	
	// defaults are no params, or no params means use defaults
	protected static MethodParams<Object>	emptyParamList			= null;
	
	// var to see if we used init() instead of initMainLevel(...); its only
	// purpose is to prevent init() usage
	private boolean							inited					= false;
	
	// true if we inited a default 'var' so we know to deInit it
	// we won't deInit passed 'var' param
	protected boolean						usingOwnVarLevel		= false;
	
	private static MethodParams<Object>		defaults				= null;
	
	protected static MethodParams<Object>	temporaryLevel1Params	= null;
	
	
	public MainLevel0() {

		// since this is static:
		if ( null == emptyParamList ) {
			emptyParamList = new MethodParams<Object>();
			emptyParamList.init();
			// FIXME: when is this deInited? should be when last instance is
			// deInited, but can't compare class names, could be Level3 and
			// Level2 classes, but we can't deInit on last Level3.deInit
			// true that a deInit is not really needed, but as a concept...when?
		}
		
		if ( null == temporaryLevel1Params ) {
			temporaryLevel1Params = new MethodParams<Object>();
			temporaryLevel1Params.init();
			// FIXME: when's this deInited also?
		}
	}
	
	/**
	 * must override this in each level AND call super at end or beginning<br>
	 * 
	 * @param obj
	 */
	abstract protected void setVarLevelX( Object obj );
	
	/**
	 * must override this in each Level w/o calling super, and use the right
	 * type<br>
	 */
	abstract protected Object newVarLevelX();
	
	/**
	 * must override in each level w/o calling super<br>
	 * this method must make sure the obj is of VarLevelX type depending on the
	 * current variable type used in the class<br>
	 * forgetting to override this may cause unexpected bugs but you can see it
	 * when you get NullPointerException when calling a method only available in
	 * a later level
	 * 
	 * @param obj
	 */
	abstract protected void checkVarLevelX( Object obj );
	
	/**
	 * must override this, and don't call super
	 * 
	 * @return
	 */
	abstract protected Object getVarLevelX();
	
	/**
	 * DO NOT override this, ever
	 * 
	 * @param varAny
	 *            the VarLevel at this level
	 * @param params
	 *            that must be passed to a super.initMainLevel()
	 * @return
	 */
	protected MethodParams<Object> internalInit( Object varAny,
			MethodParams<Object> params ) {

		// Object refToVarAny = varAny;// even if null
		MethodParams<Object> refToParams = params;
		if ( null == refToParams ) {
			// empty means use defaults
			refToParams = emptyParamList;
		}
		RunTime.assertNotNull( refToParams );
		
		// optional param, but the top level will supply this if toplevel exists
		// or the user will supply this if it is so desired but he will be
		// responsible for it being inited/deinited
		Reference<Object> ref = refToParams.get( PossibleParams.varLevelAll );
		if ( null == ref ) {
			// no VarLevel1 given thus must use defaults for VarLevel1
			// maybe use some defaults ie. homeDir value to default
			if ( null == this.getVarLevelX() ) {
				// refToVarAny =
				this.newVarLevelX();
				// varAny = new VarLevel2();// 1
			}
			usingOwnVarLevel = true;// 2
			

			// TODO avoid new-ing this every time; clone does the new
			MethodParams<Object> moo = this.getDefaults().getClone();
			// using defaults but overwriting them with params
			moo.mergeWith( refToParams, true );
			( (VarLevel1)this.getVarLevelX() ).init( moo );// 3 FIXME: do not
			// spec type
			moo.deInit();
			
			// set this for Level1
			synchronized ( temporaryLevel1Params ) {
				temporaryLevel1Params.set( PossibleParams.varLevelAll,
						this.getVarLevelX() );
			}
			refToParams = temporaryLevel1Params;
		} else {
			Object obj = ref.getObject();
			RunTime.assertNotNull( obj );
			this.checkVarLevelX( obj );
			// varAny = obj;
			this.setVarLevelX( obj );
		}
		

		return refToParams;
	}
	
	/**
	 * override this and call internalInit(...) then super with the returned
	 * value<br>
	 * ie. super.initMainLevel( this.internalInit( var1, params ) );
	 * 
	 * @param params
	 */
	public void initMainLevel( MethodParams<Object> params ) {

		RunTime.assertNotNull( params );
		inited = true;// first
		this.init();// second
	}
	
	protected MethodParams<Object> getDefaults() {

		if ( null == defaults ) {
			defaults = new MethodParams<Object>();
			defaults.init();
		}
		
		return defaults;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dml.tools.StaticInstanceTracker#done()
	 */
	@Override
	protected void done() {

		inited = false;// first
		
		if ( null != this.getVarLevelX() ) {
			// could be not yet inited due to throws in initMainLevel()
			if ( usingOwnVarLevel ) {
				// we inited it, then we deinit it
				usingOwnVarLevel = false;// 1 //this did the trick
				( (StaticInstanceTracker)this.getVarLevelX() ).deInit();// 2
				// not setting it to null, since we might use it on the next
				// call
			} else {
				// var1 = null;
				this.setVarLevelX( null );
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dml.tools.StaticInstanceTracker#start()
	 */
	@Override
	protected void start() {

		if ( !inited ) {
			// called init() which is not supported
			RunTime.badCall( "please don't use init() w/o params" );
			// this.initMainLevel( null );this won't work, init() recursion
		}
	}
}