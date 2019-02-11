package com.exasol.adapter.capabilities;

import com.exasol.adapter.sql.Predicate;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PredicateCapabilityTest {
    @Test
    public void testCompleteness() {
        // Do we have predicates where we don't have capabilities for?
        for (final Predicate pred : Predicate.values()) {
            boolean foundCap = false;
            for (final PredicateCapability cap : PredicateCapability.values()) {
                if (cap.getPredicate() == pred) {
                    foundCap = true;
                }
            }
            assertTrue("Did not find a capability for predicate " + pred.name(), foundCap);
        }
    }

    @Test
    public void testConsistentNaming() {
        for (final PredicateCapability cap : PredicateCapability.values()) {
            assertTrue(cap.name().startsWith(cap.getPredicate().name()));
        }
    }

}