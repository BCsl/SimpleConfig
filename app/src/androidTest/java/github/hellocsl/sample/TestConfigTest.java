package github.hellocsl.sample;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.Set;

import github.hellocsl.simpleconfig.Config;
import github.hellocsl.simpleconfig.SimpleConfig;

/**
 * Created by chensuilun on 2017/6/12.
 */
@RunWith(AndroidJUnit4.class)
@MediumTest
public class TestConfigTest {
    Context mAppContext = InstrumentationRegistry.getTargetContext();
    SimpleConfig mSimpleConfig;

    @Before
    public void setUp() {
        mSimpleConfig = new SimpleConfig.Builder(mAppContext).build();
    }

    @Test
    public void testConfig() {
        Config config = mSimpleConfig.loadConfigs(TestConfig.class);
        Assert.assertEquals(config.getName(), "Simple");
        Assert.assertEquals(config.getMode(), 0);
    }


    @Test
    public void testGet() {
        TestConfig testConfig = mSimpleConfig.create(TestConfig.class);
        mSimpleConfig.loadConfigs(TestConfig.class).clear(false);

        Assert.assertEquals(testConfig.getIndex(), -1);
        Assert.assertEquals(testConfig.getIndex(100), 100);

        Assert.assertEquals(testConfig.getLong(), -1L);
        Assert.assertEquals(testConfig.getLong(100l), 100l);

        Assert.assertEquals(testConfig.getFloat(0.f), 0.f);
        Assert.assertEquals(testConfig.getFloat(0.2f), 0.2f);

        Assert.assertEquals(testConfig.getBoolean(), false);
        Assert.assertEquals(testConfig.getBoolean(true), true);

        Assert.assertNull(testConfig.getString());
        String helloWorld = "HelloWorld";
        Assert.assertEquals(testConfig.getString(helloWorld), helloWorld);


        Assert.assertNull(testConfig.getSet());
        Set<String> set = new HashSet<String>();
        Assert.assertEquals(testConfig.getSet(set), set);

    }

    @Test
    public void testCommit() {
        TestConfig testConfig = mSimpleConfig.create(TestConfig.class);
        mSimpleConfig.loadConfigs(TestConfig.class).clear(false);

        Assert.assertEquals(testConfig.getIndex(), -1);
        Assert.assertTrue(testConfig.commitIndex(111));
        Assert.assertEquals(testConfig.getIndex(), 111);

        Assert.assertEquals(testConfig.getLong(), -1);
        Assert.assertTrue(testConfig.commitLong(111l));
        Assert.assertEquals(testConfig.getLong(), 111l);

        Assert.assertEquals(testConfig.getBoolean(), false);
        Assert.assertTrue(testConfig.commitBoolean(false));
        Assert.assertEquals(testConfig.getBoolean(), false);

        Assert.assertEquals(testConfig.getFloat(), 0.f);
        Assert.assertTrue(testConfig.commitFloat(0.3f));
        Assert.assertEquals(testConfig.getFloat(), 0.3f);

        Assert.assertNull(testConfig.getString());
        String helloWorld = new String("Test SimpleConfig");
        Assert.assertTrue(testConfig.commitString(helloWorld));
        Assert.assertEquals(testConfig.getString(), helloWorld);

        Assert.assertNull(testConfig.getSet());
        Set<String> set = new HashSet<String>();
        set.add(helloWorld);
        Assert.assertTrue(testConfig.commitSet(set));
        Assert.assertEquals(testConfig.getSet(), set);
    }

    @Test
    public void testApply() {
        TestConfig testConfig = mSimpleConfig.create(TestConfig.class);
        mSimpleConfig.loadConfigs(TestConfig.class).clear(false);

        Assert.assertEquals(testConfig.getIndex(), -1);
        testConfig.applyIndex(111);
        Assert.assertEquals(testConfig.getIndex(), 111);

        Assert.assertEquals(testConfig.getLong(), -1);
        testConfig.applyLong(111l);
        Assert.assertEquals(testConfig.getLong(), 111l);

        Assert.assertEquals(testConfig.getBoolean(), false);
        testConfig.applyBoolean(false);
        Assert.assertEquals(testConfig.getBoolean(), false);

        Assert.assertEquals(testConfig.getFloat(), 0.f);
        testConfig.applyFloat(0.3f);
        Assert.assertEquals(testConfig.getFloat(), 0.3f);

        Assert.assertNull(testConfig.getString());
        String helloWorld = new String("Test SimpleConfig");
        testConfig.applyString(helloWorld);
        Assert.assertEquals(testConfig.getString(), helloWorld);

        Assert.assertNull(testConfig.getSet());
        Set<String> set = new HashSet<String>();
        set.add(helloWorld);
        testConfig.applySet(set);
        Assert.assertEquals(testConfig.getSet(), set);

    }
}
