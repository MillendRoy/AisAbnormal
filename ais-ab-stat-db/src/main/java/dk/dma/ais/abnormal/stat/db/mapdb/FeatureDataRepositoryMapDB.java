/* Copyright (c) 2011 Danish Maritime Authority
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this library.  If not, see <http://www.gnu.org/licenses/>.
 */

package dk.dma.ais.abnormal.stat.db.mapdb;

import dk.dma.ais.abnormal.stat.db.FeatureDataRepository;
import dk.dma.ais.abnormal.stat.db.data.FeatureData;
import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;
import java.util.Set;

public class FeatureDataRepositoryMapDB implements FeatureDataRepository {

    static final Logger LOG = LoggerFactory.getLogger(FeatureDataRepositoryMapDB.class);

    private static final String FILENAME_SUFFIX = ".featureData";

    private DB db;

    public FeatureDataRepositoryMapDB(String dbFileName) throws Exception {
        if (! dbFileName.endsWith(FILENAME_SUFFIX)) {
            dbFileName = dbFileName.concat(FILENAME_SUFFIX);
        }

        File dbFile = new File(dbFileName);
        String canonicalPath = dbFile.getCanonicalPath();// Check that path is valid

        LOG.debug("Attempting to access feature data in file " + canonicalPath);

        if (dbFile.exists()) {
            LOG.debug("Will reuse existing file: " + canonicalPath);
        } else {
            LOG.debug("Will create new file: " + canonicalPath);
        }

        db = DBMaker
                .newFileDB(dbFile)
                .transactionDisable()
                .fullChunkAllocationEnable()
                .randomAccessFileEnable()
                //.asyncWriteDisable()
                .cacheDisable()
                .closeOnJvmShutdown()
                .make();

        LOG.debug("File " + canonicalPath + " successfully opened by MapDB.");
    }

    /*
    @Override
    public void close() {
        db.commit();
        db.close();
    }

    @Override
    public void store(FeatureData featureData) {
        HTreeMap<Object,Object> featureCollection = db.getHashMap(DB_FEATURES);
        featureCollection.put(featureData.getFeatureName(), featureData);

    }
*/

    @Override
    public Set<String> getFeatureNames() {
        Map<String, Object> features = db.getAll();
        return features.keySet();
    }

    @Override
    public FeatureData get(String featureName, long cellId) {
        BTreeMap<Object, Object> allCellDataForFeature = db.createTreeMap(featureName).makeOrGet();
        FeatureData featureData = (FeatureData) allCellDataForFeature.get(cellId);
        db.commit();
        return featureData;
    }

    @Override
    public void put(String featureName, long cellId, FeatureData featureData) {
        BTreeMap<Object, Object> allCellDataForFeature = db.createTreeMap(featureName).makeOrGet();
        allCellDataForFeature.put(cellId, featureData);
    }

    @Override
    public void close() {
        LOG.info("Attempting to commit feature data repository.");
        db.commit();
        LOG.info("Feature data repository committed.");

        // LOG.info("Attempting to compact feature data repository.");
        // db.compact();
        // LOG.info("Feature data repository compacted.");

        LOG.info("Attempting to close feature data repository.");
        db.close();
        LOG.info("Feature data repository closed.");
    }
}
