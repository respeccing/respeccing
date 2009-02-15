
package org.demlinks.node;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith( Suite.class )
@Suite.SuiteClasses( value = {
		NodeTest.class, NodeListTest.class, PointerNodeTest.class,
		GlobalNodesTest.class, NodeWithDupChildrenTest.class
} )
public class AllTestsNode {
}