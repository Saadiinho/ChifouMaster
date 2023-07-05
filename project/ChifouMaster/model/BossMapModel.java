package model;


public class BossMapModel {
	private double x;
    private double y;

    public BossMapModel() {
        this.x = 380;
        this.y = 120;
    }

    //Guetteur
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    //Setteurs
    public void setX(double x) {
    	//Bloque tout ceux qu'il y a en dehors le route
    	if (x<=270) {
    		return;
    	}
    	else if (x>=480) {
    		return;
    	}
    	//Bloque le bas
    	else if (x<=310) {
    		if (y>=480) {
    			return;
    		}
    	}
    	else if (x>=440) {
    		if (y>=480) {
    			return;
    		}
    	}
    	this.x = x;
    }
    public void setY(double y) {
    	//Limite les mouvements à la route
    	if (y>=510 || y<0) {
    		return;
    	}
    	else if (x<=310) {
    		if (y>=480) {
    			return;
    		}
    	}
    	else if (x>=440) {
    		if (y>=480) {
    			return;
    		}
    	}
    	this.y = y;
    }
    
    //Pour savoir si le personnage est proche du boss
    public boolean isCharacterNearBoss() {
		if (y >= 470) {
			return true;
		}
		return false;
	}
    //Pour savoir si le personnage est proche de la map précédente
    public boolean isCharacterNearPreviousMap() {
		if (y <= 10) {
			return true;
		}
		return false;
	}
	
}
