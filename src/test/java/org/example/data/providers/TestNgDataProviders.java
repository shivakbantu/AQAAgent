package org.example.data.providers;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.example.core.Config;
import org.example.core.ConfigLoader;
import org.example.data.TestDataLoader;
import org.testng.annotations.DataProvider;

public final class TestNgDataProviders {
    private TestNgDataProviders() {
    }

    @DataProvider(name = "loginData")
    public static Iterator<Object[]> loginData() {
        Config config = ConfigLoader.load();
        List<Map<String, String>> rows = TestDataLoader.load(config.testDataSource());
        return rows.stream().map(r -> new Object[]{r}).iterator();
    }
}

