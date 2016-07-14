/*
 * Copyright (c) 2015-2016, Pradeeban Kathiravelu and others. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.emory.bmi.datacafe.core.conf;

import java.io.File;

/**
 * The common constants of Data Cafe
 */
public final class DatacafeConstants {

    /**
     * Suppress instantiation.
     */
    private DatacafeConstants() {
    }

    public static final String DATACAFE_PROPERTIES_FILE_ALT = new File(".").getAbsolutePath() +
            "/../../conf/datacafe.properties";

    public static final String DATACAFE_PROPERTIES_FILE = "conf/datacafe.properties";

    public static final String SQL_WRAP_CHARACTER = "`";

    public static final String PERIOD = ".";

    public static final String DEFAULT_HAZELCAST_MULTI_MAP = "default";

    public static final String RELATIONS_MAP_SUFFIX = "_RELATIONS";

    public static final String COLLECTION_INDICES_MAP_SUFFIX = "_COLLECTION_INDICES";

    public static final String META_INDICES_MULTI_MAP_SUFFIX = "_MULTI_META";

    public static final String META_INDICES_SINGLE_MAP_SUFFIX = "_SINGLE_META";

    public static final String DATASOURCES_MAP_ENTRY_KEY = "DATASOURCES";

    public static final String ATTRIBUTES_MAP_ENTRY_KEY = "ATTRIBUTES";

    public static final String SQL_FROM_ENTRY_KEY = "FROM";
}