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
public class NoAnnotationConfigTest {
    Context mAppContext = InstrumentationRegistry.getTargetContext();
    SimpleConfig mSimpleConfig;

    @Before
    public void setUp() {
        mSimpleConfig = new SimpleConfig.Builder(mAppContext).build();
    }

    @Test
    public void testConfig() {
        Config config = mSimpleConfig.loadConfigs(NoAnnotationConfig.class);
        Assert.assertEquals(config.getName(), "NoAnnotationConfig");
        Assert.assertEquals(config.getMode(), 0);
    }


    @Test
    public void testGet() {
        NoAnnotationConfig testConfig = mSimpleConfig.create(NoAnnotationConfig.class);
        mSimpleConfig.loadConfigs(NoAnnotationConfig.class).clear(false);

        Assert.assertEquals(testConfig.getHelloInt(), -1);
        Assert.assertEquals(testConfig.getHelloInt(100), 100);

        Assert.assertEquals(testConfig.getHelloLong(), -1L);
        Assert.assertEquals(testConfig.getHelloLong(100l), 100l);

        Assert.assertEquals(testConfig.getHelloFloat(0.f), 0.f);
        Assert.assertEquals(testConfig.getHelloFloat(0.2f), 0.2f);

        Assert.assertEquals(testConfig.getHelloBoolean(), false);
        Assert.assertEquals(testConfig.getHelloBoolean(true), true);

        Assert.assertNull(testConfig.getHelloString());
        String helloWorld = "HelloWorld";
        Assert.assertEquals(testConfig.getHelloString(helloWorld), helloWorld);


        Assert.assertNull(testConfig.getHelloSet());
        Set<String> set = new HashSet<String>();
        Assert.assertEquals(testConfig.getHelloSet(set), set);

    }

    @Test
    public void testCommit() {
        NoAnnotationConfig testConfig = mSimpleConfig.create(NoAnnotationConfig.class);
        mSimpleConfig.loadConfigs(NoAnnotationConfig.class).clear(false);

        Assert.assertEquals(testConfig.getHelloInt(), -1);
        Assert.assertTrue(testConfig.commitHelloInt(111));
        Assert.assertEquals(testConfig.getHelloInt(), 111);

        Assert.assertEquals(testConfig.getHelloLong(), -1);
        Assert.assertTrue(testConfig.commitHelloLong(111l));
        Assert.assertEquals(testConfig.getHelloLong(), 111l);

        Assert.assertEquals(testConfig.getHelloBoolean(), false);
        Assert.assertTrue(testConfig.commitHelloBoolean(false));
        Assert.assertEquals(testConfig.getHelloBoolean(), false);

        Assert.assertEquals(testConfig.getHelloFloat(), 0.f);
        Assert.assertTrue(testConfig.commitHelloFloat(0.3f));
        Assert.assertEquals(testConfig.getHelloFloat(), 0.3f);

        Assert.assertNull(testConfig.getHelloString());
        String helloWorld = new String("Test SimpleConfig");
        Assert.assertTrue(testConfig.commitHelloString(helloWorld));
        Assert.assertEquals(testConfig.getHelloString(), helloWorld);

        Assert.assertNull(testConfig.getHelloSet());
        Set<String> set = new HashSet<String>();
        set.add(helloWorld);
        Assert.assertTrue(testConfig.commitHelloSet(set));
        Assert.assertEquals(testConfig.getHelloSet(), set);
    }

    @Test
    public void testApply() {
        NoAnnotationConfig testConfig = mSimpleConfig.create(NoAnnotationConfig.class);
        mSimpleConfig.loadConfigs(NoAnnotationConfig.class).clear(false);

        Assert.assertEquals(testConfig.getHelloInt(), -1);
        testConfig.applyHelloInt(111);
        Assert.assertEquals(testConfig.getHelloInt(), 111);

        Assert.assertEquals(testConfig.getHelloLong(), -1);
        testConfig.applyHelloLong(111l);
        Assert.assertEquals(testConfig.getHelloLong(), 111l);

        Assert.assertEquals(testConfig.getHelloBoolean(), false);
        testConfig.applyHelloBoolean(false);
        Assert.assertEquals(testConfig.getHelloBoolean(), false);

        Assert.assertEquals(testConfig.getHelloFloat(), 0.f);
        testConfig.applyHelloFloat(0.3f);
        Assert.assertEquals(testConfig.getHelloFloat(), 0.3f);

        Assert.assertNull(testConfig.getHelloString());
        String helloWorld = new String("Test SimpleConfig");
        testConfig.applyHelloString(helloWorld);
        Assert.assertEquals(testConfig.getHelloString(), helloWorld);

        Assert.assertNull(testConfig.getHelloSet());
        Set<String> set = new HashSet<String>();
        set.add(helloWorld);
        testConfig.applyHelloSet(set);
        Assert.assertEquals(testConfig.getHelloSet(), set);

    }
}
