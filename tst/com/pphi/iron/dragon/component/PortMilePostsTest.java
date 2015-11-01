package com.pphi.iron.dragon.component;


import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class PortMilePostsTest {

    @Test
    public void testPortsCreation() throws IOException {
        PortMilePosts portMilePosts = new PortMilePosts();
        assertEquals(25, portMilePosts.getPorts().size());
    }
}
