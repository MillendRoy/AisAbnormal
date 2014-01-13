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

package dk.dma.ais.abnormal.event.db.domain.builders;

import com.google.common.base.Strings;
import dk.dma.ais.abnormal.event.db.domain.Vessel;

public class VesselBuilder {

    Vessel vessel;
    BehaviourBuilder behaviourBuilder;

    public VesselBuilder() {
        vessel = new Vessel();
    }

    public VesselBuilder(BehaviourBuilder behaviourBuilder) {
        vessel = new Vessel();
        this.behaviourBuilder = behaviourBuilder;
    }

    public static VesselBuilder Vessel() {
        return new VesselBuilder();
    }

    public BehaviourBuilder name(String name) {
        vessel.setName(trimAisString(name));
        return behaviourBuilder;
    }

    public VesselBuilder callsign(String callsign) {
        vessel.setCallsign(trimAisString(callsign));
        return this;
    }

    public VesselBuilder imo(Integer imo) {
        vessel.setImo(imo);
        return this;
    }

    public VesselBuilder mmsi(Integer mmsi) {
        vessel.setMmsi(mmsi);
        return this;
    }

    public Vessel getVessel() {
        return vessel;
    }

    private static String trimAisString(String name) {
        if (!Strings.isNullOrEmpty(name)) {
            name = name.replace('@', ' ').trim();
        }
        return name;
    }
}
