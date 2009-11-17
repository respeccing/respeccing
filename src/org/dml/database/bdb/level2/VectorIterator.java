/**
 * File creation: Nov 17, 2009 4:39:48 PM
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


package org.dml.database.bdb.level2;



import org.dml.database.bdb.level1.Level1_Storage_BerkeleyDB;
import org.dml.storagewrapper.StorageException;
import org.dml.tools.RunTime;
import org.dml.tools.StaticInstanceTracker;
import org.javapart.logger.Log;
import org.references.method.MethodParams;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.CursorConfig;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;



/**
 * parses data of a key->data primary dbase<br>
 * key=initial<br>
 * data=terminal<br>
 * we iterate only on terminals<br>
 * the iteration order is not guaranteed, ie. the terminals are stored as in a
 * Set, not as in an ordered list; however in practice they're sorted
 * alphabetically so to speak, but shouldn't count on this!<br>
 */
public class VectorIterator<InitialType, TerminalType> extends
		StaticInstanceTracker {
	
	private final Database						db;
	private final InitialType					initialObject;					// key
																				
	private final EntryBinding<InitialType>		initialBinding;
	private final EntryBinding<TerminalType>	terminalBinding;
	private Cursor								cursor					= null;
	
	private TerminalType						currentTerminalObject	= null;
	private final Level1_Storage_BerkeleyDB		bdbL1;
	private TransactionCapsule					txn						= null;
	private DatabaseEntry						deKey					= null;
	private DatabaseEntry						deData					= null;
	
	public VectorIterator( Level1_Storage_BerkeleyDB bdb_L1,
			Database whichPriDB, InitialType initialObject1,
			EntryBinding<InitialType> initialBinding1,
			EntryBinding<TerminalType> terminalBinding1 ) {

		RunTime.assertNotNull( bdb_L1, whichPriDB, initialObject1,
				initialBinding1, terminalBinding1 );
		bdbL1 = bdb_L1;
		db = whichPriDB;
		initialObject = initialObject1;
		initialBinding = initialBinding1;
		terminalBinding = terminalBinding1;
		deKey = new DatabaseEntry();
		initialBinding.objectToEntry( initialObject, deKey );
		deData = new DatabaseEntry();
	}
	
	private final Cursor getCursor() throws DatabaseException {

		if ( null == cursor ) {
			txn = TransactionCapsule.getNewTransaction( bdbL1 );
			cursor = db.openCursor( txn.get(), CursorConfig.READ_COMMITTED );
		}
		RunTime.assertNotNull( cursor );
		return cursor;
	}
	
	/**
	 * you have to call this first, if you call goNext() you get exception
	 * 
	 * @throws DatabaseException
	 */
	public void goFirst() throws DatabaseException {

		deData.setSize( 0 );
		RunTime.assertTrue( deData.getOffset() == 0 );
		OperationStatus ret = this.getCursor().getSearchKey( deKey, deData,
				LockMode.RMW );
		if ( OperationStatus.SUCCESS == ret ) {
			currentTerminalObject = terminalBinding.entryToObject( deData );
		} else {
			currentTerminalObject = null;
		}
	}
	
	public TerminalType now() {

		return currentTerminalObject;
	}
	
	public void goNext() throws DatabaseException {

		RunTime.assertTrue( deData.getOffset() == 0 );
		OperationStatus ret = this.getCursor().getNextDup( deKey, deData,
				LockMode.RMW );
		if ( OperationStatus.SUCCESS == ret ) {
			currentTerminalObject = terminalBinding.entryToObject( deData );
		} else {
			currentTerminalObject = null;
		}
	}
	
	public void goPrev() throws DatabaseException {

		RunTime.assertTrue( deData.getOffset() == 0 );
		OperationStatus ret = this.getCursor().getPrevDup( deKey, deData,
				LockMode.RMW );
		if ( OperationStatus.SUCCESS == ret ) {
			currentTerminalObject = terminalBinding.entryToObject( deData );
		} else {
			currentTerminalObject = null;
		}
	}
	
	/**
	 * @return
	 * @throws DatabaseException
	 */
	public int count() throws DatabaseException {

		return this.getCursor().count();
	}
	
	@Override
	protected void done( MethodParams<Object> params ) {

		this.closeSilently();
	}
	
	private final void close() throws DatabaseException {

		if ( null != cursor ) {
			try {
				cursor.close();
			} catch ( DatabaseException e ) {
				txn = txn.abort();
				throw e;
			} finally {
				cursor = null;
			}
			txn = txn.commit();
		}
	}
	
	/**
	 * 
	 */
	private void closeSilently() {

		try {
			this.close();
		} catch ( DatabaseException e ) {
			Log.thro( e.getLocalizedMessage() );
		}
		
	}
	
	@Override
	protected void start( MethodParams<Object> params ) {

		if ( null != params ) {
			RunTime.badCall( "not accepting any parameters here" );
		}
		try {
			this.goFirst();// init cursor
		} catch ( DatabaseException de ) {
			throw new StorageException( de );
		}
	}
	
	// goLast() is too expensive to implement, due to BDB not giving support for
	// it
}