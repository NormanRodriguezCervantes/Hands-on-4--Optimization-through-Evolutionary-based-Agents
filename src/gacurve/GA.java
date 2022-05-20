/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gacurve;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

/**
 *
 * @author Jet_m
 */
public class GA extends Agent{
    GACurve ga = new GACurve();
    
    @Override
    public void setup(){
        addBehaviour(new myShot());
    }  
    
    private class myShot extends OneShotBehaviour{
        @Override
        public void action(){
        ga.execute();
        }
        
        @Override
        public int onEnd(){
            myAgent.doDelete();
            return super.onEnd();
        }
    }
}
