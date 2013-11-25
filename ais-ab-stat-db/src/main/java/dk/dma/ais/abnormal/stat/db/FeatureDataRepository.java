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
package dk.dma.ais.abnormal.stat.db;

import dk.dma.ais.abnormal.stat.db.data.FeatureData;

import java.util.Set;

public interface FeatureDataRepository {
    FeatureData get(String featureName, long cellId);
    void put(String featureName, long cellId, FeatureData featureData);
    void close();

    Set<String> getFeatureNames();
    long getNumberOfCells(String featureName);
}
