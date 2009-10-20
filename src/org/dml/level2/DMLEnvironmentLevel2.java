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


package org.dml.level2;



import java.io.File;

import org.dml.level1.NodeJID;
import org.dml.storagewrapper.BerkeleyDBStorageLevel1;
import org.dml.storagewrapper.StorageException;
import org.dml.storagewrapper.StorageWrapperLevel2;
import org.dml.tools.RunTime;
import org.javapart.logger.Log;
import org.references.ObjRefsList;
import org.references.Position;



/**
 * 
 *
 */
public class DMLEnvironmentLevel2 implements StorageWrapperLevel2 {
	
	public final static String								DEFAULT_BDB_ENVIRONMENT_HOMEDIR	= "."
																									+ File.separator
																									+ "bin"
																									+ File.separator
																									+ "mainEnv"
																									+ File.separator;
	// FIXME: should be StorageWrapper type but upsets my F3 key
	private final BerkeleyDBStorageLevel1							Storage;
	
	private final static ObjRefsList<DMLEnvironmentLevel2>	ALL_INSTANCES					= new ObjRefsList<DMLEnvironmentLevel2>();
	
	/**
	 * @param envHomeDir
	 * @param wipeEnvFirst
	 *            true if to erase all data prior to init!
	 * @throws StorageException
	 */
	public static final DMLEnvironmentLevel2 getNew( String envHomeDir,
			boolean wipeEnvFirst ) throws StorageException {

		DMLEnvironmentLevel2 env = new DMLEnvironmentLevel2( envHomeDir,
				wipeEnvFirst );
		if ( ALL_INSTANCES.addFirst( env ) ) {
			RunTime.Bug( "couldn't have already existed!" );
		}
		return env;
	}
	
	/**
	 * generic constructor using default BDB environment
	 * 
	 * @throws StorageException
	 */
	public static DMLEnvironmentLevel2 getNew() throws StorageException {

		return getNew( DEFAULT_BDB_ENVIRONMENT_HOMEDIR, false );
	}
	
	public static DMLEnvironmentLevel2 getNew( boolean wipeEnvFirst )
			throws StorageException {

		return getNew( DEFAULT_BDB_ENVIRONMENT_HOMEDIR, wipeEnvFirst );
	}
	
	/**
	 * private constructor
	 * 
	 * @param envHomeDir
	 * @param wipeEnvFirst
	 *            this should be false, unless inside a JUnit; will delete all
	 *            data
	 * @throws StorageException
	 */
	protected DMLEnvironmentLevel2( String envHomeDir, boolean wipeEnvFirst )
			throws StorageException {

		RunTime.assertNotNull( envHomeDir, wipeEnvFirst );
		Storage = new BerkeleyDBStorageLevel1( envHomeDir, wipeEnvFirst );
	}
	
	public static final void deInitAll() {

		Log.entry();
		DMLEnvironmentLevel2 iter;
		while ( null != ( iter = ALL_INSTANCES.getObjectAt( Position.FIRST ) ) ) {
			iter.deInit();
			ALL_INSTANCES.removeObject( iter );
		}
		RunTime.assertTrue( ALL_INSTANCES.isEmpty() );
	}
	
	/**
	 * 
	 */
	public void deInit() {

		Log.entry();
		Storage.deInit();
	}
	
	/**
	 * there's a one to one mapping between NodeID and NodeJID<br>
	 * given the NodeID return its NodeJID<br>
	 * NodeIDs are on some kind of Storage<br>
	 * 
	 * @param nodeID
	 * @return NodeJID
	 * @throws StorageException
	 */
	public NodeJID getNodeJID( NodeID nodeID ) throws StorageException {

		RunTime.assertNotNull( nodeID );
		return Storage.getNodeJID( nodeID );
	}
	
	/**
	 * eget=ensure get<br>
	 * make a new one if it doesn't exist<br>
	 * but if exists don't complain<br>
	 * 
	 * @param theJID
	 *            this JID and this Node will be mapped 1 to 1
	 * @return never null
	 * @throws StorageException
	 */
	public NodeID ensureNodeID( NodeJID theJID ) throws StorageException {

		RunTime.assertNotNull( theJID );
		return Storage.ensureNodeID( theJID );
	}
	
	/**
	 * @param identifiedByThisJID
	 * @return
	 * @throws StorageException
	 */
	public NodeID getNodeID( NodeJID identifiedByThisJID )
			throws StorageException {

		return Storage.getNodeID( identifiedByThisJID );
	}
	
	/**
	 * @param fromJID
	 * @return
	 * @throws StorageException
	 */
	public NodeID createNodeID( NodeJID fromJID ) throws StorageException {

		return Storage.createNodeID( fromJID );
	}
	
	/**
	 * level 3
	 */
	public boolean ensureGroup( NodeID first, NodeID second )
			throws StorageException {

		return Storage.ensureGroup( first, second );
	}
}