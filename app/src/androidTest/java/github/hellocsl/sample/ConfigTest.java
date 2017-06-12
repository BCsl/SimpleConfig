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

import github.hellocsl.simpleconfig.SimpleConfig;

/**
 * Created by chensuilun on 2017/6/12.
 */
@RunWith(AndroidJUnit4.class)
@MediumTest
public class ConfigTest {
    Context mAppContext = InstrumentationRegistry.getTargetContext();

    @Before
    public void setUp() {

    }

    public void testConfig() {
        SimpleConfig simpleConfig = new SimpleConfig(mAppContext);

    }


    @Test
    public void testGet() {
        SimpleConfig simpleConfig = new SimpleConfig(mAppContext);
        Config config = simpleConfig.create(Config.class);
        simpleConfig.getConfig().edit().clear().commit();

        Assert.assertEquals(config.getIndex(), -1);
        Assert.assertEquals(config.getIndex(100), 100);

        Assert.assertEquals(config.getLong(), -1L);
        Assert.assertEquals(config.getLong(100l), 100l);

        Assert.assertEquals(config.getFloat(0.f), 0.f);
        Assert.assertEquals(config.getFloat(0.2f), 0.2f);

        Assert.assertEquals(config.getBoolean(), false);
        Assert.assertEquals(config.getBoolean(true), true);

        Assert.assertNull(config.getString());
        String helloWorld = "HelloWorld";
        Assert.assertEquals(config.getString(helloWorld), helloWorld);


        Assert.assertNull(config.getSet());
        Set<String> set = new HashSet<String>();
        Assert.assertEquals(config.getSet(set), set);

    }

    @Test
    public void testCommit() {
        SimpleConfig simpleConfig = new SimpleConfig(mAppContext);
        Config config = simpleConfig.create(Config.class);
        simpleConfig.getConfig().edit().clear().commit();

        Assert.assertEquals(config.getIndex(), -1);
        Assert.assertTrue(config.commitIndex(111));
        Assert.assertEquals(config.getIndex(), 111);

        Assert.assertEquals(config.getLong(), -1);
        Assert.assertTrue(config.commitLong(111l));
        Assert.assertEquals(config.getLong(), 111l);

        Assert.assertEquals(config.getBoolean(), false);
        Assert.assertTrue(config.commitBoolean(false));
        Assert.assertEquals(config.getBoolean(), false);

        Assert.assertEquals(config.getFloat(), 0.f);
        Assert.assertTrue(config.commitFloat(0.3f));
        Assert.assertEquals(config.getFloat(), 0.3f);

        Assert.assertNull(config.getString());
        String helloWorld = new String("Test SimpleConfig");
        Assert.assertTrue(config.commitString(helloWorld));
        Assert.assertEquals(config.getString(), helloWorld);

        Assert.assertNull(config.getSet());
        Set<String> set = new HashSet<String>();
        set.add(helloWorld);
        Assert.assertTrue(config.commitSet(set));
        Assert.assertEquals(config.getSet(), set);
    }

    @Test
    public void testApply() {
        SimpleConfig simpleConfig = new SimpleConfig(mAppContext);
        Config config = simpleConfig.create(Config.class);
        simpleConfig.getConfig().edit().clear().commit();

        Assert.assertEquals(config.getIndex(), -1);
        config.applyIndex(111);
        Assert.assertEquals(config.getIndex(), 111);

        Assert.assertEquals(config.getLong(), -1);
        config.applyLong(111l);
        Assert.assertEquals(config.getLong(), 111l);

        Assert.assertEquals(config.getBoolean(), false);
        config.applyBoolean(false);
        Assert.assertEquals(config.getBoolean(), false);

        Assert.assertEquals(config.getFloat(), 0.f);
        config.applyFloat(0.3f);
        Assert.assertEquals(config.getFloat(), 0.3f);

        Assert.assertNull(config.getString());
        String helloWorld = new String("Test SimpleConfig");
        config.applyString(helloWorld);
        Assert.assertEquals(config.getString(), helloWorld);

        Assert.assertNull(config.getSet());
        Set<String> set = new HashSet<String>();
        set.add(helloWorld);
        config.applySet(set);
        Assert.assertEquals(config.getSet(), set);

    }
}
