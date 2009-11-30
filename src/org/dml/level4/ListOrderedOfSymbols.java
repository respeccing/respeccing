/**
 * File creation: Oct 19, 2009 11:30:51 PM
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


package org.dml.level4;



import org.dml.level1.Symbol;
import org.references.Position;



/**
 * list of NodeIDs in which order matters and it's known<br>
 * should be able to hold any number of NodeIDs even if they repeat inside the
 * list<br>
 * the order of insertion is kept<br>
 * this will be a double linked list represented in DMLEnvironment<br>
 * this is level 4
 */
public class ListOrderedOfSymbols extends ListOrderedOfElementCapsules {
	
	
	public ListOrderedOfSymbols( Level4_DMLEnvironment envDML, Symbol name1 ) {

		super( envDML, name1 );
	}
	
	@Override
	protected void internal_setName() {

		env.ensureVector( env.allListsSymbol, name );
	}
	
	@Override
	protected boolean internal_hasNameSetRight() {

		return env.isVector( env.allListsSymbol, name );
	}
	
	

	synchronized public void addLast( Symbol whichSymbol ) {

		ElementCapsule ec = internal_encapsulateSymbol( whichSymbol );
		this.add_ElementCapsule( ec, Position.LAST );
	}
	
	public void addFirst( Symbol whichSymbol ) {

	}
}