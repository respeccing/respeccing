/**
 * File creation: Jun 3, 2009 8:57:07 PM
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


package org.dml.database;



import org.dml.JUnits.Consts;
import org.dml.database.bdb.BerkeleyDB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sleepycat.je.DatabaseException;



/**
 * 
 *
 */
public class DBTest {
	
	BerkeleyDB	bdb;
	
	@Before
	public void setUp() throws DatabaseException {

		bdb = new BerkeleyDB( Consts.BDB_ENV_PATH );
		
	}
	
	@After
	public void tearDown() {

		bdb.deInit();
	}
	
	@Test
	public void testInitDeInit() {

		// @Before and @After kicking in;
	}
	

}