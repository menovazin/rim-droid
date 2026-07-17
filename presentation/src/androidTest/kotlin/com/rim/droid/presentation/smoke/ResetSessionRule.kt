package com.rim.droid.presentation.smoke

import org.junit.rules.TestWatcher
import org.junit.runner.Description

class ResetSessionRule : TestWatcher() {
    override fun starting(description: Description) {
        FakeSessionModule.session.clear()
    }
}
