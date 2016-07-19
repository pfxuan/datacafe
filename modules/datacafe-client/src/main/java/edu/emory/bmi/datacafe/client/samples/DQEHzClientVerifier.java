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
package edu.emory.bmi.datacafe.client.samples;

import edu.emory.bmi.datacafe.client.core.ClientExecutorEngine;
import edu.emory.bmi.datacafe.client.core.QueryBuilderClient;
import edu.emory.bmi.datacafe.client.drill.DrillConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * A sample drill query executor.
 */
public class DQEHzClientVerifier {
    private static Logger logger = LogManager.getLogger(DQEHzClientVerifier.class.getName());
    private static final String executionId = "PhysioNetIntegratedExecutor";

    private static final String attributes[] = {"SUBJECT_ID", "DOB", "HADM_ID", "ICD9_CODE", "SHORT_TITLE", "DESCRIPTION"};

    private static final String collections[] = {
            "hdfs.root.`physionet_patients.csv`",
            "hdfs.root.`physionet_patients.csv`",
            "hdfs.root.`physionet_diagnosesicd.csv`",
            "hdfs.root.`physionet_dicddiagnosis.csv`",
            "hdfs.root.`physionet_dicddiagnosis.csv`",
            "hdfs.root.`physionet_caregivers.csv`"};

    public static final String DRILL_SAMPLE_QUERY = "SELECT PhysioNetIntegratedExecutor1.SUBJECT_ID, PhysioNetIntegratedExecutor1.DOB, PhysioNetIntegratedExecutor2.HADM_ID, PhysioNetIntegratedExecutor3.ICD9_CODE, PhysioNetIntegratedExecutor3.SHORT_TITLE, PhysioNetIntegratedExecutor5.DESCRIPTION\n" +
            "FROM hdfs.root.`physionet_patients.csv` PhysioNetIntegratedExecutor1,\n" +
            "hdfs.root.`physionet_diagnosesicd.csv` PhysioNetIntegratedExecutor2,\n" +
            "hdfs.root.`physionet_dicddiagnosis.csv` PhysioNetIntegratedExecutor3,\n" +
            "hdfs.root.`physionet_datetimeevents.csv` PhysioNetIntegratedExecutor4,\n" +
            "hdfs.root.`physionet_caregivers.csv` PhysioNetIntegratedExecutor5\n" +
            "WHERE PhysioNetIntegratedExecutor1.SUBJECT_ID = PhysioNetIntegratedExecutor2.SUBJECT_ID AND PhysioNetIntegratedExecutor2.ICD9_CODE = PhysioNetIntegratedExecutor3.ICD9_CODE AND PhysioNetIntegratedExecutor4.SUBJECT_ID = PhysioNetIntegratedExecutor1.SUBJECT_ID AND PhysioNetIntegratedExecutor4.CGID = PhysioNetIntegratedExecutor5.CGID";

    public static String derivedQueryFromHazelcast;

    public static void main(String[] args) {
        ClientExecutorEngine.init();
        QueryBuilderClient queryBuilderClient = new QueryBuilderClient(executionId, attributes, collections);
        derivedQueryFromHazelcast = queryBuilderClient.buildQueryStatement();


//        DrillConnector.executeQuery(DRILL_SAMPLE_QUERY, 6);
        DrillConnector.executeQuery(derivedQueryFromHazelcast, 6);

        if (DRILL_SAMPLE_QUERY.trim().equals(derivedQueryFromHazelcast.trim())) {
            logger.info("The derived Query is equal to the static query");
        } else {
            logger.info("The derived query is: \n" + derivedQueryFromHazelcast);
        }
    }
}