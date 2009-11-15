/**
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



import java.util.HashMap;

import org.dml.tools.RunTime;
import org.references.Reference;



/**
 * Node Java-Identifier a.k.a. NodeJavaID = node name on the java level which is
 * basically
 * a string<br>
 * this is how we identify Nodes at the java level, using NodeJavaIDs<br>
 * 
 * - you're supposed to always use NodeJavaIDs while in java, to access Nodes<br>
 * 
 * - it's probably a bad idea to cache ie. NodeIDs because while cached, it may<br>
 * be
 * removed from the underlying storage(ie. BDB) by some other
 * program/thread(assuming concurrency will ever be allowed),
 * although this might be challenging to implement (or limiting).<br>
 * 
 * - NodeJavaIDs(Level1) != NodeIDs(Level2)
 */
public class NodeJavaID extends Reference<String> {
	
	// this will keep track of all NodeJavaIDs of all environments
	// there's no point of having same JavaID twice for diff environments
	protected static final HashMap<String, NodeJavaID>	all_Level1_NodeJavaIDs	= new HashMap<String, NodeJavaID>();
	
	// string representation of the ID
	// private String stringID = null;
	
	protected static final void junitClearAll() {

		if ( null != all_Level1_NodeJavaIDs ) {
			all_Level1_NodeJavaIDs.clear();
		}
		// all_Level1_NodeJavaIDs = new HashMap<String, NodeJavaID>();
	}
	
	/**
	 * get the JavaID for this string<br>
	 * make a new one if it doesn't exist<br>
	 * one to one mapping between the string and the JavaID
	 * 
	 * @param strID
	 *            a normal string that's supposed to be used as an ID
	 * @return JavaID (java ID) - the encapsulated string strID<br>
	 *         should never return null
	 */
	public static NodeJavaID ensureJavaIDFor( String strID ) {

		RunTime.assertNotNull( strID );
		NodeJavaID curr = all_Level1_NodeJavaIDs.get( strID );
		if ( null == curr ) {
			// create new
			curr = new NodeJavaID( strID );
			if ( all_Level1_NodeJavaIDs.put( strID, curr ) != null ) {
				RunTime.bug( "a value already existed?!! wicked! it means that the above .get() is bugged?!" );
			}
		}
		return curr;
	}
	
	
	/**
	 * private constructor to prevent usage via new
	 * 
	 * @param strID
	 */
	private NodeJavaID( String strID ) {

		RunTime.assertNotNull( strID );
		
		this.setObject( strID );
		
	}
	
	@Override
	public String toString() {

		return this.getClass().getSimpleName() + ":" + this.getObject();
	}
	
}