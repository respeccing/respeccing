/**
 * 
 * Copyright (C) 2005-2010 AtKaaZ <atkaaz@users.sourceforge.net>
 * Copyright (C) 2005-2010 UnKn <unkn@users.sourceforge.net>
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



package org.dml.tools;



import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.dml.error.BadCallError;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.references.Reference;
import org.references.method.MethodParams;
import org.references.method.PossibleParams;



/**
 * 
 *
 */
public class LevelsTest {
	
	MainLevel1	ml1;
	MainLevel2	ml2;
	MainLevel3	ml3;
	
	@Before
	public void setUp() {

		
		ml3 = new MainLevel3();
		ml1 = new MainLevel1();
		ml2 = new MainLevel2();
	}
	
	@After
	public void tearDown() {

		ml1.deInitSilently();
		ml2.deInitSilently();
		ml3.deInitSilently();
		
		// FIXME: StaticInstanceTracker.deInitAllThatExtendMe();
	}
	
	@Test
	public void test1() {

		

		VarLevel1 v1 = new VarLevel1();
		v1.init( null );
		MethodParams params1 = MethodParams.getNew();
		params1.set( PossibleParams.varLevelAll, v1 );
		ml1.init( params1 );
		ml1.do1();
		
		MethodParams v2params = MethodParams.getNew();
		v2params.set( PossibleParams.homeDir, "home2" );
		VarLevel2 v2 = new VarLevel2();
		v2.init( v2params );
		
		MethodParams params2 = MethodParams.getNew();
		params2.set( PossibleParams.varLevelAll, v2 );
		
		boolean threw = false;
		try {
			ml2.init( params2 );
		} catch ( BadCallError bce ) {
			threw = true;
		}
		assertFalse( threw );
		
		ml2.deInit();
		ml2.init( params2 );
		ml2.showHome();
		
		ml1.deInit();
		try {
			threw = false;
			ml1.init( params2 );
		} catch ( BadCallError bce ) {
			threw = true;
		}
		assertFalse( threw );
		ml1.do1();// level2
		
		try {
			threw = false;
			ml2.init( params1 );
		} catch ( BadCallError bce ) {
			threw = true;
		}
		assertTrue( threw );
		
		params1.set( PossibleParams.varLevelAll, "something" );
		try {
			threw = false;
			ml2.init( params1 );
		} catch ( BadCallError bce ) {
			threw = true;
		}
		assertTrue( threw );
		ml2.deInit();
		
		params1.set( PossibleParams.varLevelAll, null );
		try {
			threw = false;
			ml2.init( params1 );
		} catch ( AssertionError ae ) {
			threw = true;
		}
		assertTrue( threw );
		ml2.deInit();
		
		ml2.init( params2 );
		ml2.showHome();
		params2.set( PossibleParams.homeDir, "home3" );
		params2.remove( PossibleParams.varLevelAll );
		ml2.deInit();
		ml2.init( params2 );
		ml2.showHome();
		
		ml2.deInit();
		ml2.init( params2 );
		ml2.showHome();
		ml2.do1();
		

		ml1.deInit();
		params2.set( PossibleParams.varLevelAll, null );
		try {
			threw = false;
			ml1.init( params2 );
		} catch ( AssertionError ae ) {
			threw = true;
		}
		assertTrue( threw );
		
		// Level 3:
		MethodParams v3params = MethodParams.getNew();
		v3params.set( PossibleParams.homeDir, "homedirL3" );
		VarLevel3 v3 = new VarLevel3();
		v3.init( v3params );
		
		MethodParams params3 = MethodParams.getNew();
		params3.set( PossibleParams.varLevelAll, null );
		try {
			threw = false;
			ml3.init( params3 );
		} catch ( AssertionError ae ) {
			threw = true;
		}
		assertTrue( threw );
		
		params3.set( PossibleParams.varLevelAll, v2 );
		try {
			threw = false;
			ml3.init( params3 );
		} catch ( BadCallError bce ) {
			threw = true;
		}
		assertTrue( threw );
		
		params3.set( PossibleParams.varLevelAll, v1 );
		try {
			threw = false;
			ml3.init( params3 );
		} catch ( BadCallError bce ) {
			threw = true;
		}
		assertTrue( threw );
		ml3.deInit();
		
		params3.set( PossibleParams.varLevelAll, v3 );
		ml3.init( params3 );
		ml3.showHome();
		
		params3.remove( PossibleParams.varLevelAll );
		params3.set( PossibleParams.homeDir, "L3nondefaultHomeDir" );
		ml3.deInit();
		ml3.init( params3 );
		ml3.showHome();
		ml3.do1();
		ml3.deInit();
		
		params1.deInit();
		v2params.deInit();
		params2.deInit();
		v3params.deInit();
		params3.deInit();
	}
	
	@Test
	public void testOwn() {

		VarLevel3 old = null;
		int count2 = 3;
		while ( count2 > 0 ) {
			ml3.init( null );// using own VarLevel
			VarLevel3 vl3 = (VarLevel3)ml3.junitGetVar();
			if ( null == old ) {
				old = vl3;
			}
			
			assertNotNull( vl3 );
			assertTrue( vl3.isInited() );
			ml3.deInit();// will deInit it
			assertFalse( vl3.isInited() );
			assertNotNull( ml3.junitGetVar() );// not null if it's own
			assertTrue( ml3.junitGetVar() == vl3 );
			assertTrue( ml3.junitGetVar() == old );
			old = (VarLevel3)ml3.junitGetVar();
			count2--;
		}
		
		// now using not own, after used own
		VarLevel3 notOwn = new VarLevel3();
		MethodParams params = MethodParams.getNew();
		String homeDir = "homeNotOwn";
		params.set( PossibleParams.homeDir, homeDir );
		notOwn.init( params );
		assertTrue( notOwn.isInited() );
		params.deInit();
		
		MethodParams mlParams = MethodParams.getNew();
		mlParams.set( PossibleParams.varLevelAll, notOwn );
		
		int count = 3;
		while ( count > 0 ) {
			ml3.init( mlParams );
			VarLevel3 newVL3 = (VarLevel3)ml3.junitGetVar();
			assertNotNull( newVL3 );
			assertTrue( newVL3.isInited() );
			ml3.deInit();
			assertTrue( newVL3.isInited() );
			assertNull( ml3.junitGetVar() );// null if it's now own
			assertTrue( newVL3 == notOwn );
			count--;
		}
		
		ml3.init( null );
		VarLevel3 own = (VarLevel3)ml3.junitGetVar();
		assertNotNull( own );
		assertTrue( own.isInited() );
		assertFalse( own == notOwn );
		ml3.showHome();
		ml3.deInit();
		assertNotNull( ml3.junitGetVar() );
		assertTrue( ml3.junitGetVar() == own );
		assertFalse( own.isInited() );
		
		mlParams.deInit();
	}
	
	@Test
	public void test2() {

		
		ml2.init( null );
		VarLevel1 v1 = ml2.junitGetVar();
		assertNotNull( v1 );
		ml2.deInit();
		assertNull( ml3.junitGetVar() );
		
		ml2.init( null );
		VarLevel1 v1_1 = ml2.junitGetVar();
		ml2.deInit();
		
		assertTrue( v1 == v1_1 );// same var used on consecutive inits using
		// defaults
		assertNotNull( ml2.junitGetVar() );// not null after deInit because it's
		// own var
	}
	
	@Test
	public void test3() {

		// the parameters won't get modified
		MethodParams mp = MethodParams.getNew();
		// mp.init( null );
		assertTrue( 0 == mp.size() );
		ml2.init( mp );
		assertTrue( 0 == mp.size() );
		ml1.init( mp );
		assertTrue( 0 == mp.size() );
		ml3.init( mp );
		assertTrue( 0 == mp.size() );
		


		ml3.deInit();
		VarLevel3 vl3 = new VarLevel3();
		MethodParams v3params = MethodParams.getNew();
		// v3params.init( null );
		v3params.set( PossibleParams.homeDir, "homeDir3" );
		vl3.init( v3params );
		v3params.deInit();
		mp.set( PossibleParams.varLevelAll, vl3 );
		assertTrue( 1 == mp.size() );
		
		ml3.init( mp );
		assertTrue( 1 == mp.size() );
		Reference<Object> ref = mp.get( PossibleParams.varLevelAll );
		assertNotNull( ref );
		assertTrue( vl3 == ref.getObject() );
		assertTrue( ml3.junitGetVar() == vl3 );
		ml3.deInit();
		assertNull( ml3.junitGetVar() );
		
		boolean ex = false;
		try {
			ml3.init( null );
		} catch ( Error err ) {
			ex = true;
		}
		assertFalse( ex );
		ml3.deInit();
		
		assertNotNull( ml3.junitGetVar() );
		ml3.init( mp );
		assertTrue( ml3.junitGetVar() == vl3 );
		ml3.deInit();
		assertNull( ml3.junitGetVar() );
		vl3.deInitSilently();
		ml3.init( null );
		assertTrue( ml3.junitGetVar() != vl3 );
		assertNotNull( ml3.junitGetVar() );
		ml3.showHome();
		
		mp.deInit();
	}
	
	@Test
	public void test4() {

		MethodParams mp = MethodParams.getNew();
		// mp.init( null );
		VarLevel3 vl3 = new VarLevel3();
		MethodParams vl3mp = MethodParams.getNew();
		// vl3mp.init( null );
		vl3mp.set( PossibleParams.homeDir, "homeDir3" );
		vl3.init( vl3mp );
		vl3mp.deInit();
		mp.set( PossibleParams.varLevelAll, vl3 );
		assertTrue( vl3.isInited() );
		ml3.init( mp );
		mp.deInit();
		assertTrue( vl3.isInited() );
		assertTrue( ml3.junitGetVar() == vl3 );
		ml3.showHome();
		ml3.deInit();
		assertTrue( vl3.isInited() );
		vl3.deInit();
		assertFalse( vl3.isInited() );
		vl3 = null;
		
		ml3.init( null );
		VarLevel3 intern = (VarLevel3)ml3.junitGetVar();
		assertNotNull( intern );
		assertTrue( intern.isInited() );
		ml3.deInit();
		assertFalse( intern.isInited() );
		assertNotNull( ml3.junitGetVar() );// not null when using own var
	}
	

}