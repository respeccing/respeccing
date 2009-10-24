/**
 * File creation: Oct 23, 2009 8:41:50 AM
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



/**
 * 
 *
 */
public class MainLevel1 extends StaticInstanceTracker implements
		VarLevel1Interface {
	
	protected VarLevel1Interface	var		= null;
	private boolean					inited	= false;
	
	public MainLevel1() {

	}
	
	/**
	 * @param var1
	 *            must be already .init() -ed
	 */
	public void init( VarLevel1Interface var1 ) {

		// FIXME: if the var here is already inited, what happens when I run
		// this.deInit() and this.init() again
		// FIXME: then again if it's not inited, I would need to pass to
		// this.init(var1,...) params to var1.init(...) to init it which means a
		// lot of overloading methods if some params are optional in 3 places +
		// with var1 combination: here, in interface and in var1, also here with
		// var1 combination; assuming w/o var1 would just use the defaults
		RunTime.assertNotNull( var1 );
		var = var1;
		inited = true;
		this.init();
	}
	
	@Override
	public void start() {

		if ( !inited ) {
			// called via .init()
			// var = new VarLevel1();
			// var.init();
			// inited = true;
			RunTime.BadCallError( "you should not call init() w/o params" );
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dml.tools.StaticInstanceTracker#done()
	 */
	@Override
	protected void done() {

		if ( null != var ) {
			var.deInit();
		}
		// var = null;
		System.out.println( this.getClass().getSimpleName() + " done." );
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.temporary.tests.VarLevel1Interface#sayHello()
	 */
	@Override
	public void sayHello() {

		var.sayHello();
		
	}
}
