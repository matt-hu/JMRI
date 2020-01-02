package jmri.jmrix;

import java.util.Comparator;
import java.util.ResourceBundle;
import jmri.InstanceManager;
import jmri.NamedBean;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Abstract base class for SystemConnectionMemo objects.
 *
 * @author Paul Bender Copyright (C) 2017
 */
abstract public class SystemConnectionMemoTestBase {

    protected SystemConnectionMemo scm = null; // is set up in overriding test

    public void getTest(Class t) {
        if (scm.provides(t)) {
            // if the manager reports providing the class, make sure it exists.
            Assert.assertNotNull("Provides Class " + t.getName(), scm.get(t));
        } else {
            Assert.assertNull("Provides Class " + t.getName(), scm.get(t));
        }
    }

    @Test
    public void getPowerManager() {
        getTest(jmri.PowerManager.class);
    }

    @Test
    public void getTurnoutManager() {
        getTest(jmri.TurnoutManager.class);
    }

    @Test
    public void getThrottleManager() {
        getTest(jmri.ThrottleManager.class);
    }

    @Test
    public void getSensorManager() {
        getTest(jmri.SensorManager.class);
    }

    @Test
    public void getLightManager() {
        getTest(jmri.LightManager.class);
    }

    @Test
    public void getReporterManager() {
        getTest(jmri.ReporterManager.class);
    }

    @Test
    public void testCtor() {
        Assert.assertNotNull("exists", scm);
    }

    @Test
    public void testProvidesConsistManager() {
        getTest(jmri.ReporterManager.class);
    }

    @Test
    public void testGetAndSetPrefix() {
        scm.setSystemPrefix("A2");
        Assert.assertEquals("System Prefix after set", "A2", scm.getSystemPrefix());
    }

    @Test
    public void testMultipleMemosSamePrefix() {
        SystemConnectionMemo m = new SystemConnectionMemo("t", "test") {
            @Override
            protected ResourceBundle getActionModelResourceBundle() {
                return null;
            }

            @Override
            public <B extends NamedBean> Comparator<B> getNamedBeanComparator(Class<B> type) {
                return null;
            }
        };
        Assert.assertEquals("t", m.getSystemPrefix());
        m.register();
        Assert.assertTrue(InstanceManager.getList(SystemConnectionMemo.class).contains(m));
        Assert.assertFalse(scm.setSystemPrefix("t"));
        Assert.assertTrue(scm.setSystemPrefix("t2"));
        Assert.assertEquals("t2", scm.getSystemPrefix());
    }

    @Test
    public void testGetAndSetOutputInterval() {
        scm.setOutputInterval(50);
        Assert.assertEquals("Output Interval after set", 50, scm.getOutputInterval());
    }

    @Before
    abstract public void setUp();

    @After
    abstract public void tearDown();

}
