package com.disneyLand

import io.mockk.MockKAnnotations
import org.junit.Before

open class BaseTest {

    @Before
    open fun setUp() {
        MockKAnnotations.init(this)
    }
}
