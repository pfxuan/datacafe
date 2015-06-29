/*
 * Title:        Cloud2Sim
 * Description:  Distributed and Concurrent Cloud Simulation
 *               Toolkit for Modeling and Simulation
 *               of Clouds - Enhanced version of CloudSim.
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2014, Pradeeban Kathiravelu <pradeeban.kathiravelu@tecnico.ulisboa.pt>
 */

package edu.emory.bmi.datacafe.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.FileSystemXmlConfig;
import com.hazelcast.config.GroupConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;

/**
 * The singleton base class integrating hazelcast into cloudsim, with its configurations
 */
public class HazelSimCore {
    private static HazelSimCore hazelSimCore = null;
    private static String clusterGroup = HzConstants.MAIN_HZ_CLUSTER;
    private static Logger logger = LogManager.getLogger(HazelSimCore.class.getName());


    private HazelSimCore(int noOfSimultaneousInstances) {
        Config cfg = getCfg();
        HazelSim.spawnInstances(cfg, noOfSimultaneousInstances);
    }

    /**
     * Gets the configurations
     * @return hazelcast configurations
     */
    public static Config getCfg() {
        return getCfg(clusterGroup);
    }

    /**
     * Gets the configurations
     * @return hazelcast configurations
     */
    public static Config getCfg(String mainCluster) {
        Config cfg;
        try {
            cfg = new FileSystemXmlConfig(HzConstants.HAZELCAST_CONFIG_FILE);
        } catch (FileNotFoundException e) {
            logger.info(HzConstants.HAZELCAST_CONFIG_FILE_NOT_FOUND_ERROR);
            cfg = new Config();
        }
        cfg.setProperty("hazelcast.initial.min.cluster.size", String.valueOf(HzConstants.NO_OF_PARALLEL_EXECUTIONS));
        cfg.setProperty("hazelcast.operation.call.timeout.millis", "50000000");
        GroupConfig groupConfig = new GroupConfig(mainCluster);
        cfg.setGroupConfig(groupConfig);
        return cfg;
    }



    public static HazelSimCore getHazelSimCore(int noOfSimultaneousInstances, String _clusterGroup) {
        if (hazelSimCore == null) {
            clusterGroup = _clusterGroup;
            hazelSimCore = new HazelSimCore(noOfSimultaneousInstances);
        }
        return hazelSimCore;
    }

    public static HazelSimCore getHazelSimCore(int noOfSimultaneousInstances) {
        if (hazelSimCore == null) {
            hazelSimCore = new HazelSimCore(noOfSimultaneousInstances);
        }
        return hazelSimCore;
    }
}
