/**
 * File creation: Jun 1, 2009 2:01:51 PM
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


package org.dml.database.bdb;



import org.dml.tools.RunTime;
import org.javapart.logger.Log;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.SecondaryConfig;
import com.sleepycat.je.SecondaryDatabase;



/**
 * encapsulates the SecondaryConfig and the SecondaryDatabase objects into one<br>
 * also makes sure the database isn't open unless it's needed<br>
 * once opened it stays open until silentClose() is called<br>
 */
public class SecondaryDatabaseCapsule {
	
	private final String			secDbName;
	private SecondaryDatabase		secDb	= null;
	private final SecondaryConfig	secDbConf;
	private final Database			primaryDb;
	
	/**
	 * @param string
	 */
	public SecondaryDatabaseCapsule( String dbName, SecondaryConfig secConf,
			@SuppressWarnings( "hiding" ) Database primaryDb ) {

		RunTime.assertNotNull( dbName );
		RunTime.assertFalse( dbName.isEmpty() );
		
		secDbName = dbName;
		this.primaryDb = primaryDb;
		secDbConf = secConf;// can be null if defaults are to be used
	}
	
	/**
	 * @return
	 * @throws DatabaseException
	 */
	public SecondaryDatabase getSecDB() throws DatabaseException {

		if ( null == secDb ) {
			// first time init:
			secDb = BerkeleyDB.getEnvironment().openSecondaryDatabase( null,
					secDbName, primaryDb, secDbConf );
			RunTime.assertNotNull( secDb );
			// Runtime.getRuntime().addShutdownHook(null); bad idea:
			// concurrently called
		}
		return secDb;
	}
	
	@Override
	protected void finalize() throws Throwable {

		Log.exit( "in finalize() for secDbName:" + secDbName );
		this.silentClose();
		super.finalize();
	}
	
	/**
	 * 
	 */
	public void silentClose() {

		secDb = BerkeleyDB.silentCloseAnySecDB( secDb, secDbName );
	}
	
}