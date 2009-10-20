/**
 * File creation: May 30, 2009 12:16:28 AM
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


package org.dml.level1;



import org.dml.storagewrapper.BerkeleyDBStorageLevel1;
import org.dml.storagewrapper.StorageWrapperLevel1;
import org.dml.tools.RunTime;
import org.javapart.logger.Log;
import org.references.ObjRefsList;
import org.references.Position;



/**
 * 
 *
 */
public class DMLEnvironmentLevel1 implements StorageWrapperLevel1 {
	
	
	private final StorageWrapperLevel1						Storage;
	
	private final static ObjRefsList<DMLEnvironmentLevel1>	ALL_INSTANCES	= new ObjRefsList<DMLEnvironmentLevel1>();
	
	/**
	 */
	public static DMLEnvironmentLevel1 getNew() {

		return new DMLEnvironmentLevel1();
		
	}
	
	protected DMLEnvironmentLevel1() {

		Storage = new BerkeleyDBStorageLevel1();
		this.init();
	}
	
	public static final void deInitAll() {

		Log.entry();
		DMLEnvironmentLevel1 iter;
		while ( null != ( iter = ALL_INSTANCES.getObjectAt( Position.FIRST ) ) ) {
			iter.deInit();
			if ( ALL_INSTANCES.removeObject( iter ) ) {
				RunTime.Bug( "should not have existed; was removed by a prior call" );
			}
		}
		RunTime.assertTrue( ALL_INSTANCES.isEmpty() );
	}
	
	/**
	 * must not use this environment after a call to this
	 */
	public void deInit() {

		Log.entry();
		Storage.deInit();
		if ( !ALL_INSTANCES.removeObject( this ) ) {
			RunTime.Bug( "should've existed" );
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dml.storagewrapper.StorageWrapperLevel1#init()
	 */
	@Override
	public void init() {

		Storage.init();
		
	}
	
}