/*
 * Adapted from the Wizardry License
 *
 * Copyright (c) 2015-2018 the Valkyrien Warfare team
 *
 * Permission is hereby granted to any persons and/or organizations using this software to copy, modify, merge, publish, and distribute it.
 * Said persons and/or organizations are not allowed to use the software or any derivatives of the work for commercial use or any other means to generate income unless it is to be used as a part of a larger project (IE: "modpacks"), nor are they allowed to claim this software as their own.
 *
 * The persons and/or organizations are also disallowed from sub-licensing and/or trademarking this software without explicit permission from the Valkyrien Warfare team.
 *
 * Any persons and/or organizations using this software must disclose their source code and have it publicly available, include this license, provide sufficient credit to the original authors of the project (IE: The Valkyrien Warfare team), as well as provide a link to the original project.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package valkyrienwarfare.physics.data;

import valkyrienwarfare.api.Vector;
import valkyrienwarfare.mod.network.PhysWrapperPositionMessage;
import valkyrienwarfare.physics.management.PhysicsObject;

public class ShipTransformData {

    public int relativeTick;

    public double posX, posY, posZ;
    public double pitch, yaw, roll;
    public Vector centerOfRotation;

    public ShipTransformData(PhysWrapperPositionMessage wrapperMessage) {
        posX = wrapperMessage.posX;
        posY = wrapperMessage.posY;
        posZ = wrapperMessage.posZ;

        pitch = wrapperMessage.pitch;
        yaw = wrapperMessage.yaw;
        roll = wrapperMessage.roll;

        centerOfRotation = wrapperMessage.centerOfMass;

        relativeTick = wrapperMessage.relativeTick;
    }

    // Apply all the position/rotation variables accordingly onto the passed physObject
    public void applyToPhysObject(PhysicsObject physObj) {
        physObj.wrapper.posX = posX;
        physObj.wrapper.posY = posY;
        physObj.wrapper.posZ = posZ;

        physObj.wrapper.pitch = pitch;
        physObj.wrapper.yaw = yaw;
        physObj.wrapper.roll = roll;

        physObj.centerCoord = centerOfRotation;
    }
}
