/**
 * File creation: Oct 20, 2009 12:22:26 AM
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



import org.junit.After;
import org.junit.Before;



/**
 * 
 *
 */
public class DMLEnvironmentLevel1Test {
	
	DMLEnvironmentLevel1	dml1;
	
	@Before
	public void setUp() {

		dml1 = new DMLEnvironmentLevel1();
		dml1.init();
		DMLEnvironmentLevel1.getNew();
		
	}
	
	@After
	public void tearDown() {

		dml1.deInit();
		dml1 = null;
	}
}