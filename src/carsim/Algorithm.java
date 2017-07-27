/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsim;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ayomitundefafore
 */
public class Algorithm {

    enum Action {
        F,
        B,
        R,
        L
    }

    enum State {
        S,
        F
    }

    private final int maxDistanceToCrash = 30;
    CarSim carsim;
    Map<Enum, Integer> degreeMapper = new HashMap();

    public Algorithm(CarSim carsim) {
        this.carsim = carsim;
    }

    //first create very simple algorithm.. apply degree to which turns are taken ?
    public void simpleDrive(boolean start) {
        while (start) {
            if (carsim.getSENSOR2DISTANCE() <= maxDistanceToCrash) {
                if (degreeMapper.get(Action.F) != null) {
                    carsim.applyBrake(degreeMapper.get(Action.F));
                } else {
                    if (degreeMapper.get(Action.B) == null) {
                        carsim.applyBrake(1);
                        degreeMapper.put(Action.B, 1);
                    } else {
                        int degree = degreeMapper.get(Action.B) + 1;
                        carsim.applyBrake(degree);
                        degreeMapper.put(Action.B, degree);
                    }
                }
                carsim.moveBackward();
            } else {
                if (carsim.getSENSOR1DISTANCE() <= maxDistanceToCrash) {
                    if (degreeMapper.get(Action.L) != null) {
                        carsim.moveRight(degreeMapper.get(Action.L));
                        degreeMapper.put(Action.R, degreeMapper.get(Action.L));
                    } else {
                        if (degreeMapper.get(Action.R) == null) {
                            carsim.moveRight(1);
                            degreeMapper.put(Action.R, 1);
                        } else {
                            int degree = degreeMapper.get(Action.R) + 1;
                            carsim.moveRight(degree);
                            degreeMapper.put(Action.R, degree);
                        }
                    }
                } else if (carsim.getSENSOR3DISTANCE() <= maxDistanceToCrash) {
                    if (degreeMapper.get(Action.R) != null) {
                        carsim.moveLeft(degreeMapper.get(Action.R));
                        degreeMapper.put(Action.L, degreeMapper.get(Action.R));
                    } else {
                        if (degreeMapper.get(Action.L) == null) {
                            carsim.moveLeft(1);
                            degreeMapper.put(Action.L, 1);
                        } else {
                            int degree = degreeMapper.get(Action.L) + 1;
                            carsim.moveLeft(degree);
                            degreeMapper.put(Action.L, degree);
                        }
                    }
                } else {
                    carsim.moveForward();
                    if (degreeMapper.get(Action.F) == null) {
                        degreeMapper.put(Action.F, 1);
                    } else {
                        degreeMapper.put(Action.F, degreeMapper.get(Action.F) + 1);
                    }
                }
            }

            //left sensor
            //System.out.println(carsim.getSENSOR1DISTANCE()+"\n"+carsim.getSENSOR2DISTANCE()+"\n"+carsim.getSENSOR3DISTANCE());
        }
    }
}